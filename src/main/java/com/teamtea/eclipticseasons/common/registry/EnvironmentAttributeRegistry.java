package com.teamtea.eclipticseasons.common.registry;

import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.api.constant.solar.Season;
import com.teamtea.eclipticseasons.api.constant.solar.SolarTerm;
import com.teamtea.eclipticseasons.api.constant.solar.TimePeriod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.attribute.AttributeTypes;
import net.minecraft.world.attribute.EnvironmentAttribute;

public class EnvironmentAttributeRegistry {

    public static final EnvironmentAttribute<SolarTerm> SOLAR_TERM_ATTRIBUTE =
            EnvironmentAttribute.builder(AttributeTypeRegistry.SOLAR_TERM_TYPE)
                    .defaultValue(SolarTerm.NONE)
                    .notPositional()
                    .syncable()
                    .build();

    public static final EnvironmentAttribute<Season> SEASON_ATTRIBUTE =
            EnvironmentAttribute.builder(AttributeTypeRegistry.SEASON_TYPE)
                    .defaultValue(Season.NONE)
                    .notPositional()
                    .syncable()
                    .build();

    public static final EnvironmentAttribute<TimePeriod> TIME_PERIOD_ATTRIBUTE =
            EnvironmentAttribute.builder(AttributeTypeRegistry.TIME_PERIOD_TYPE)
                    .defaultValue(TimePeriod.NONE)
                    .syncable()
                    .notPositional()
                    .build();

    public static final EnvironmentAttribute<Float> HUMIDITY_ATTRIBUTE =
            EnvironmentAttribute.builder(AttributeTypes.FLOAT)
                    .defaultValue(0f)
                    .build();

    public static final EnvironmentAttribute<Boolean> SEASONAL_WORLD_ATTRIBUTE =
            EnvironmentAttribute.builder(AttributeTypes.BOOLEAN)
                    .defaultValue(false)
                    .syncable()
                    .notPositional()
                    .build();

    public static final EnvironmentAttribute<Integer> SOLAR_DAY_ATTRIBUTE =
            EnvironmentAttribute.builder(AttributeTypes.INTEGER)
                    .defaultValue(0)
                    .syncable()
                    .notPositional()
                    .build();

    public static final EnvironmentAttribute<SolarTerm> SOLAR_TERM = register("solar_term", SOLAR_TERM_ATTRIBUTE);
    public static final EnvironmentAttribute<Season> SEASON = register("season", SEASON_ATTRIBUTE);
    public static final EnvironmentAttribute<TimePeriod> TIME_PERIOD = register("time_period", TIME_PERIOD_ATTRIBUTE);
    public static final EnvironmentAttribute<Float> HUMIDITY = register("humidity", HUMIDITY_ATTRIBUTE);
    public static final EnvironmentAttribute<Boolean> SEASONAL_WORLD = register("seasonal_world", SEASONAL_WORLD_ATTRIBUTE);
    public static final EnvironmentAttribute<Integer> SOLAR_DAY = register("solar_day", SOLAR_DAY_ATTRIBUTE);

    private static <T, A extends EnvironmentAttribute<T>> A register(String name, A attribute) {
        var id = EclipticSeasons.rl( name);
        ResourceKey<EnvironmentAttribute<?>> key = ResourceKey.create(Registries.ENVIRONMENT_ATTRIBUTE, id);

        return Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, key, attribute);
    }

    public static void init() {
    }
}