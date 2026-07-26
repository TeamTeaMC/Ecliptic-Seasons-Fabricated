package com.teamtea.eclipticseasons.client.mixin.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.teamtea.eclipticseasons.client.render.worldui.GrowthWorldUiRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {

    @Shadow
    @Final
    private SubmitNodeStorage submitNodeStorage;

    @Inject(
            method = {"submitBlockDestroyAnimation"},
            at = @At(value = "RETURN")
    )
    private void eclipticseasons$lambda$addMainPass$0(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, LevelRenderState levelRenderState, CallbackInfo ci
    ) {
        GrowthWorldUiRenderer.renderLevelStage(poseStack, this.submitNodeStorage);
    }

}
