package com.teamtea.eclipticseasons.client.model.block;

import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.util.RandomSource;

import java.util.List;

public record ReplacingBlockStateModel(BlockStateModel original,
                                       boolean replace) implements BlockStateModel {

    public static boolean replace(BlockStateModel stateModel) {
        return stateModel instanceof ReplacingBlockStateModel rp && rp.replace();
    }

    @Override
    public void collectParts(RandomSource random, List<BlockStateModelPart> output) {
        original.collectParts(random, output);
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
