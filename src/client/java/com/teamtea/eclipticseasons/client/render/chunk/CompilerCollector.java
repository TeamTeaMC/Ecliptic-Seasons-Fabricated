package com.teamtea.eclipticseasons.client.render.chunk;

import net.minecraft.world.level.ChunkPos;

import java.util.HashMap;
import java.util.List;


public class CompilerCollector {

    public static HashMap<ChunkPos, List<int[]>> sectionCompiler = new HashMap<>(1024);

    public static void add(ChunkPos pos, List<int[]> map) {
        if(true)return ;
        sectionCompiler.put(pos, map);
    }

    public static List<int[]> get(ChunkPos pos) {
        if(true)return null;
        // if (MapChecker.isChunkDirty(pos)) {
        //     sectionCompiler.remove(pos);
        //     MapChecker.removeDirtyChunk(pos);
        //     return null;
        // }
        return sectionCompiler.getOrDefault(pos,null);
    }

    public static void clearChunk(ChunkPos pos) {
        sectionCompiler.remove(pos);
    }

    public static void clearAll() {
        sectionCompiler.clear();
    }
}