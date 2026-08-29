package com.teamtea.eclipticseasons.compat.distanthorizons;

import com.teamtea.eclipticseasons.client.lod.SeasonalModelEntry;

public interface IDhClientBlockStateTextureCache {
    BlockFaceTexture[] eclipticseasons$bakeSeasonalModel(
            BlockState state,
            SeasonalModelEntry entry
    );
}
