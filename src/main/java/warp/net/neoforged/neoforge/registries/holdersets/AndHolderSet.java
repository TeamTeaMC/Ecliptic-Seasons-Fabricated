/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package warp.net.neoforged.neoforge.registries.holdersets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.teamtea.eclipticseasons.compat.fabric.ExtendHolderSetCodec;
import com.teamtea.eclipticseasons.compat.fabric.HolderSetTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;

/**
 * <p>Holderset that represents an intersection of other holdersets. Json format:</p>
 *
 * <pre>
 * {
 *   "type": "neoforge:and",
 *   "values":
 *   [
 *      // list of sub-holdersets (strings, lists, or objects)
 *   ]
 * }
 * </pre>
 */
public class AndHolderSet<T> extends CompositeHolderSet<T> {
    public AndHolderSet(List<HolderSet<T>> values) {
        super(values);
    }

    @SafeVarargs
    public AndHolderSet(HolderSet<T>... values) {
        this(List.of(values));
    }

    @Override
    public HolderSetType type() {
        return HolderSetTypes.AND;
    }

    @Override
    protected Set<Holder<T>> createSet() {
        List<HolderSet<T>> components = this.getComponents();
        if (components.size() < 1) {
            return Set.of();
        }
        if (components.size() == 1) {
            return components.get(0).stream().collect(Collectors.toSet());
        }

        List<HolderSet<T>> remainingComponents = components.subList(1, components.size());
        return components.get(0)
                .stream()
                .filter(holder -> remainingComponents.stream().allMatch(holderset -> holderset.contains(holder)))
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "AndSet[" + this.getComponents() + "]";
    }

    public static class Type implements HolderSetType {
        @Override
        public <T> MapCodec<? extends ICustomHolderSet<T>> makeCodec(ResourceKey<? extends Registry<T>> registryKey, Codec<Holder<T>> holderCodec, boolean forceList) {
            return ExtendHolderSetCodec.create(registryKey, holderCodec, forceList)
                    .listOf()
                    .xmap(AndHolderSet::new, CompositeHolderSet::homogenize)
                    .fieldOf("values");
        }

        @Override
        public <T> StreamCodec<RegistryFriendlyByteBuf, ? extends ICustomHolderSet<T>> makeStreamCodec(ResourceKey<? extends Registry<T>> registryKey) {
            if (true)
                throw new UnsupportedOperationException(
                        "Custom HolderSet network codecs require NeoForge's ByteBufCodecs.holderSet patch"
                );
            return ByteBufCodecs.<RegistryFriendlyByteBuf, HolderSet<T>>list()
                    .apply(ByteBufCodecs.holderSet(registryKey))
                    .map(AndHolderSet::new, CompositeHolderSet::getComponents);
        }
    }
}
