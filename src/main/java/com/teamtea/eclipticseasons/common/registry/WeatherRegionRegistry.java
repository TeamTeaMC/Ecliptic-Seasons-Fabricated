package com.teamtea.eclipticseasons.common.registry;

import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.api.data.weather.WeatherRegion;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class WeatherRegionRegistry {

    private static ResourceKey<WeatherRegion> createKey(String name) {
        return ResourceKey.create(ESRegistries.WEATHER_REGION, EclipticSeasons.rl(name));
    }

    public static void bootstrap2(BootstrapContext<WeatherRegion> context) {
    }
}
