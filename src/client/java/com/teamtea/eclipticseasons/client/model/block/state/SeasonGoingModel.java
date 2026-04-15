package com.teamtea.eclipticseasons.client.model.block.state;

import com.mojang.datafixers.util.Pair;
import com.teamtea.eclipticseasons.api.constant.solar.SolarTerm;
import com.teamtea.eclipticseasons.api.misc.BiomeHolderPredicate;
import com.teamtea.eclipticseasons.api.misc.client.IMapSlice;
import com.teamtea.eclipticseasons.client.model.block.NeoLikeBlockStateModel;
import com.teamtea.eclipticseasons.client.util.ClientCon;
import com.teamtea.eclipticseasons.common.core.map.MapChecker;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Predicate;

public record SeasonGoingModel(BlockStateModelPart base,
                               List<Holder> defaultList,
                               List<Pair<BiomeHolderPredicate, Holder>> testList)
        implements NeoLikeBlockStateModel {



    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource rand, List<BlockStateModelPart> parts) {
        // BlockStateModel.super.collectParts(level, pos, state, random, parts);
        SolarTerm nowSolarTerm = ClientCon.nowSolarTerm;

        Holder holder = null;

        if (testList.isEmpty()) {
            if (defaultList.isEmpty()) return;
            BlockStateModelPart useModel = null;
            holder = defaultList.getFirst();
            List<Pair<BlockStateModelPart, BlockStateModelPart>> bakedModels = holder.seasonalModels.get(nowSolarTerm);
            if (bakedModels != null && !bakedModels.isEmpty()) {
                var modelPartPair = bakedModels.size() == 1 ? bakedModels.getFirst() : bakedModels.get(rand.nextInt(bakedModels.size()));
                if (modelPartPair.getFirst() == modelPartPair.getSecond())
                    useModel = modelPartPair.getFirst();
                else
                    useModel = rand.nextInt(100) >= ClientCon.progress ? modelPartPair.getFirst() : modelPartPair.getSecond();
            }
            if (useModel != null) {
                parts.add(useModel);
            }
        } else {
            var biomeHolder = level instanceof IMapSlice iMapSlice ?
                    MapChecker.idToBiome(ClientCon.getUseLevel(), iMapSlice.getSurfaceFaceBiomeId(pos)) :
                    MapChecker.getSurfaceBiome(ClientCon.getUseLevel(), pos);
            BlockStateModelPart useModel = null;
            for (Pair<BiomeHolderPredicate, Holder> pair : testList) {
                if (pair.getFirst().test(biomeHolder)) {
                    List<Pair<BlockStateModelPart, BlockStateModelPart>> bakedModels = pair.getSecond().seasonalModels.get(nowSolarTerm);
                    if (bakedModels != null && !bakedModels.isEmpty()) {
                        var modelPartPair = bakedModels.size() == 1 ? bakedModels.getFirst() : bakedModels.get(rand.nextInt(bakedModels.size()));
                        if (modelPartPair.getFirst() == modelPartPair.getSecond())
                            useModel = modelPartPair.getFirst();
                        else
                            useModel = rand.nextInt(100) >= ClientCon.progress ? modelPartPair.getFirst() : modelPartPair.getSecond();
                    }
                    if (useModel != null) {
                        parts.add(useModel);
                        break;
                    }
                }
            }
        }

        if (parts.isEmpty()) {
            collectParts(rand, parts);
        }
    }

    @Override
    public void collectParts(RandomSource random, List<BlockStateModelPart> output) {
        output.add(base);
    }

    @Override
    public Material.Baked particleMaterial() {
        return base.particleMaterial();
    }

    @Override
    public @BakedQuad.MaterialFlags int materialFlags() {
        return base.materialFlags();
    }

    public record Holder(EnumMap<SolarTerm, List<Pair<BlockStateModelPart, BlockStateModelPart>>> seasonalModels,
                         EnumMap<SolarTerm, List<Pair<BlockStateModelPart, BlockStateModelPart>>> snowyModels) {

    }
}
