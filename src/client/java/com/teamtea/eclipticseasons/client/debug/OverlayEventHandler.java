package com.teamtea.eclipticseasons.client.debug;

import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.client.registry.KeyMappingRegistry;
import com.teamtea.eclipticseasons.common.core.solar.SolarAngelHelper;
import com.teamtea.eclipticseasons.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;

public final class OverlayEventHandler {
    private static DebugInfoRenderer RENDERER;

    public static void onEvent(GuiGraphicsExtractor guiGraphics) {

        if (RENDERER == null) RENDERER = new DebugInfoRenderer(Minecraft.getInstance());
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        var level = mc.level;

        if (player != null && level != null && !mc.options.hideGui) {
            if (KeyMappingRegistry.DEBUG_KEY.consumeClick()) {
                ClientConfig.Debug.debugInfo.set(!ClientConfig.Debug.debugInfo.getAsBoolean());
            }

            if (ClientConfig.Debug.debugInfo.get() || ClientConfig.GUI.simpleSeasonHud.get()) {
                BlockPos pos = player.blockPosition();

                var solarTermsDay = EclipticUtil.getNowSolarDay(level);
                long dayTime = Math.floorMod(level.getDefaultClockTime(), EclipticUtil.getDayLengthInMinecraft(level));
                double envTemp = EclipticUtil.getTemperatureFloat(level, level.getBiome(pos).value(), pos);
                int solarTime = -1;
                solarTime = level.dimensionType().defaultClock().<Integer>map(
                        clockHolder ->
                                SolarAngelHelper.getSolarAngelTime(clockHolder, dayTime, EclipticUtil.getDayLengthInMinecraft(level))
                ).orElse(0);

                RENDERER.renderStatusBar(
                        guiGraphics,
                        mc.getWindow().getGuiScaledWidth(),
                        mc.getWindow().getGuiScaledHeight(),
                        level,
                        player,
                        String.valueOf(solarTermsDay),
                        dayTime,
                        envTemp,
                        solarTime
                );
            }
        }
    }
}