package com.teamtea.eclipticseasons.compat.sodium;


import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.resources.model.geometry.BakedQuad;

import java.util.List;

public interface SodiumStatus {
    void eclipticseasons$bindCounter(SodiumBoard sodiumBoard);

    List<BakedQuad> getCacheBakeQuad();

    BlockStateModel getSnowModel();

    void setShouldCollect(boolean shouldCollect);

    boolean shouldCollect();

    ;
}
