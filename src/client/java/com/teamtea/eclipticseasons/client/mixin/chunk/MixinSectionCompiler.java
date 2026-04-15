package com.teamtea.eclipticseasons.client.mixin.chunk;


import com.mojang.blaze3d.vertex.VertexSorting;
import com.teamtea.eclipticseasons.api.misc.client.IMapSlice;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.client.renderer.chunk.RenderSectionRegion;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.core.SectionPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SectionCompiler.class)
public abstract class MixinSectionCompiler {

    @Inject(
            method = "compile",
            at = @At(value = "HEAD"
            )
    )
    private void eclipticseasons$compile_init_chunk(SectionPos sectionPos, RenderSectionRegion region, VertexSorting vertexSorting, SectionBufferBuilderPack builders, CallbackInfoReturnable<SectionCompiler.Results> cir) {
        ((IMapSlice) region).forceMapSliceUpdate();
    }

}
