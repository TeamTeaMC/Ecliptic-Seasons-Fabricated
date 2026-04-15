package com.teamtea.eclipticseasons.common.registry;


import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.api.data.climate.AgroClimaticZone;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class AgroClimateRegistry {

    /**
     * Temperate Climate is the standard Crop.
     **/

    public static final ResourceKey<AgroClimaticZone> TEMPERATE = createKey("temperate");
    public static final ResourceKey<AgroClimaticZone> COLD = createKey("cold");
    public static final ResourceKey<AgroClimaticZone> HOT = createKey("hot");
    // public static final ResourceKey<AgroClimaticZone> DESERT = createKey("desert");
    public static final ResourceKey<AgroClimaticZone> NETHER = createKey("nether");
    public static final ResourceKey<AgroClimaticZone> END = createKey("end");


    private static ResourceKey<AgroClimaticZone> createKey(String name) {
        return ResourceKey.create(ESRegistries.AGRO_CLIMATE, EclipticSeasons.rl(name));
    }

    public static void bootstrap(BootstrapContext<AgroClimaticZone> context) {

    }

}
