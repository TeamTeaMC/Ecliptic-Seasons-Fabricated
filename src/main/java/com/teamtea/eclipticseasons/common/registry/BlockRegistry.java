package com.teamtea.eclipticseasons.common.registry;

import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.common.block.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

public class BlockRegistry {

    // 1. 定义日历和湿度计
    public static final Block calendar = register("calendar",
            p -> new CalendarBlock(p.strength(0.5f).sound(SoundType.WOOD).noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final Block hygrometer = register("hygrometer",
            p -> new HygrometerBlock(p.strength(0.5f).sound(SoundType.AMETHYST).noOcclusion().pushReaction(PushReaction.DESTROY).randomTicks()));

    // 2. 锅釜系列 (使用 ofLegacyCopy 对应原本逻辑)
    public static final Block snow_cauldron = register("snow_cauldron",
            p -> new IceOrSnowCauldronBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CAULDRON).setId(key("snow_cauldron"))));

    public static final Block ice_cauldron = register("ice_cauldron",
            p -> new IceOrSnowCauldronBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CAULDRON).setId(key("ice_cauldron"))));

    // 3. 积雪系列 (使用 ofFullCopy 对应原本逻辑)
    public static final Block snowyLeaves = register("snowy_leaves",
            p -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SNOW_BLOCK).setId(key("snowy_leaves")).dynamicShape().noOcclusion()));

    public static final Block snowyBlock = register("snowy_block",
            p -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SNOW_BLOCK).setId(key("snowy_block")).dynamicShape().noOcclusion()));

    public static final Block snowyStairs = register("snowy_stairs",
            p -> new StairBlock(Blocks.OAK_PLANKS.defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).setId(key("snowy_stairs")).dynamicShape().noOcclusion()));

    public static final Block snowySlab = register("snowy_slab",
            p -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).setId(key("snowy_slab")).dynamicShape().noOcclusion()));

    public static final Block snowyVine = register("snowy_vine",
            p -> new VineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE).setId(key("snowy_vine")).dynamicShape().noOcclusion()));



    private static ResourceKey<Block> key(String name) {
        return ResourceKey.create(Registries.BLOCK, EclipticSeasons.rl(name));
    }

    private static <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> factory) {
        ResourceKey<Block> key = key(name);
        BlockBehaviour.Properties props = BlockBehaviour.Properties.of().setId(key);
        return Registry.register(BuiltInRegistries.BLOCK, key, factory.apply(props));
    }

    public static void init() {
        // 触发类加载
    }
}