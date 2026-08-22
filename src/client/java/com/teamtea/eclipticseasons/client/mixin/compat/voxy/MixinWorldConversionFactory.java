package com.teamtea.eclipticseasons.client.mixin.compat.voxy;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.teamtea.eclipticseasons.common.mixin.condition.ConditionalMixin;
import com.teamtea.eclipticseasons.compat.voxy.VoxyTool;
import com.teamtea.eclipticseasons.compat.voxy.helper.IVoxyLevelProvider;
import me.cortex.voxy.common.voxelization.ILightingSupplier;
import me.cortex.voxy.common.voxelization.VoxelizedSection;
import me.cortex.voxy.common.voxelization.WorldConversionFactory;
import me.cortex.voxy.common.world.other.Mapper;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({WorldConversionFactory.class})
@ConditionalMixin(value = "voxy",version = "0.2.18-beta")
public abstract class MixinWorldConversionFactory {

    @WrapOperation(
            remap = false,
            method = "convert(Lme/cortex/voxy/common/voxelization/VoxelizedSection;Lme/cortex/voxy/common/world/other/Mapper;Lnet/minecraft/world/level/chunk/PalettedContainer;Lnet/minecraft/world/level/chunk/PalettedContainerRO;Lme/cortex/voxy/common/voxelization/ILightingSupplier;ZJ)Lme/cortex/voxy/common/voxelization/VoxelizedSection;",
            at = @At(value = "INVOKE", target = "Lme/cortex/voxy/common/world/other/Mapper;composeMappingId(BII)J")
    )
    private static long eclipticseasons$convert(
            byte light, int blockId, int biomeId, Operation<Long> original,
            @Local(argsOnly = true) Mapper mapper,
            @Local(argsOnly = true) ILightingSupplier lightSupplier,
            @Local(name = "i") int i,
            @Local(argsOnly = true) VoxelizedSection section) {
        blockId = VoxyTool.changeBlockId(blockId, mapper, i, section, lightSupplier, biomeId);
        return original.call(light, blockId, biomeId);
    }

    @Inject(
            remap = false,
            method = "convert(Lme/cortex/voxy/common/voxelization/VoxelizedSection;Lme/cortex/voxy/common/world/other/Mapper;Lnet/minecraft/world/level/chunk/PalettedContainer;Lnet/minecraft/world/level/chunk/PalettedContainerRO;Lme/cortex/voxy/common/voxelization/ILightingSupplier;ZJ)Lme/cortex/voxy/common/voxelization/VoxelizedSection;",
            at = @At(value = "RETURN")
    )
    private static void eclipticseasons$convert_release(
            VoxelizedSection section,
            Mapper stateMapper, PalettedContainer<BlockState> blockContainer, PalettedContainerRO<Holder<Biome>> biomeContainer, ILightingSupplier lightSupplier, boolean shouldZoom, long zoomSeed, CallbackInfoReturnable<VoxelizedSection> cir) {
        if (VoxyTool.isVoxyTest() && section instanceof IVoxyLevelProvider levelProvider) {
            levelProvider.release();
        }
    }
}
