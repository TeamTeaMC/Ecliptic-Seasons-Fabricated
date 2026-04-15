package com.teamtea.eclipticseasons.client.mixin.model;


import com.teamtea.eclipticseasons.client.model.MyResolver;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ModelBakery.class)
public abstract class MixinModelBakery2 {


    @Inject(at = {@At(value = "TAIL")}, method = {"<init>"})
    private void eclipticseasons$register_snowy_models(
            EntityModelSet entityModelSet,
            SpriteGetter sprites,
            PlayerSkinRenderCache playerSkinRenderCache,
            Map<BlockState, BlockStateModel.UnbakedRoot> unbakedBlockStateModels,
            Map<Identifier, ClientItem> clientInfos,
            Map<Identifier, ResolvedModel> resolvedModels,
            ResolvedModel missingModel,
            CallbackInfo ci) {
        MyResolver.INSTANCE.setBlockState(Blocks.AIR.defaultBlockState());
        unbakedBlockStateModels.forEach(
                (blockState, unbakedRoot) -> {
                    unbakedRoot.resolveDependencies(MyResolver.INSTANCE.setBlockState(blockState));
                }
        );
        MyResolver.INSTANCE.setBlockState(Blocks.AIR.defaultBlockState());
    }

}
