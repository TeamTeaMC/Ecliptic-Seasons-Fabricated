package com.teamtea.eclipticseasons.common.registry;

import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.api.data.season.SeasonCycle;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class SeasonCycleRegistry {

    public static final ResourceKey<SeasonCycle> MONSOON = createKey("monsoon");
    // public static final ResourceKey<SeasonCycle> RAINY = createKey("rainy");
    // public static final ResourceKey<SeasonCycle> DESERT = createKey("desert");
    public static final ResourceKey<SeasonCycle> COLD = createKey("cold");
    public static final ResourceKey<SeasonCycle> HOT = createKey("hot");

    private static ResourceKey<SeasonCycle> createKey(String name) {
        return ResourceKey.create(ESRegistries.SEASON_CYCLE, EclipticSeasons.rl(name));
    }


    public static void bootstrap(BootstrapContext<SeasonCycle> context) {

    }
}
