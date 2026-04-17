package com.teamtea.eclipticseasons.client.mixin.compat.sodium;


import net.caffeinemc.mods.sodium.client.services.DefaultModelEmitter;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({DefaultModelEmitter.class})
public abstract class MixinDefaultModelEmitter {


    // @ModifyExpressionValue(
    //         remap = false,
    //         method = "emitModel",
    //         at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/services/PlatformModelAccess;collectPartsOf(Lnet/minecraft/client/renderer/block/dispatch/BlockStateModel;Lnet/minecraft/client/renderer/block/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/util/RandomSource;Lnet/caffeinemc/mods/sodium/client/render/helper/ListStorage;)Ljava/util/List;")
    // )
    // private List<BlockStateModelPart> eclipticseasons$getQuads_getQuads(
    //         List<BlockStateModelPart> original,
    //         @Local(argsOnly = true) BlockStateModel stateModel,
    //         @Local(argsOnly = true) BlockAndTintGetter blockAndTintGetter,
    //         @Local(argsOnly = true) BlockPos pos,
    //         @Local(argsOnly = true) BlockState state,
    //         @Local(argsOnly = true) RandomSource random) {
    //     if (blockAndTintGetter instanceof IMapSlice mapSlice)
    //         ExtraRenderDispatcher.findModel(mapSlice, pos, state, random,
    //                 state.getSeed(pos), mapSlice.getModelCheckPos(),
    //                 original);
    //     return original;
    // }


}
