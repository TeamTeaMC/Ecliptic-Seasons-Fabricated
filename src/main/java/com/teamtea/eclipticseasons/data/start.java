package com.teamtea.eclipticseasons.data;

import com.teamtea.eclipticseasons.data.datapack.DatapackRegistryGenerator;
import com.teamtea.eclipticseasons.data.general.advancement.ESAdvancementGenerator;
import com.teamtea.eclipticseasons.data.general.loot.EclipticSeasonsGiftLootTables;
import com.teamtea.eclipticseasons.data.general.loot.EclipticSeasonsLootTableProvider;
import com.teamtea.eclipticseasons.data.general.recipe.ESRecipeProvider;
import com.teamtea.eclipticseasons.data.general.tag.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class start implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(@NonNull FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();


        // CompletableFuture<RegistrySetBuilder.PatchedRegistries> tradeRebalanceWorldRegistries = TradeRebalanceRegistries.createPatchedWorldRegistries(
        //         registriesFuture
        // );
        // RegistrySetBuilder.PatchedRegistries::patches
        // CompletableFuture<RegistrySetBuilder.PatchedRegistries> tradeRebalanceReloadableRegistries = TradeRebalanceRegistries.createPatchedReloadable(
        //         tradeRebalanceWorldRegistries.thenApply(RegistrySetBuilder.PatchedRegistries::full), registriesFuture
        // );
        pack.addProvider(DatapackRegistryGenerator::new);
        pack.addProvider(ESRecipeProvider.Runner::new);
        pack.addProvider(ESAdvancementGenerator::new);
        EclipticSeasonsLootTableProvider.addAll(pack::addProvider);

        //tag
        pack.addProvider(CropClimateTagsDataProvider::new);
        pack.addProvider(EffectTagsDataProvider::new);
        pack.addProvider(EnhancementTagsDataProvider::new);
        pack.addProvider(ESBlockTagProvider::new);
        pack.addProvider(ESEntityTypeTagsProvider::new);
        pack.addProvider(ESItemTagProvider::new);
        pack.addProvider(TagsDataProvider::new);
        pack.addProvider(TimeLineTagDataProvider::new);
    }

    @Override
    public void buildRegistry(@NonNull RegistrySetBuilder registryBuilder) {
        // DatapackRegistryGenerator.apply(registryBuilder);
        // registryBuilder.entries.removeIf(entry ->
        //         entry.requiredRegistries().toList().contains(Registries.ADVANCEMENT));
        // registryBuilder.entries.removeIf(entry ->
        //         entry.requiredRegistries().toList().contains(Registries.RECIPE));
        registryBuilder.entries.addAll(DatapackRegistryGenerator.REGISTRY_SET_BUILDER.entries);
    }

    // private static <T extends DataProvider> DataProvider.Factory<T> bindRegistries(
    //         final BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, T> target, final CompletableFuture<HolderLookup.Provider> registries
    // ) {
    //     return output -> target.apply(output, registries);
    // }

    // public static HolderLookup.Provider createReloadableLookup() {
    //     RegistryAccess.Frozen staticRegistries = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
    //     HolderLookup.Provider newRegistries = DatapackRegistryGenerator.REGISTRY_SET_BUILDER.build(staticRegistries);
    //     validateLootData(newRegistries);
    //     return newRegistries;
    // }

}
