package com.teamtea.eclipticseasons.common.registry;

import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.common.misc.HeatStrokeEffect;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public final class EffectRegistry {

    public static class Effects {
        public static final ResourceKey<MobEffect> HEAT_STROKE = ResourceKey.create(
                Registries.MOB_EFFECT,
                EclipticSeasons.rl("heat_stroke")
        );
    }

    public static final MobEffect HEAT_STROKE = new HeatStrokeEffect(MobEffectCategory.NEUTRAL, 0xf9d27d);

    public static void init() {
        register(Effects.HEAT_STROKE, HEAT_STROKE);
    }

    private static <T extends MobEffect> T register(ResourceKey<MobEffect> key, T effect) {
        return Registry.register(BuiltInRegistries.MOB_EFFECT, key, effect);
    }
}