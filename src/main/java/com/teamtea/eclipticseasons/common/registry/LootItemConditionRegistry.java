package com.teamtea.eclipticseasons.common.registry;

import com.mojang.serialization.MapCodec;
import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.common.loot.SeasonCondition;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootItemConditionRegistry {

    public static final  MapCodec<? extends LootItemCondition> SEASON = register("season", SeasonCondition.CODEC);


    private static MapCodec<? extends LootItemCondition> register(String name, MapCodec<? extends LootItemCondition> codec) {
        var id = EclipticSeasons.rl(name);
        ResourceKey<MapCodec<? extends LootItemCondition>> key = ResourceKey.create(Registries.LOOT_CONDITION_TYPE, id);
        return Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, key, codec);
    }

    public static void init() {
    }
}
