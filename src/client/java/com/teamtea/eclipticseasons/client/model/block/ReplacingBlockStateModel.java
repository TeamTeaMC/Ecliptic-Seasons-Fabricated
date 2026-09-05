package com.teamtea.eclipticseasons.client.model.block;

import com.teamtea.eclipticseasons.client.model.block.fabric.NeoLikeBlockStateModel;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public record ReplacingBlockStateModel(BlockStateModel original,
                                       boolean replace) implements NeoLikeBlockStateModel {

    public static boolean replace(BlockStateModel stateModel) {
        return stateModel instanceof ReplacingBlockStateModel rp && rp.replace();
    }

    @Override
    public void collectParts(RandomSource random, List<BlockStateModelPart> output) {
        original.collectParts(random, output);
    }

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockStateModelPart> parts) {
        if (original instanceof NeoLikeBlockStateModel nbl)
            nbl.collectParts(level, pos, state, random, parts);
        else collectParts(random, parts);
    }

    @Override
    public void emitQuads(@NonNull QuadEmitter emitter, @NonNull BlockAndTintGetter level, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull RandomSource random, @NonNull Predicate<@Nullable Direction> cullTest) {
        original.emitQuads(emitter, level, pos, state, random, cullTest);
        // NeoLikeBlockStateModel.super.emitQuads(emitter, level, pos, state, random, cullTest);
    }

    @Override
    public Material.Baked particleMaterial() {
        return original.particleMaterial();
    }

    @Override
    public @BakedQuad.MaterialFlags int materialFlags() {
        return original.materialFlags();
    }


}
