package com.teamtea.eclipticseasons.client.registry;

import com.mojang.blaze3d.platform.InputConstants;
import com.teamtea.eclipticseasons.EclipticSeasons;
import net.minecraft.client.KeyMapping;

public class KeyMappingRegistry {
    public static final KeyMapping.Category MAIN = KeyMapping.Category.register(EclipticSeasons.rl("main"));
    public static final KeyMapping DEBUG_KEY = new KeyMapping(
            EclipticSeasons.rl("main/debug").toLanguageKey("keys"),
            InputConstants.KEY_N, KeyMappingRegistry.MAIN
    );
    public static final KeyMapping DEBUG_KEY_1 = new KeyMapping(
            EclipticSeasons.rl("main/debug_1").toLanguageKey("keys"),
            InputConstants.KEY_LCONTROL, KeyMappingRegistry.MAIN
    );
}
