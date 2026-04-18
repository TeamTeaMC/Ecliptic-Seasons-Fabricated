package com.teamtea.eclipticseasons.mixin.common.item;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.common.AllListener;
import com.teamtea.eclipticseasons.common.hook.ESEventHook;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import warp.net.neoforged.neoforge.event.entity.player.BonemealEvent;

@Mixin(BoneMealItem.class)
public class MixinBoneMealItem {

    @Inject(
            method = "growCrop",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/BonemealableBlock;isBonemealSuccess(Lnet/minecraft/world/level/Level;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"),
            cancellable = true)
    private static void eclipticseasons$growCrop_test(ItemStack itemStack, Level level, BlockPos pos, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) BlockPos blockPos,
                                                      @Local(name = "state") BlockState state) {
        Player nearestPlayer = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, false);
        BonemealEvent bonemealEvent = new BonemealEvent(nearestPlayer, level, pos, state, itemStack);
        ESEventHook.BONEMEAL.invoker().onEvent(bonemealEvent);
        if (bonemealEvent.isCanceled()) {
            if (bonemealEvent.isSuccess()) {
                itemStack.shrink(1);
            }
            cir.setReturnValue(true);
        }
    }


}
