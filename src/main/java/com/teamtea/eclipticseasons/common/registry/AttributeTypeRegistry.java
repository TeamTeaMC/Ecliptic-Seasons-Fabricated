package com.teamtea.eclipticseasons.common.registry;

import com.mojang.serialization.Codec;
import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.api.constant.solar.Season;
import com.teamtea.eclipticseasons.api.constant.solar.SolarTerm;
import com.teamtea.eclipticseasons.api.constant.solar.TimePeriod;
import com.teamtea.eclipticseasons.api.util.codec.ESExtraCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.attribute.AttributeType;
import net.minecraft.world.attribute.LerpFunction;
import net.minecraft.world.attribute.modifier.AttributeModifier;

public class AttributeTypeRegistry {

    // 1. 定义实例（保持原有的逻辑）
    public static final AttributeType<SolarTerm> SOLAR_TERM_TYPE =
            AttributeType.ofNotInterpolated(ESExtraCodec.SOLAR_TERM);

    public static final AttributeType<Season> SEASON_TYPE =
            AttributeType.ofNotInterpolated(ESExtraCodec.SEASON);

    public static final AttributeType<TimePeriod> TIME_PERIOD_TYPE =
            AttributeType.ofNotInterpolated(ESExtraCodec.TIME_PERIOD);

    public static final AttributeType<Float> HUMIDITY_TYPE =
            AttributeType.ofInterpolated(
                    Codec.FLOAT,
                    AttributeModifier.FLOAT_LIBRARY,
                    LerpFunction.ofFloat()
            );

    public static final AttributeType<Float> RAINFALL_TYPE =
            AttributeType.ofInterpolated(
                    Codec.FLOAT,
                    AttributeModifier.FLOAT_LIBRARY,
                    LerpFunction.ofFloat()
            );

    public static final AttributeType<Float> TEMPERATURE_TYPE =
            AttributeType.ofInterpolated(
                    Codec.FLOAT,
                    AttributeModifier.FLOAT_LIBRARY,
                    LerpFunction.ofFloat()
            );

    public static final AttributeType<SolarTerm> SOLAR_TERM = register("solar_term", SOLAR_TERM_TYPE);
    public static final AttributeType<Season> SEASON = register("season", SEASON_TYPE);
    public static final AttributeType<TimePeriod> TIME_PERIOD = register("time_period", TIME_PERIOD_TYPE);
    public static final AttributeType<Float> HUMIDITY = register("humidity", HUMIDITY_TYPE);
    public static final AttributeType<Float> RAINFALL = register("rainfall", RAINFALL_TYPE);
    public static final AttributeType<Float> TEMPERATURE = register("temperature", TEMPERATURE_TYPE);

    private static <T, A extends AttributeType<T>> A register(String name, A type) {
        var id = EclipticSeasons.rl( name);
        ResourceKey<AttributeType<?>> key = ResourceKey.create(Registries.ATTRIBUTE_TYPE, id);

        return Registry.register(BuiltInRegistries.ATTRIBUTE_TYPE, key, type);
    }

    public static void init() {
    }
}