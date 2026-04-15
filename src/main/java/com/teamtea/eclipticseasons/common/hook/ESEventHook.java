package com.teamtea.eclipticseasons.common.hook;

import com.teamtea.eclipticseasons.api.event.*;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TriState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

public class ESEventHook {

    public static final Event<Tricker<BeforeCheckSnowStatusEvent>> BEFORE_CHECK_SNOW = create();

    public static final Event<Tricker<CanPlantGrowEvent>> CHECK_PLANT_GROWTH = create();

    public static final Event<Tricker<RegisterAndModifyCropInfoEvent>> MODIFY_CROP_INFO = create();

    public static final Event<Tricker<SolarTermChangeEvent>> SOLAR_TERM_CHANGE = create();

    public static boolean canExtraCropGrow(Level level, BlockPos pos, BlockState state, boolean def) {
        var ev = CanPlantGrowEvent.builder().level(level).pos(pos).state(state).build();
        CHECK_PLANT_GROWTH.invoker().onEvent(ev);
        return (ev.getResult() == TriState.TRUE || (ev.getResult() == TriState.DEFAULT && def));
    }

    public static BeforeCheckSnowStatusEvent modifySnowStatus(ServerLevel level, Holder<Biome> biome, BlockPos pos, boolean rain) {
        var ev = BeforeCheckSnowStatusEvent.builder()
                .level(level)
                .pos(pos)
                .biome(biome)
                .rain(rain)
                .status(null)
                .build();
        BEFORE_CHECK_SNOW.invoker().onEvent(ev);
        return ev;
    }


    @FunctionalInterface
    public interface Tricker<E extends IESEvent> {
        void onEvent(E event);
    }

    public static <E extends IESEvent> Event<Tricker<E>> create() {
        return EventFactory.createArrayBacked(
                Tricker.class,
                (listeners) -> (E event) -> {
                    for (Tricker<E> listener : listeners) {
                        listener.onEvent(event);
                    }
                }
        );
    }
}
