package com.teamtea.eclipticseasons.client.mixin;


import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({LevelChunk.class})
public abstract class MixinClientLevelChunk {
    @Shadow
    @Final
    Level level;

    // @Inject(
    //         at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/Heightmap;update(IIILnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 2),
    //         method = "setBlockState"
    // )
    // public void eclipticseasons$Client_setBlockState(BlockPos pos, BlockState state, boolean p_62867_, CallbackInfoReturnable<BlockState> cir) {
    //     if (level != null && level.isClientSide()) {
    //         // MapChecker.getHeightOrUpdate(clientLevel, pos, true);
    //         // ClientMapFixer.addPlanner(clientLevel, state, pos, clientLevel.getGameTime(), MapChecker.getHeight(clientLevel, pos));
    //         MapChecker.getHeightOrUpdate(level, pos, true);
    //     }
    // }
}
