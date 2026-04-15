package com.teamtea.eclipticseasons.api.misc;

import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;



@ApiStatus.Internal
public interface IBlockStateFlagger {

    int getBlockTypeFlag();

    void setBlockTypeFlag(int flag);

    BlockState es$asState();

    boolean forceTickControl();

    void setForceTickControl(boolean force);
}
