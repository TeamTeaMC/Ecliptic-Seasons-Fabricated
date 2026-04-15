package com.teamtea.eclipticseasons.client.registry;

import com.mojang.blaze3d.platform.InputConstants;
import com.teamtea.eclipticseasons.EclipticSeasons;
import net.minecraft.client.KeyMapping;

public class KeyMappingRegistry {
    public static final KeyMapping.Category MAIN = KeyMapping.Category.register(EclipticSeasons.rl("main"));
    public static final KeyMapping DEBUG_KEY = new KeyMapping(
            EclipticSeasons.rl("main/debug").toLanguageKey("keys"),
            InputConstants.KEY_E, KeyMappingRegistry.MAIN
    );
}
