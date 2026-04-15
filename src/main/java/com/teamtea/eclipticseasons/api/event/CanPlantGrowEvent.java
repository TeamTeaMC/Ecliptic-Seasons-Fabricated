package com.teamtea.eclipticseasons.api.event;

import lombok.Builder;
import lombok.Data;
import net.minecraft.core.BlockPos;
import net.minecraft.util.TriState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The event is fired on the {@link com.teamtea.eclipticseasons.common.hook.ESEventHook}
 **/
@Data
@Builder
public class CanPlantGrowEvent  implements IESEvent{
    @Builder.Default
    private TriState result = TriState.DEFAULT;
    private final Level level;
    private final BlockPos pos;
    private final BlockState state;
}
