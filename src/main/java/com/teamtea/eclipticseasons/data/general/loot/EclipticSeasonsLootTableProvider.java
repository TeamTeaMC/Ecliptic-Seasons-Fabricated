package com.teamtea.eclipticseasons.data.general.loot;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


public class EclipticSeasonsLootTableProvider extends LootTableProvider {

    private final PackOutput generator;

    public EclipticSeasonsLootTableProvider(PackOutput generator, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(Set.of(), List.of(
        //         new SubProviderEntry(
        //         EclipticSeasonsBlockLootTables::new,
        //         // Loot table generator for the 'empty' param set
        //         LootContextParamSets.BLOCK
        // ),new SubProviderEntry(
        //         EclipticSeasonsGiftLootTables::new,
        //         // Loot table generator for the 'empty' param set
        //         LootContextParamSets.GIFT
        // )
        ));
        this.generator = generator;
    }

    public static void addAll(
            Consumer<FabricDataGenerator.Pack.RegistryDependentFactory<? extends DataProvider>> factory
    ) {
        factory.accept(EclipticSeasonsBlockLootTables::new);
        factory.accept(EclipticSeasonsGiftLootTables::new);
    }

}
