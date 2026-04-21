package com.teamtea.eclipticseasons.common.core.map;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v8.component.CardinalComponent;

@Data
public class BiomeHolder implements CardinalComponent {
    final int[] biomes;
    boolean hasUpdated;
    int version;

    public BiomeHolder(int[] biomes, boolean hasUpdated, int version) {
        this.biomes = biomes;
        this.hasUpdated = hasUpdated;
        this.version = version;
    }

    public int[] biomes() {
        return biomes;
    }

    public boolean hasUpdated() {
        return hasUpdated;
    }

    public int version() {
        return version;
    }

    public static final int FLAG_NEED_VERSION = -1;
    public static final int FLAG_FILL_SMALL = -2;

    public static final Codec<BiomeHolder> CODEC = Codec.lazyInitialized(
            () -> RecordCodecBuilder.create(
                    snowyRemoverInstance ->
                            snowyRemoverInstance.group(
                                            Codec.INT.sizeLimitedListOf(16 * 16)
                                                    .fieldOf("blocks").forGetter(snowyRemover ->
                                                            IntList.of(snowyRemover.biomes())
                                                    )
                                            , Codec.BOOL.fieldOf("has_updated").forGetter(BiomeHolder::hasUpdated)
                                            , Codec.INT.fieldOf("version").forGetter(BiomeHolder::version)
                                    )
                                    .apply(snowyRemoverInstance, (biomes, hasUpdated, v) ->
                                            {
                                                int[] biomeArryas;
                                                if (biomes instanceof IntList intList) {
                                                    biomeArryas = intList.toIntArray();
                                                } else {
                                                    biomeArryas = new int[biomes.size()];
                                                    for (int i = 0; i < biomes.size(); i++) {
                                                        biomeArryas[i] = biomes.get(i);
                                                    }
                                                }
                                                return new BiomeHolder(biomeArryas, hasUpdated, v);
                                            }
                                    )
            )
    );

    public static BiomeHolder prepareBiomes(Level serverLevel, ChunkPos chunkPos, int biomeDataVersion, boolean registryUpdate) {
        int[] newBiomes = new int[256];
        boolean near = true;
        Registry<Biome> biomeRegistry = serverLevel.registryAccess().lookupOrThrow(Registries.BIOME);
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int xm = chunkPos.getBlockX(i);
                int zm = chunkPos.getBlockZ(j);
                mutableBlockPos.set(xm, 0, zm);
                Holder<Biome> unCachedSurfaceBiome = MapChecker.getUnCachedSurfaceBiome(serverLevel, mutableBlockPos);
                newBiomes[i * 16 + j] = registryUpdate ?
                        MapChecker.biomeToId(biomeRegistry, unCachedSurfaceBiome.value()) :
                        MapChecker.biomeToId(serverLevel, unCachedSurfaceBiome.value());
                // near &= MapChecker.isLoadNearBy(serverLevel, mutableBlockPos);

                if (!near) break;
            }
        }

        return new BiomeHolder(newBiomes, near, biomeDataVersion);
    }

    public static BiomeHolder fillSmallBiomes(Level serverLevel, ChunkAccess chunk, BiomeHolder oldHolder, int biomeDataVersion) {
        int[] newBiomes = new int[256];
        boolean near = true;
        int[] oldBiomes = oldHolder.biomes;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        ChunkPos chunkPos = chunk.getPos();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Holder<Biome> biomeHolder = MapChecker.idToBiome(serverLevel, oldBiomes[i * 16 + j]);
                if (MapChecker.isSmallBiome(biomeHolder)) {
                    int xm = chunkPos.getBlockX(i);
                    int zm = chunkPos.getBlockZ(j);
                    mutableBlockPos.set(xm, chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, i, j) + 1, zm);

                    newBiomes[i * 16 + j] =
                            MapChecker.biomeToId(serverLevel, MapChecker.getUnCachedSurfaceBiome(serverLevel, mutableBlockPos).value());
                    // near &= MapChecker.isLoadNearBy(serverLevel, mutableBlockPos);

                    if (!near) break;
                } else {
                    newBiomes[i * 16 + j] = oldBiomes[i * 16 + j];
                }
            }
        }

        return new BiomeHolder(newBiomes, near, biomeDataVersion);
    }


    public int getBiomeId(BlockPos blockPos) {
        // return -1;
        return hasUpdated ? biomes[((blockPos.getX() & 15) * 16) + (blockPos.getZ() & 15)] : -1;
    }

    public static BiomeHolder empty() {
        return new BiomeHolder(new int[256], false, 0);
    }

    @Override
    public void readData(ValueInput input) {
        var snowyStatus = input.read("biome_holder", CODEC);
        if (snowyStatus.isEmpty()) return;
        copyFrom(snowyStatus.get());
    }

    public void copyFrom(BiomeHolder biomeHolder) {
        System.arraycopy(biomeHolder.biomes, 0, this.biomes, 0, biomeHolder.biomes.length);
        this.hasUpdated = biomeHolder.hasUpdated();
        this.version = biomeHolder.version;
    }

    @Override
    public void writeData(ValueOutput output) {
        output.storeNullable("biome_holder", CODEC, this);
    }

}
