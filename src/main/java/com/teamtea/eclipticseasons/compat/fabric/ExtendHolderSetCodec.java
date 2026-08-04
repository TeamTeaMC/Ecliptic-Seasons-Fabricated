package com.teamtea.eclipticseasons.compat.fabric;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import warp.net.neoforged.neoforge.registries.holdersets.HolderSetType;

public class ExtendHolderSetCodec<E> implements Codec<HolderSet<E>> {
    private final ResourceKey<? extends Registry<E>> registryKey;
    private final Codec<Holder<E>> elementCodec;
    private final Codec<List<Holder<E>>> homogenousListCodec;
    private final Codec<Either<TagKey<E>, List<Holder<E>>>> registryAwareCodec;
    private final Codec<warp.net.neoforged.neoforge.registries.holdersets.ICustomHolderSet<E>> forgeDispatchCodec;
    private final Codec<Either<warp.net.neoforged.neoforge.registries.holdersets.ICustomHolderSet<E>, Either<TagKey<E>, List<Holder<E>>>>> combinedCodec;

    private static <E> Codec<List<Holder<E>>> homogenousList(Codec<Holder<E>> elementCodec, boolean alwaysUseList) {
        Codec<List<Holder<E>>> listCodec = elementCodec.listOf().validate(ExtraCodecs.ensureHomogenous(Holder::kind));
        return alwaysUseList ? listCodec : ExtraCodecs.compactListCodec(elementCodec, listCodec);
    }

    public static <E> Codec<HolderSet<E>> create(ResourceKey<? extends Registry<E>> registryKey, Codec<Holder<E>> elementCodec, boolean alwaysUseList) {
        return new ExtendHolderSetCodec<>(registryKey, elementCodec, alwaysUseList);
    }

    private static final Codec<Identifier> HOLDER_SET_TYPE_CODEC = Codec.STRING.xmap(
            value -> value.contains(":") ? Identifier.parse(value) : EclipticSeasons.rl(value),
            id -> id.getNamespace().equals(EclipticSeasonsApi.MODID) ? id.getPath() : id.toString()
    );

    public ExtendHolderSetCodec(ResourceKey<? extends Registry<E>> registryKey, Codec<Holder<E>> elementCodec, boolean alwaysUseList) {
        this.registryKey = registryKey;
        this.elementCodec = elementCodec;
        this.homogenousListCodec = homogenousList(elementCodec, alwaysUseList);
        this.registryAwareCodec = Codec.either(TagKey.hashedCodec(registryKey), this.homogenousListCodec);
        // FORGE: make registry-specific dispatch codec and make forge-or-vanilla either codec
        this.forgeDispatchCodec =
                HOLDER_SET_TYPE_CODEC.dispatch(
                        "type",
                        i -> HolderSetTypes.get(i.type()),
                        id -> {
                            HolderSetType type = HolderSetTypes.TYPES.get(id);
                            if (type == null) {
                                throw new IllegalArgumentException("Unknown holder set type: " + id);
                            }
                            return type.makeCodec(registryKey, elementCodec, alwaysUseList);
                        }
                );
        // this.forgeDispatchCodec = warp.net.neoforged.neoforge.registries.NeoForgeRegistries.HOLDER_SET_TYPES.byNameCodec()
        //         .dispatch(warp.net.neoforged.neoforge.registries.holdersets.ICustomHolderSet::type, type -> type.makeCodec(registryKey, elementCodec, alwaysUseList));
        this.combinedCodec = Codec.either(this.forgeDispatchCodec, this.registryAwareCodec);
    }

    @Override
    public <T> DataResult<Pair<HolderSet<E>, T>> decode(DynamicOps<T> ops, T input) {
        if (ops instanceof RegistryOps<T> registryOps) {
            Optional<HolderGetter<E>> registryOptional = registryOps.getter(this.registryKey);
            if (registryOptional.isPresent()) {
                HolderGetter<E> registry = registryOptional.get();
                // Neo: use the wrapped codec to decode custom/tag/list instead of just tag/list
                return this.combinedCodec.decode(ops, input)
                        .flatMap(
                                p -> {
                                    DataResult<HolderSet<E>> result = p.getFirst()
                                            .map(
                                                    DataResult::success,
                                                    tagOrList -> tagOrList.map(
                                                            tag -> lookupTag(registry, (TagKey<E>) tag),
                                                            values -> DataResult.success(HolderSet.direct((List<? extends Holder<E>>) values))
                                                    )
                                            );
                                    return result.map(holders -> Pair.of((HolderSet<E>) holders, (T) p.getSecond()));
                                }
                        );
            }
        }

        return this.decodeWithoutRegistry(ops, input);
    }

    private static <E> DataResult<HolderSet<E>> lookupTag(HolderGetter<E> registry, TagKey<E> key) {
        return registry.get(key)
                .<DataResult<HolderSet<E>>>map(DataResult::success)
                .orElseGet(() -> DataResult.error(() -> "Missing tag: '" + key.location() + "' in '" + key.registry().identifier() + "'"));
    }

    public <T> DataResult<T> encode(HolderSet<E> input, DynamicOps<T> ops, T prefix) {
        if (ops instanceof RegistryOps<T> registryOps) {
            Optional<HolderGetter<E>> maybeOwner = registryOps.getter(this.registryKey);
            if (maybeOwner.isPresent()) {
                if (!input.canSerializeIn(maybeOwner.get())) {
                    return DataResult.error(() -> "HolderSet " + input + " is not valid in current registry set");
                }

                // FORGE: use the dispatch codec to encode custom holdersets, otherwise fall back to vanilla tag/list
                if (input instanceof warp.net.neoforged.neoforge.registries.holdersets.ICustomHolderSet<E> customHolderSet)
                    return this.forgeDispatchCodec.encode(customHolderSet, ops, prefix);
                return this.registryAwareCodec.encode(input.unwrap().mapRight(List::copyOf), ops, prefix);
            }
        }

        return this.encodeWithoutRegistry(input, ops, prefix);
    }

    private <T> DataResult<Pair<HolderSet<E>, T>> decodeWithoutRegistry(DynamicOps<T> ops, T input) {
        return this.elementCodec.listOf().decode(ops, input).flatMap(p -> {
            List<Holder.Direct<E>> directHolders = new ArrayList<>();

            for (Holder<E> holder : p.getFirst()) {
                if (!(holder instanceof Holder.Direct<E> direct)) {
                    return DataResult.error(() -> "Can't decode element " + holder + " without registry");
                }

                directHolders.add(direct);
            }

            return DataResult.success(new Pair<>(HolderSet.direct(directHolders), p.getSecond()));
        });
    }

    private <T> DataResult<T> encodeWithoutRegistry(HolderSet<E> input, DynamicOps<T> ops, T prefix) {
        return this.homogenousListCodec.encode(input.stream().toList(), ops, prefix);
    }
}
