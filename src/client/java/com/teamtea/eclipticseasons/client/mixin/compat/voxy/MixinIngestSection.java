package com.teamtea.eclipticseasons.client.mixin.compat.voxy;

import com.teamtea.eclipticseasons.common.mixin.condition.ConditionalMixin;
import com.teamtea.eclipticseasons.compat.voxy.helper.IVoxyLevelProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.lang.ref.WeakReference;

@Mixin(targets = {"me.cortex.voxy.common.world.service.VoxelIngestService$IngestSection",
        "me.cortex.voxy.common.voxelization.VoxelizedSection"})
@ConditionalMixin(value = "voxy",version = "0.2.18-beta")
public abstract class MixinIngestSection implements IVoxyLevelProvider {

    @Unique
    LevelChunk eclipticseasons$level;

    @Override
    public void setLevelReference(LevelChunk levelReference) {
        this.eclipticseasons$level = levelReference;
    }

    @Override
    public WeakReference<LevelChunk> getLevelReference() {
        return new WeakReference<>(eclipticseasons$level);
    }
}
