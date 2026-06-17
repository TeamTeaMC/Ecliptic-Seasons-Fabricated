package com.teamtea.eclipticseasons.client.mixin.client;

import com.mojang.blaze3d.platform.Window;
import com.teamtea.eclipticseasons.client.ClientEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraftClient {

    @Shadow
    @Final
    private Window window;

    @Shadow
    @Nullable
    public ClientLevel level;

    @Inject(
            method = "resizeGui",
            at = @At("RETURN")
    )
    private void experiment$resize(CallbackInfo ci) {
        // FogRenderer.INSTANCE.resize(window.getWidth(), window.getHeight());
    }

    @Inject(
            method = "Lnet/minecraft/client/Minecraft;disconnect(Lnet/minecraft/client/gui/screens/Screen;ZZ)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;close()V")
    )
    private void es$setLevel(Screen screen, boolean keepResourcePacks, boolean stopSound, CallbackInfo ci) {
        if (level != null) {
            ClientEventHandler.onLevelUnloadEvent(level);
        }
    }

}
