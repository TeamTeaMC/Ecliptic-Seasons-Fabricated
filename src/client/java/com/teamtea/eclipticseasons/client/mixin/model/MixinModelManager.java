package com.teamtea.eclipticseasons.client.mixin.model;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.teamtea.eclipticseasons.client.ClientEventHandler;
import com.teamtea.eclipticseasons.client.ClientSetup;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

@Mixin(net.minecraft.client.resources.model.ModelManager.class)
public abstract class MixinModelManager {

    @WrapOperation(method = "apply", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/ModelBakery$BakingResult;itemStackModels()Ljava/util/Map;"))
    private Map<Identifier, ItemModel> eclipticseasons$loadBlockStates(ModelBakery.BakingResult instance, Operation<Map<Identifier, ItemModel>> original) {
        ClientSetup.onModelBaked(instance);
        return original.call(instance);
    }

    // @ModifyExpressionValue(method = "reload", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/BlockStateModelLoader;loadBlockStates(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;"))
    // private CompletableFuture<Map<Identifier, List<BlockStateModelLoader.LoadedModels>>> eclipticseasons$loadBlockStates_before_loadModelDef(
    //         CompletableFuture<Map<Identifier, List<BlockStateModelLoader.LoadedModels>>> original,
    //         @Local(name = "manager") ResourceManager resourceManager,
    //         @Local(ordinal = 0, argsOnly = true) Executor executor
    //
    // ) {
    //     return CompletableFuture.allOf(
    //             ClientJsonCacheListener.modelDefCache.prepareAsync(
    //                     resourceManager, executor
    //             ),
    //             ClientJsonCacheListener.textureReMappingsCache.prepareAsync(
    //                     resourceManager, executor
    //             ).thenRun(() -> {
    //                 ExtraModelManager.SEASONAL_TEXTURE_HASH_MAP.clear();
    //                 Map<Identifier, SeasonalTexture> build = ClientJsonCacheListener.textureReMappingsCache.build(SeasonalTexture.CODEC);
    //                 build.forEach(
    //                         (Identifier, seasonalTexture) -> {
    //                             seasonalTexture = seasonalTexture.build(Identifier);
    //                             if (seasonalTexture.getParent().isEmpty()) {
    //                                 List<SeasonalTexture> seasonalTextures = ExtraModelManager.SEASONAL_TEXTURE_HASH_MAP.computeIfAbsent(
    //                                         Identifier.withPrefix("block/"), (xx) -> new ArrayList<>());
    //                                 seasonalTextures.add(seasonalTexture);
    //                             } else {
    //                                 for (Identifier location : seasonalTexture.getParent()) {
    //                                     List<SeasonalTexture> seasonalTextures = ExtraModelManager.SEASONAL_TEXTURE_HASH_MAP.computeIfAbsent(
    //                                             location, (xx) -> new ArrayList<>());
    //                                     seasonalTextures.add(seasonalTexture);
    //                                 }
    //                             }
    //                         }
    //                 );
    //             })
    //     ).thenCompose(ignored -> original);
    // }


}
