package com.teamtea.eclipticseasons.api.misc.client;

import com.teamtea.eclipticseasons.compat.vanilla.IExtendBlockView;
import net.minecraft.core.BlockPos;
import net.minecraft.client.renderer.block.BlockAndTintGetter;

public interface IMapSlice extends BlockAndTintGetter, IMapSliceProvider, IExtendBlockView, IFakeSnowHolder {
    int getBlockHeight(BlockPos pos);

    int getSurfaceFaceBiomeId(BlockPos pos);

    default int getSnowyStatus(BlockPos pos) {
        return 0;
    }

    default boolean isSnowyBlock(BlockPos pos) {
        return false;
    }

    default void forceMapSliceUpdate() {
    }

}
