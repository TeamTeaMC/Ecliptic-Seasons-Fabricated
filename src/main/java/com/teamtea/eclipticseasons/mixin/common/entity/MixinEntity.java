package com.teamtea.eclipticseasons.mixin.common.entity;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({Entity.class})
public abstract class MixinEntity {

    @Shadow
    private Level level;

    @Shadow
    public abstract Level level();

    @WrapOperation(at = {@At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;getSoundType()Lnet/minecraft/world/level/block/SoundType;")},
            method = {"playStepSound"})
    public SoundType eclipticseasons$playStepSound(BlockState instance, Operation<SoundType> original, @Local BlockPos pos) {
        if (EclipticSeasonsApi.getInstance().isSnowyBlock(this.level, instance, pos))
            instance = Blocks.SNOW.defaultBlockState();
        return original.call(instance);
    }

    @WrapOperation(at = {@At(value = "NEW",
            target = "(Lnet/minecraft/core/particles/ParticleType;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/core/particles/BlockParticleOption;")},
            method = {"spawnSprintParticle"})
    public BlockParticleOption eclipticseasons$spawnSprintParticle_snow(ParticleType<?> type, BlockState state, Operation<BlockParticleOption> original, @Local(ordinal = 0) BlockPos pos) {
        if (EclipticSeasonsApi.getInstance().isSnowyBlock(level, state, pos)) {
            state = Blocks.SNOW.defaultBlockState();
        }
        return original.call(type, state);
    }

    // @Inject(at = {@At(value = "INVOKE",
    //         target = "Lnet/minecraft/world/level/block/Block;stepOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/Entity;)V")},
    //         method = "applyEffectsFromBlocks(Ljava/util/List;)V")
    // public void eclipticseasons$move_stepOn(List movements,
    //                                         CallbackInfo ci,
    //                                         @Local BlockPos pos,
    //                                         @Local BlockState blockstate) {
    //     SnowyMapChecker.onEntityStepOn((Entity) (Object) this, level, pos, blockstate);
    // }
}
