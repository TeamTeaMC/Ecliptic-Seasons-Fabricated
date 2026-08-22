package com.teamtea.eclipticseasons.compat.voxy;

import com.teamtea.eclipticseasons.api.event.SolarTermChangeEvent;
import com.teamtea.eclipticseasons.compat.CompatModule;
import me.cortex.voxy.client.core.IVoxyRenderSystemHolder;
import net.minecraft.client.Minecraft;

public class VoxyEsHandler {

    public static final VoxyEsHandler INSTANCE = new VoxyEsHandler();

    public void onSolarTermChangeEvent(SolarTermChangeEvent event) {
        if (event.getLevel() == Minecraft.getInstance().level
                && CompatModule.CommonConfig.voxyReloadWhenSeasonChanged.get()) {
            try {
                IVoxyRenderSystemHolder levelRenderer = (IVoxyRenderSystemHolder)
                        Minecraft.getInstance().levelRenderer;
                levelRenderer.voxy$shutdownRenderer();
                levelRenderer.voxy$createRenderer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
