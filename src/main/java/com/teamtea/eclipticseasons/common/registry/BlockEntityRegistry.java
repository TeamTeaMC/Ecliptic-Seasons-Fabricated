package com.teamtea.eclipticseasons.common.registry;

import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.common.block.blockentity.CalendarBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRegistry {

    public static final BlockEntityType<CalendarBlockEntity> calendar_entity_type = register(
            "calendar",
            FabricBlockEntityTypeBuilder.create(CalendarBlockEntity::new, BlockRegistry.calendar).build()
    );

    private static <T extends BlockEntityType<?>> T register(String name, T type) {
        var id = EclipticSeasons.rl(name);
        ResourceKey<BlockEntityType<?>> key = ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, id);

        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, key, type);
    }

    public static void init() {
    }
}