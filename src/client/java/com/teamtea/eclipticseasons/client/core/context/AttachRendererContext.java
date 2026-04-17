package com.teamtea.eclipticseasons.client.core.context;

import com.teamtea.eclipticseasons.api.misc.client.IAttachRendererContextOwner;
import com.teamtea.eclipticseasons.client.model.block.ISnowyReplaceModel;
import com.teamtea.eclipticseasons.client.model.block.NeoLikeBlockStateModel;
import com.teamtea.eclipticseasons.common.core.map.MapChecker;
import lombok.Data;
import lombok.experimental.Accessors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TriState;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;


/**
 *
 * Get from {@link  IAttachRendererContextOwner#of(Object)}.
 * <p> Object is usually a {@link  com.teamtea.eclipticseasons.api.misc.client.IMapSlice}.
 *
 **/
@Data
@Accessors(chain = true)
public class AttachRendererContext {

    BlockStateModel originalModel = null;
    @NonNull
    List<BlockStateModel> extraModels = new ArrayList<>();
    BlockStateModel snowyModel = null;
    boolean replace = false;

    public AttachRendererContext add(BlockStateModel model) {
        if (model != null)
            this.extraModels.add(model);
        return this;
    }

    public void resetAll() {
        extraModels.clear();
        this.originalModel = null;
        this.snowyModel = null;
        this.replace = false;
    }

    public boolean shouldApply() {
        return !extraModels.isEmpty();
    }

    public AttachRendererContext apply(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockStateModelPart> parts) {
        if (this.shouldApply()) {
            if (this.isReplace()) parts.clear();
            List<BlockStateModel> extraModels = this.getExtraModels();
            for (int i = 0, extraModelsSize = extraModels.size(); i < extraModelsSize; i++) {
                BlockStateModel extraModel = extraModels.get(i);
                NeoLikeBlockStateModel.collectParts(extraModel, level, pos, state, random, parts);
            }
        }
        return this;
    }

    public Iterable<BlockStateModel> cycle() {
        return this.extraModels;
    }

    public static final AttachRendererContext EMPTY = new AttachRendererContext();


    public static TriState getModelForAmbientOcclusion(Object context, BlockState state, ChunkSectionLayer renderType) {
        AttachRendererContext rendererHolder = IAttachRendererContextOwner.of(context);
        if (rendererHolder.getExtraModels() instanceof ISnowyReplaceModel snowyBakedModelWrapper) {
            if (snowyBakedModelWrapper.getBindBlockType() == MapChecker.FLAG_CUSTOM_AO) {
                return TriState.TRUE;
            }
        }
        return null;
    }
}
