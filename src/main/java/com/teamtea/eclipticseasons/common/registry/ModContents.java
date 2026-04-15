package com.teamtea.eclipticseasons.common.registry;

import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import com.teamtea.eclipticseasons.api.data.misc.ESSortInfo;
import com.teamtea.eclipticseasons.api.data.season.SeasonCycle;
import com.teamtea.eclipticseasons.api.data.season.definition.SeasonDefinition;
import com.teamtea.eclipticseasons.api.data.season.SeasonPhase;
import com.teamtea.eclipticseasons.api.data.season.SnowDefinition;
import com.teamtea.eclipticseasons.api.data.climate.AgroClimaticZone;
import com.teamtea.eclipticseasons.api.data.climate.BiomesClimateSettings;
import com.teamtea.eclipticseasons.api.data.craft.WetterStructure;
import com.teamtea.eclipticseasons.api.data.crop.CropGrowControlBuilder;
import com.teamtea.eclipticseasons.api.data.weather.CustomRainBuilder;
import com.teamtea.eclipticseasons.api.data.weather.CustomSnowTerm;
import com.teamtea.eclipticseasons.api.data.weather.WeatherDimension;
import com.teamtea.eclipticseasons.api.data.weather.WeatherRegion;
import com.teamtea.eclipticseasons.api.data.weather.special_effect.WeatherEffect;
import com.teamtea.eclipticseasons.compat.Platform;
import com.teamtea.eclipticseasons.config.CommonConfig;
import com.teamtea.eclipticseasons.config.StartConfig;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;


public class ModContents {

    public static final ResourceKey<CreativeModeTab> MAIN_TAB_KEY = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            EclipticSeasons.rl(EclipticSeasonsApi.MODID)
    );

    public static final CreativeModeTab MAIN_TAB = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            MAIN_TAB_KEY,
            FabricCreativeModeTab.builder()
                    .icon(() -> new ItemStack(ItemRegistry.calendar_item))
                    .title(Component.translatable("itemGroup." + EclipticSeasonsApi.MODID + ".core"))
                    .displayItems((params, output) -> {
                        output.accept(ItemRegistry.hygrometer);
                        output.accept(ItemRegistry.growth_detector);
                        output.accept(ItemRegistry.calendar_item);
                        output.accept(ItemRegistry.snowless_hometown);
                    })
                    .build()
    );

    public static void onNewRegistry() {
        DynamicRegistries.registerSynced(ESRegistries.WETTER, WetterStructure.CODEC);
        DynamicRegistries.registerSynced(ESRegistries.BIOME_CLIMATE_SETTING, BiomesClimateSettings.CODEC);
        DynamicRegistries.registerSynced(ESRegistries.CROP, CropGrowControlBuilder.CODEC);
        DynamicRegistries.registerSynced(ESRegistries.AGRO_CLIMATE, AgroClimaticZone.CODEC);
        DynamicRegistries.registerSynced(ESRegistries.SNOW_DEFINITIONS, SnowDefinition.CODEC);
        DynamicRegistries.registerSynced(ESRegistries.SEASON_PHASE, SeasonPhase.CODEC);
        DynamicRegistries.registerSynced(ESRegistries.SEASON_CYCLE, SeasonCycle.CODEC);
        DynamicRegistries.registerSynced(ESRegistries.SNOW_TERM, CustomSnowTerm.CODEC);
        DynamicRegistries.registerSynced(ESRegistries.SEASON_DEFINITION, SeasonDefinition.CODEC);
        DynamicRegistries.registerSynced(ESRegistries.WEATHER_REGION, WeatherRegion.CODEC);
        DynamicRegistries.registerSynced(ESRegistries.EXTRA_INFO, ESSortInfo.CODEC);
        DynamicRegistries.registerSynced(ESRegistries.WEATHER_EFFECT, WeatherEffect.CODEC);
        DynamicRegistries.registerSynced(ESRegistries.BIOME_RAIN, CustomRainBuilder.CODEC);
        DynamicRegistries.registerSynced(ESRegistries.WEATHER_DIMENSION, WeatherDimension.CODEC);
    }


    public static void registerBuiltinResourcePacks() {
        ModContainer container = FabricLoader.getInstance()
                .getModContainer(EclipticSeasonsApi.MODID)
                .orElseThrow();


        if (Platform.isPhysicalClient()) {
            ResourceLoader.registerBuiltinPack(
                    EclipticSeasons.rl("legacy_snowy_block"),
                    container,
                    Component.translatable("pack.eclipticseasons.legacy_snowy_block"),
                    PackActivationType.NORMAL
            );
        }


        // if (StartConfig.Resource.extraSnow.get()) {
        //     ResourceLoader.registerBuiltinPack(
        //             EclipticSeasons.rl("extra_snow"),
        //             container,
        //             PackActivationType.DEFAULT_ENABLED
        //     );
        // }


        // registerIf(container, "rain_together", CommonConfig.Resource.RainTogether.get());
        // registerIf(container, "regional_snow_time", CommonConfig.Resource.RegionalSnowTime.get());
        // registerIf(container, "snow_together", CommonConfig.Resource.SnowTogether.get());
        // registerIf(container, "vanilla_biome_climate_settings", CommonConfig.Resource.VanillaBiomeClimateSettings.get());
        // registerIf(container, "not_ignore_river", CommonConfig.Resource.NotIgnoreRiver.get());
    }


    private static void registerIf(ModContainer container, String path, boolean enabled) {
        if (enabled) {
            var id = EclipticSeasons.rl(path);
            ResourceLoader.registerBuiltinPack(
                    id,
                    container,
                    Component.translatable("pack." + id.getNamespace() + "." + id.getPath()),
                    PackActivationType.DEFAULT_ENABLED
            );
        }
    }
}
