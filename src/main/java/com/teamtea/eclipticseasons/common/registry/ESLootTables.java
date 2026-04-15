package com.teamtea.eclipticseasons.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class ESLootTables {
    public static final ResourceKey<LootTable> snowless_hometown = ResourceKey.create(Registries.LOOT_TABLE, ItemRegistry.snowless_hometown.builtInRegistryHolder().key().identifier().withPrefix("gifts/"));

}
