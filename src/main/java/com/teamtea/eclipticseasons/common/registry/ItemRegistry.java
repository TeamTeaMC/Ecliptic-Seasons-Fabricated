package com.teamtea.eclipticseasons.common.registry;

import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.common.item.CalendarBlockItem;
import com.teamtea.eclipticseasons.common.item.GrowthDetectorItem;
import com.teamtea.eclipticseasons.common.item.MeterBlockItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Function;

public class ItemRegistry {

    public static final Item hygrometer = register("hygrometer",
            p -> new MeterBlockItem(BlockRegistry.calendar, p));

    public static final Item growth_detector = register("growth_detector",
            GrowthDetectorItem::new);

    public static final Item calendar_item = register("calendar",
            p -> new CalendarBlockItem(BlockRegistry.calendar, p));

    public static final Item snowless_hometown = register("snowless_hometown",
            p -> new Item(p.stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(SongRegistry.SNOWLESS_HOMETOWN)));



    private static ResourceKey<Item> key(String name) {
        return ResourceKey.create(Registries.ITEM, EclipticSeasons.rl(name));
    }

    private static <T extends Item> T register(String name, Function<Item.Properties, T> factory) {
        ResourceKey<Item> key = key(name);

        // 关键点：在 1.21.2+ 中，必须通过 Properties 的 setId 绑定 Key
        Item.Properties properties = new Item.Properties().setId(key);

        return Registry.register(BuiltInRegistries.ITEM, key, factory.apply(properties));
    }

    public static void init() {
    }
}