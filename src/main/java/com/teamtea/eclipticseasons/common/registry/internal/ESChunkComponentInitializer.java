package com.teamtea.eclipticseasons.common.registry.internal;

import com.teamtea.eclipticseasons.common.core.map.BiomeHolder;
import com.teamtea.eclipticseasons.common.registry.AttachmentRegistry;
import org.jspecify.annotations.NonNull;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentInitializer;

public class ESChunkComponentInitializer implements ChunkComponentInitializer {

    @Override
    public void registerChunkComponentFactories(@NonNull ChunkComponentFactoryRegistry registry) {
        registry.register(AttachmentRegistry.BIOME_HOLDER, _ -> BiomeHolder.empty());
    }
}
