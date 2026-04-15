package com.teamtea.eclipticseasons.client.model.block;

import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.client.renderer.v1.model.FabricBlockStateModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public interface NeoLikeBlockStateModel extends FabricBlockStateModel, BlockStateModel {

    @Override
    public default void emitQuads(@NonNull QuadEmitter emitter, @NonNull BlockAndTintGetter level, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull RandomSource random, @NonNull Predicate<@Nullable Direction> cullTest) {
        final boolean cutoutLeaves = Minecraft.getInstance().options.cutoutLeaves().get();
        final boolean forceOpaque = ModelBlockRenderer.forceOpaque(cutoutLeaves, state);
        if (forceOpaque) {
            emitter.pushTransform(quad -> {
                quad.chunkLayer(ChunkSectionLayer.SOLID);
                return true;
            });
        }
        final List<BlockStateModelPart> parts = new ArrayList<>();
        this.collectParts(level, pos, state, random, parts);
        final int partCount = parts.size();
        for (int i = 0; i < partCount; i++) {
            parts.get(i).emitQuads(emitter, cullTest);
        }
        if (forceOpaque) {
            emitter.popTransform();
        }
    }

    void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockStateModelPart> parts);
}
