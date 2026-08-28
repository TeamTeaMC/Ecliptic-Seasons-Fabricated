package com.teamtea.eclipticseasons.compat.voxy.helper;

import org.jspecify.annotations.Nullable;

public interface IVoxyModelController {
    boolean isSnowyBlock();

    void setSnowyBlock(boolean snowyBlock);

    @Nullable SeasonalModelEntry getSeasonalModel();

    void setSeasonalModel(@Nullable SeasonalModelEntry seasonalModel);
}
