package com.teamtea.eclipticseasons.client.mixin.client.gui;


import com.llamalad7.mixinextras.sugar.Local;
import com.teamtea.eclipticseasons.api.misc.BasicWeather;
import com.teamtea.eclipticseasons.client.debug.OverlayEventHandler;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Gui.class})
public abstract class MixinGui implements BasicWeather {


    @Inject(at = {@At(value = "TAIL")},
            method = {"extractRenderState"})
    private void eclipticseasons$extractRenderState_debug_render(
            DeltaTracker deltaTracker, boolean shouldRenderLevel,
            boolean resourcesLoaded, CallbackInfo ci,
            @Local(name = "graphics") GuiGraphicsExtractor graphics) {
        if (shouldRenderLevel)
            OverlayEventHandler.onEvent(graphics);
    }
}
