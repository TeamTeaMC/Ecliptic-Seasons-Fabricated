package com.teamtea.eclipticseasons.compat.voxy.helper;

import net.minecraft.resources.Identifier;

public record SeasonalModelEntry(
        int originalBlockId,
        Identifier modelIdentifier,
        boolean snowy
) {
}
