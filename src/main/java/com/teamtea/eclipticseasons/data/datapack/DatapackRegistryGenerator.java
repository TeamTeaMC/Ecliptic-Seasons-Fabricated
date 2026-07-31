package com.teamtea.eclipticseasons.data.datapack;

import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import com.teamtea.eclipticseasons.common.registry.*;
import com.teamtea.eclipticseasons.data.general.advancement.Advancements;
import com.teamtea.eclipticseasons.data.general.recipe.ESRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.EmptyTagLookupWrapper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

import java.util.concurrent.CompletableFuture;

public class DatapackRegistryGenerator extends FabricDynamicRegistryProvider {

    public static final RegistrySetBuilder REGISTRY_SET_BUILDER = new RegistrySetBuilder()
            .add(ESRegistries.WETTER, WetterStructureRegistry::bootstrap)
            .add(ESRegistries.BIOME_CLIMATE_SETTING, BiomeClimateSettingsRegistry::bootstrap)
            .add(ESRegistries.CROP, CropRegistry::bootstrap)
            .add(ESRegistries.AGRO_CLIMATE, AgroClimateRegistry::bootstrap)
            // .add(ESRegistries.SEASON_QUEST, SeasonQuestRegistry::bootstrap)
            // .add(ESRegistries.HUMIDITY_CONTROL, HumidityControlRegistry::bootstrap)
            .add(ESRegistries.SNOW_DEFINITIONS, SnowDefinitionsRegistry::bootstrap)
            .add(ESRegistries.SEASON_PHASE, SeasonPhaseRegistry::bootstrap)
            .add(ESRegistries.SEASON_CYCLE, SeasonCycleRegistry::bootstrap)
            .add(Registries.JUKEBOX_SONG, SongRegistry::bootstrap)
            .add(ESRegistries.EXTRA_INFO, ESSortInfoRegistry::bootstrap)
            .add(Registries.TIMELINE, TimeLineRegistry::bootstrap)
            .add(ESRegistries.WEATHER_EFFECT, WeatherEffectRegistry::bootstrap)
            .add(ESRegistries.BIOME_RAIN, BiomeRainRegistry::bootstrap)
            .add(ESRegistries.SPECIAL_DAYS, SpecialDaysRegistry::bootstrap)
            // extra registries
            // .add(Registries.LOOT_TABLE, _ -> {})
            // .add(Registries.ADVANCEMENT,_ -> {})
            // .add(ESRecipeProvider.create())
    ;

    // public static void apply(RegistrySetBuilder registryBuilder) {
    //
    //     registryBuilder.add(ESRegistries.WETTER, WetterStructureRegistry::bootstrap)
    //             .add(ESRegistries.BIOME_CLIMATE_SETTING, BiomeClimateSettingsRegistry::bootstrap)
    //             .add(ESRegistries.CROP, CropRegistry::bootstrap)
    //             .add(ESRegistries.AGRO_CLIMATE, AgroClimateRegistry::bootstrap)
    //             // .add(ESRegistries.SEASON_QUEST, SeasonQuestRegistry::bootstrap)
    //             // .add(ESRegistries.HUMIDITY_CONTROL, HumidityControlRegistry::bootstrap)
    //             .add(ESRegistries.SNOW_DEFINITIONS, SnowDefinitionsRegistry::bootstrap)
    //             .add(ESRegistries.SEASON_PHASE, SeasonPhaseRegistry::bootstrap)
    //             .add(ESRegistries.SEASON_CYCLE, SeasonCycleRegistry::bootstrap)
    //             .add(Registries.JUKEBOX_SONG, SongRegistry::bootstrap)
    //             .add(ESRegistries.EXTRA_INFO, ESSortInfoRegistry::bootstrap)
    //             .add(Registries.TIMELINE, TimeLineRegistry::bootstrap)
    //             .add(ESRegistries.WEATHER_EFFECT, WeatherEffectRegistry::bootstrap)
    //             .add(ESRegistries.BIOME_RAIN, BiomeRainRegistry::bootstrap)
    //             .add(ESRegistries.SPECIAL_DAYS, SpecialDaysRegistry::bootstrap)
    //             .add(Registries.LOOT_TABLE, c->{})
    //             .add(Registries.ADVANCEMENT, new Advancements())
    //     ;
    // }


    public DatapackRegistryGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        // for (HolderLookup.RegistryLookup<?> registryLookup : registries.listRegistries().toList()) {
        //     entries.addAll(registryLookup);
        // }
        // for (HolderLookup.RegistryLookup<?> registryLookup : registries.listRegistries().toList()) {
        //     entries.addAll(registryLookup);
        // }
        for (RegistrySetBuilder.RegistryStub entry : REGISTRY_SET_BUILDER.entries) {
            for (ResourceKey<? extends Registry<?>> resourceKey : entry.requiredRegistries().toList()) {
                entries.addAll(registries.lookupOrThrow(resourceKey));
            }
        }
        // entries.addAll(registries.lookupOrThrow(ESRegistries.AGRO_CLIMATE));
    }

    @Override
    public String getName() {
        return EclipticSeasonsApi.MODID + " Dynamic";
    }

    // public DatapackRegistryGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    //     super(output, EclipticSeasonsApi.MODID, List.of(),
    //             registriesFuture.thenApply(
    //                     DatapackRegistryGenerator::createReloadableLookup
    //             )
    //             //         CompletableFuture.supplyAsync(
    //             //         DatapackRegistryGenerator.createReloadableLookup(registriesFuture), Util.backgroundExecutor()
    //             // )
    //     );
    // }
    //
    // public static HolderLookup.Provider createReloadableLookup(final HolderLookup.Provider context) {
    //         RegistryAccess.Frozen staticRegistries = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
    //         HolderLookup.Provider newRegistries = DatapackRegistryGenerator.REGISTRY_SET_BUILDER.build(staticRegistries);
    //     // HolderLookup.Provider c=context;
    //     // try {
    //     //    c = DatapackRegistryGenerator.REGISTRY_SET_BUILDER.build(newRegistries);
    //     // } catch (Exception e) {
    //     //     e.printStackTrace();
    //     // }
    //     return newRegistries;
    // }


}
