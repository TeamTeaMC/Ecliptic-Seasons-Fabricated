package com.teamtea.eclipticseasons.common.registry;

import com.teamtea.eclipticseasons.EclipticSeasons;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;

public class SoundEventsRegistry {

    public static final SoundEvent SPRING_FOREST = register("ambient.spring_forest");
    public static final SoundEvent GARDEN_WIND = register("ambient.garden_wind");
    public static final SoundEvent NIGHT_RIVER = register("ambient.night_river");
    public static final SoundEvent WINDY_LEAVE = register("ambient.windy_leave");
    public static final SoundEvent WINTER_FOREST = register("ambient.winter_forest");
    public static final SoundEvent WINTER_COLD = register("ambient.winter_cold");
    public static final SoundEvent WIND_CHIMES = register("block.wind_chimes");
    public static final SoundEvent BAMBOO_WIND_CHIMES = register("block.bamboo_wind_chimes");
    public static final SoundEvent PAPER_WIND_CHIMES = register("block.paper_wind_chimes");
    public static final SoundEvent SNOWLESS_HOMETOWN = register("record.snowless_hometown");

    private static SoundEvent register(String name) {
        Identifier id = EclipticSeasons.rl(name);
        ResourceKey<SoundEvent> key = ResourceKey.create(Registries.SOUND_EVENT, id);

        SoundEvent event = SoundEvent.createVariableRangeEvent(id);

        return Registry.register(BuiltInRegistries.SOUND_EVENT, key, event);
    }

    public static void init() {
    }
}