package com.teamtea.eclipticseasons.compat.eclipticseasons_bundles;

import com.teamtea.eclipticseasons.compat.eclipticseasons_bundles.client.BundlesScreenDefinition;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class Bundles_ModMenuCompact implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return BundlesScreenDefinition.INSTANCE::create;
    }
}