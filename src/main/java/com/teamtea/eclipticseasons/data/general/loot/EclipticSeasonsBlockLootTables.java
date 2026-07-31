package com.teamtea.eclipticseasons.data.general.loot;

import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import com.teamtea.eclipticseasons.common.registry.BlockRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class EclipticSeasonsBlockLootTables extends FabricBlockLootSubProvider  {

    private final Map<ResourceKey<LootTable>, LootTable.Builder> map = new HashMap<>();

    protected EclipticSeasonsBlockLootTables(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    // @Override
    // protected @NonNull Iterable<Block> getKnownBlocks() {
    //     return map.keySet()
    //             .stream()
    //             .map(lootTableResourceKey -> BuiltInRegistries.BLOCK.stream()
    //                     .filter(block ->
    //                     {
    //                         Optional<ResourceKey<LootTable>> lootTable = block.getLootTable();
    //                         return lootTable.isPresent() && lootTable.get().equals(lootTableResourceKey);
    //                     })
    //                     .findFirst())
    //             .filter(Optional::isPresent)
    //             .map(Optional::get)
    //             .toList();
    // }

    @Override
    public void add(@NonNull Block block, LootTable.@NonNull Builder builder) {
        super.add(block, builder);
        // Copy
        this.map.put(block.getLootTable().orElseThrow(() -> new IllegalStateException("Block " + block + " does not have loot table")), builder);
    }

    protected void dropSelfWithContents(Set<Block> blocks) {
        for (Block block : blocks) {
            // if (skipBlocks.contains(block)) {
            //     continue;
            // }
            add(block, createSingleItemTable(block));
        }
    }

    @Override
    public void generate() {
        Set<Block> blocks = BuiltInRegistries.BLOCK.stream()
                .filter(block -> EclipticSeasonsApi.MODID.equals(BuiltInRegistries.BLOCK.getKey(block).getNamespace()))
                .filter(block -> block.getLootTable().isPresent())
                .filter(block -> block.asItem() != Items.AIR)
                .collect(Collectors.toSet());

        dropSelfWithContents(blocks);

        // dropWhenSilkTouch(BlockRegistry.greenhouse_core_container.get());
        //
        // createCoreDrop(BlockRegistry.spring_greenhouse_core.get(), ItemRegistry.spring_greenhouse_essence_item.get());
        // createCoreDrop(BlockRegistry.summer_greenhouse_core.get(), ItemRegistry.summer_greenhouse_essence_item.get());
        // createCoreDrop(BlockRegistry.autumn_greenhouse_core.get(), ItemRegistry.autumn_greenhouse_essence_item.get());
        // createCoreDrop(BlockRegistry.winter_greenhouse_core.get(), ItemRegistry.winter_greenhouse_essence_item.get());

        // dropOther(BlockRegistry.block_in_copper_grate_block.get(), Blocks.COPPER_GRATE);
        // dropOther(BlockRegistry.block_in_exposed_copper_grate_block.get(), Blocks.EXPOSED_COPPER_GRATE);
        // dropOther(BlockRegistry.block_in_weathered_copper_grate_block.get(), Blocks.WEATHERED_COPPER_GRATE);
        // dropOther(BlockRegistry.block_in_oxidized_copper_grate_block.get(), Blocks.OXIDIZED_COPPER_GRATE);
        // dropOther(BlockRegistry.block_in_waxed_copper_grate_block.get(), Blocks.WAXED_COPPER_GRATE);
        // dropOther(BlockRegistry.block_in_waxed_exposed_copper_grate_block.get(), Blocks.WAXED_EXPOSED_COPPER_GRATE);
        // dropOther(BlockRegistry.block_in_waxed_weathered_copper_grate_block.get(), Blocks.WAXED_WEATHERED_COPPER_GRATE);
        // dropOther(BlockRegistry.block_in_waxed_oxidized_copper_grate_block.get(), Blocks.WAXED_OXIDIZED_COPPER_GRATE);

        dropOther(BlockRegistry.snow_cauldron, Blocks.CAULDRON);
        dropOther(BlockRegistry.ice_cauldron, Blocks.CAULDRON);

    }


    // protected void createCoreDrop(Block pBlock, Item pItem) {
    //     add(pBlock,
    //             LootTable.lootTable()
    //                     .withPool(LootPool.lootPool()
    //                             .setRolls(ConstantValue.exactly(1.0F))
    //                             .when(MatchBlock.blockMatches(BlockPredicate.Builder.block().of(BuiltInRegistries.BLOCK,pBlock))
    //                                     .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GreenHouseCoreBlock.AGE, GreenHouseCoreBlock.MAX_STAGE)))
    //                             .add(LootItem.lootTableItem(pBlock)
    //                                     .when(this.hasSilkTouch()).otherwise(LootItem.lootTableItem(pItem).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))))
    //     );
    // }

}
