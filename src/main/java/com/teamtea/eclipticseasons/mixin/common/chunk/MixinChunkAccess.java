package com.teamtea.eclipticseasons.mixin.common.chunk;


import com.teamtea.eclipticseasons.api.misc.IChunkBiomeHolder;
import com.teamtea.eclipticseasons.common.core.map.BiomeHolder;
import com.teamtea.eclipticseasons.common.registry.AttachmentRegistry;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({ChunkAccess.class})
public abstract class MixinChunkAccess implements IChunkBiomeHolder {

    @Unique
    private BiomeHolder eclipticseasons$biomeHolder = null;

    @Override
    public BiomeHolder eclipticseasons$getBiomeHolder() {
        if (this.eclipticseasons$biomeHolder == null) {
            BiomeHolder nullable = AttachmentRegistry.BIOME_HOLDER.getNullable(this);
            if (nullable != null) {
                BiomeHolder biomeHolder = nullable;
                if (biomeHolder.hasUpdated()) {
                    this.eclipticseasons$biomeHolder = biomeHolder;
                }
            }
        }
        return this.eclipticseasons$biomeHolder;
    }

    @Override
    public void eclipticseasons$resetBiomeHolder() {
        this.eclipticseasons$biomeHolder = null;
    }
}
