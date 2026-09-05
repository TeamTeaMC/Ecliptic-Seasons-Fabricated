package com.teamtea.eclipticseasons.client.model.block.fabric;

import com.teamtea.eclipticseasons.client.model.block.part.SimpleBlockModelPart;
import net.fabricmc.fabric.api.client.renderer.v1.Renderer;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableMesh;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadAtlas;
import net.fabricmc.fabric.api.client.renderer.v1.model.FabricBlockStateModel;
import net.fabricmc.fabric.api.client.renderer.v1.sprite.FabricTextureAtlas;
import net.fabricmc.fabric.api.client.renderer.v1.sprite.SpriteFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.dispatch.SingleVariant;
import net.minecraft.client.renderer.block.dispatch.WeightedVariants;
import net.minecraft.client.renderer.block.dispatch.multipart.MultiPartModel;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class FabricModelPartCollector {

    protected static final Predicate<Direction> NO_CULL = direction -> false;
    protected static final ThreadLocal<MutableMesh> CAPTURE_MESH =
            ThreadLocal.withInitial(() -> Renderer.get().mutableMesh());

    public static void collect(BlockStateModel model, BlockAndTintGetter level, BlockPos pos,
                               BlockState state, RandomSource random, List<BlockStateModelPart> parts) {
        if (isVanillaModel(model)) {
            model.collectParts(random, parts);
            return;
        }

        MutableMesh mesh = CAPTURE_MESH.get();
        mesh.clear();

        try {
            ((FabricBlockStateModel) model).emitQuads(
                    mesh.emitter(), level, pos, state, random, NO_CULL);
            if (mesh.size() == 0) return;

            TextureAtlas atlas = Minecraft.getInstance().getAtlasManager()
                    .getAtlasOrThrow(QuadAtlas.BLOCK.getId());
            SpriteFinder spriteFinder = ((FabricTextureAtlas) atlas).spriteFinder();
            Map<Direction, List<BakedQuad>> map = new IdentityHashMap<>(7);

            mesh.forEach(quad -> map.computeIfAbsent(
                    quad.cullFace(), direction -> new ArrayList<>(4)
            ).add(quad.toBakedQuad(spriteFinder.find(quad))));

            parts.add(new SimpleBlockModelPart(map));
        } finally {
            mesh.clear();
        }
    }

    protected static boolean isVanillaModel(BlockStateModel model) {
        return model instanceof SingleVariant
                || model instanceof WeightedVariants
                || model instanceof MultiPartModel;
    }
}