package com.teamtea.eclipticseasons.data.general.loot;

import com.teamtea.eclipticseasons.common.registry.ESLootTables;
import com.teamtea.eclipticseasons.common.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class EclipticSeasonsGiftLootTables extends SimpleFabricLootTableSubProvider {

    public EclipticSeasonsGiftLootTables(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture, LootContextParamSets.GIFT);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        // output.accept(
        //         ESLootTables.spring_greenhouse_essence,
        //         LootTable.lootTable()
        //                 .withPool(
        //                         LootPool.lootPool()
        //                                 // .when(SeasonCondition.builder(SeasonCondition.Slice.builder().season(Season.SPRING).build()))
        //                                 .setRolls(ConstantValue.exactly(1.0F))
        //                                 .add(LootItem.lootTableItem(ItemRegistry.spring_greenhouse_essence_item.get()).setWeight(10))
        //                 )
        // );
        //
        // output.accept(
        //         ESLootTables.summer_greenhouse_essence,
        //         LootTable.lootTable()
        //                 .withPool(
        //                         LootPool.lootPool()
        //                                 .setRolls(ConstantValue.exactly(1.0F))
        //                                 .add(LootItem.lootTableItem(ItemRegistry.summer_greenhouse_essence_item.get()).setWeight(10))
        //                 )
        // );
        //
        // output.accept(
        //         ESLootTables.autumn_greenhouse_essence,
        //         LootTable.lootTable()
        //                 .withPool(
        //                         LootPool.lootPool()
        //                                 .setRolls(ConstantValue.exactly(1.0F))
        //                                 .add(LootItem.lootTableItem(ItemRegistry.autumn_greenhouse_essence_item.get()).setWeight(10))
        //                 )
        // );
        //
        // output.accept(
        //         ESLootTables.winter_greenhouse_essence,
        //         LootTable.lootTable()
        //                 .withPool(
        //                         LootPool.lootPool()
        //                                 .setRolls(ConstantValue.exactly(1.0F))
        //                                 .add(LootItem.lootTableItem(ItemRegistry.winter_greenhouse_essence_item.get()).setWeight(10))
        //                 )
        // );

        output.accept(
                ESLootTables.snowless_hometown,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(ItemRegistry.snowless_hometown).setWeight(10))
                        )
        );


    }

    @Override
    public void run() {
    }
}
