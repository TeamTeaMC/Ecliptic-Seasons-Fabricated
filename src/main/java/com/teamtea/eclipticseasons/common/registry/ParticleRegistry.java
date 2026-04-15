package com.teamtea.eclipticseasons.common.registry;

import com.mojang.serialization.MapCodec;
import com.teamtea.eclipticseasons.EclipticSeasons;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public final class ParticleRegistry {

    public static final SimpleParticleType FIREFLY = register("firefly", FabricParticleTypes.simple());
    public static final SimpleParticleType WILD_GOOSE = register("wild_goose", FabricParticleTypes.simple());
    public static final SimpleParticleType BUTTERFLY = register("butterfly", FabricParticleTypes.simple());

    public static final ParticleType<ColorParticleOption> GREENHOUSE = register("greenhouse",
            create(false, ColorParticleOption::codec, ColorParticleOption::streamCodec));
    public static final ParticleType<ColorParticleOption> FALLEN_LEAVES = register("fallen_leaves",
            create(false, ColorParticleOption::codec, ColorParticleOption::streamCodec));
    public static final ParticleType<ColorParticleOption> FLYING_BLOOM = register("flying_bloom",
            create(false, ColorParticleOption::codec, ColorParticleOption::streamCodec));

    private static <T extends ParticleType<?>> T register(String name, T type) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, EclipticSeasons.rl(name), type);
    }

    private static <T extends ParticleOptions> ParticleType<T> create(
            boolean pOverrideLimitter,
            final Function<ParticleType<T>, MapCodec<T>> pCodecGetter,
            final Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> pStreamCodecGetter
    ) {
        return new ParticleType<T>(pOverrideLimitter) {
            @Override
            public MapCodec<T> codec() {
                return pCodecGetter.apply(this);
            }

            @Override
            public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
                return pStreamCodecGetter.apply(this);
            }
        };
    }

    public static void init() {
        // 触发类加载
    }
}