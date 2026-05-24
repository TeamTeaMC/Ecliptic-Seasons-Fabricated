package com.teamtea.eclipticseasons.client.mixin.client;


import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamtea.eclipticseasons.client.render.worldui.GrowthWorldUiRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.util.profiling.ProfilerFiller;
import org.joml.Matrix4fc;
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
            method = {"lambda$addMainPass$0"},
            at = @At(value = "INVOKE",
                    // ordinal = 1,
                    shift = At.Shift.AFTER,
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;submitBlockDestroyAnimation(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/LevelRenderState;)V")
    )
    private void eclipticseasons$lambda$addMainPass$0(GpuBufferSlice terrainFog, LevelRenderState levelRenderState, ProfilerFiller profiler, ChunkSectionsToRender chunkSectionsToRender, ResourceHandle entityOutlineTarget, ResourceHandle translucentTarget, ResourceHandle mainTarget, ResourceHandle itemEntityTarget, ResourceHandle particleTarget, boolean renderOutline, Matrix4fc modelViewMatrix, CallbackInfo ci,
                                                      @Local(name = "poseStack") PoseStack poseStack
    ) {
        GrowthWorldUiRenderer.renderLevelStage(poseStack, this.submitNodeStorage);
    }

}
