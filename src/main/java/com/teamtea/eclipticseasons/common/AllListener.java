package com.teamtea.eclipticseasons.common;


import com.teamtea.eclipticseasons.api.data.misc.ESSortInfo;
import com.teamtea.eclipticseasons.api.event.CanPlantGrowEvent;
import com.teamtea.eclipticseasons.api.event.SolarTermChangeEvent;
import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.common.core.SolarHolders;
import com.teamtea.eclipticseasons.common.core.biome.BiomeClimateManager;
import com.teamtea.eclipticseasons.common.core.biome.WeatherManager;
import com.teamtea.eclipticseasons.common.core.crop.CropGrowthHandler;
import com.teamtea.eclipticseasons.common.core.crop.CropInfoManager;
import com.teamtea.eclipticseasons.common.core.crop.NaturalPlantHandler;
import com.teamtea.eclipticseasons.common.core.map.BiomeHolder;
import com.teamtea.eclipticseasons.common.core.map.ChunkInfoMap;
import com.teamtea.eclipticseasons.common.core.map.MapChecker;
import com.teamtea.eclipticseasons.common.core.snow.SnowChecker;
import com.teamtea.eclipticseasons.common.core.solar.SolarDataManager;
import com.teamtea.eclipticseasons.common.core.solar.extra.SpecialDaysManager;
import com.teamtea.eclipticseasons.common.environment.SolarTime;
import com.teamtea.eclipticseasons.common.network.SimpleNetworkHandler;
import com.teamtea.eclipticseasons.common.network.message.HumidModifyMessage;
import com.teamtea.eclipticseasons.common.registry.ModAdvancements;
import com.teamtea.eclipticseasons.config.CommonConfig;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import warp.net.neoforged.neoforge.event.TagsUpdatedEvent;
import warp.net.neoforged.neoforge.event.entity.player.BonemealEvent;
import warp.net.neoforged.neoforge.event.level.BlockGrowFeatureEvent;
import warp.net.neoforged.neoforge.event.level.block.CropGrowEvent;


public class AllListener {


    public static void onTagsUpdatedEventEarly(TagsUpdatedEvent tagsUpdatedEvent) {
        ESSortInfo.resetUpdate(tagsUpdatedEvent.getLookupProvider(), tagsUpdatedEvent.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD);
        BiomeClimateManager.resetBiomeTags(tagsUpdatedEvent.getLookupProvider(), tagsUpdatedEvent.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD);
    }

    // TagsUpdatedEvent invoke before ServerAboutToStartEvent

    public static void onTagsUpdatedEvent(TagsUpdatedEvent tagsUpdatedEvent) {
        if (tagsUpdatedEvent.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD) {
            for (Block block : BuiltInRegistries.BLOCK) {
                for (BlockState possibleState : block.getStateDefinition().getPossibleStates()) {
                    possibleState.initCache();
                }
            }
        }
        BiomeClimateManager.resetBiomeTemps(tagsUpdatedEvent.getLookupProvider(), tagsUpdatedEvent.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD);
        WeatherManager.informUpdateBiomes(tagsUpdatedEvent.getLookupProvider(), tagsUpdatedEvent.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD);
        CropInfoManager.init(tagsUpdatedEvent);
        CropGrowthHandler.resetUpdate(tagsUpdatedEvent.getLookupProvider(), tagsUpdatedEvent.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD);
        NaturalPlantHandler.resetUpdate(tagsUpdatedEvent.getLookupProvider(), tagsUpdatedEvent.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD);
        SnowChecker.resetUpdate(tagsUpdatedEvent.getLookupProvider(), tagsUpdatedEvent.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD);
        SpecialDaysManager.init(tagsUpdatedEvent.getLookupProvider(), tagsUpdatedEvent.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD);
    }


    public static void onServerAboutToStartEvent(MinecraftServer e) {
        WeatherManager.BIOME_WEATHER_LIST.clear();
        WeatherManager.BIOME_WEATHER_QUERY_LIST.clear();
        WeatherManager.NEXT_CHECK_BIOME_MAP.clear();
    }


    public static void onServerStoppingEvent(MinecraftServer e) {
        CropGrowthHandler.clearOnClientExitOrServerClose();
        NaturalPlantHandler.clearOnClientExitOrServerClose();
        BiomeClimateManager.clearOnClientExitOrServerClose(true);
        SpecialDaysManager.clearOnClientExitOrServerClose(true);
        SnowChecker.clearOnClientExitOrServerClose();
        ESSortInfo.clearOnClientExitOrServerClose();
    }


    // public static void onCanPlayerSleepEvent(CanPlayerSleepEvent event) {
    //     if (event.getVanillaProblem() == Player.BedSleepingProblem.OTHER_PROBLEM) {
    //         BlockPos pos = event.getPos();
    //         Level level = event.getLevel();
    //         if (EclipticUtil.hasLocalWeather(level)
    //                 && WeatherManager.isThunderAtBiome(level, pos)) {
    //             event.setProblem(null);
    //         }
    //     }
    // }
    //
    //
    // public static void onCanContinueSleepingEvent(CanContinueSleepingEvent event) {
    //     if (!event.mayContinueSleeping()
    //             && event.getProblem() == Player.BedSleepingProblem.OTHER_PROBLEM) {
    //         BlockPos pos = event.getEntity().getSleepingPos().orElse(null);
    //         Level level = event.getEntity().level();
    //         if (pos != null && EclipticUtil.hasLocalWeather(level)
    //                 && WeatherManager.isThunderAtBiome(level, pos)) {
    //             event.setContinueSleeping(true);
    //         }
    //     }
    // }
    //
    //
    // public static void onSleepFinishedTimeEvent(SleepFinishedTimeEvent event) {
    //     if (event.getLevel() instanceof ServerLevel level
    //             && level.dimensionType().defaultClock().isPresent()) {
    //
    //         long newTime = level.getDefaultClockTime(),
    //                 oldDayTime = newTime;
    //         ServerClockManager.ClockInstance instance = level.clockManager().getInstance(level.dimensionType().defaultClock().get());
    //         ClockTimeMarker timeMarker = instance.timeMarkers.get(ClockTimeMarkers.WAKE_UP_FROM_SLEEP);
    //         if (timeMarker != null) {
    //             newTime = timeMarker.resolveTimeToMoveTo(instance.totalTicks);
    //         }
    //
    //         WeatherManager.updateAfterSleep(level, newTime, oldDayTime);
    //     }
    //
    // }


    public static void onLevelLoad(ServerLevel level) {
        if (CommonConfig.Season.validDimensions.get().contains(level.dimension().identifier().toString()))
            MapChecker.validDimension.add(level);

        WeatherManager.createLevelBiomeWeatherList(level);
        SolarHolders.createSaveData(level, SolarDataManager.get(level));
        SolarTime.updateTimeMarks(level);
    }


    public static void onLevelUnloadEvent(Level level) {
        WeatherManager.BIOME_WEATHER_LIST.remove(level);
        WeatherManager.NEXT_CHECK_BIOME_MAP.remove(level);
        WeatherManager.BIOME_WEATHER_QUERY_LIST.remove(level);
        SolarHolders.DATA_MANAGER_MAP.remove(level);
        SolarHolders.remove(level);
        MapChecker.unloadLevel(level);
        MapChecker.validDimension.removeIf(l -> l.equals(level));
    }

    // 如果是客户端，即使是混合型客户端，我们也只应该清理一次，单人世界时只看一次client会更好

    public static void onChunkUnloadEvent(Level level, LevelChunk levelChunk) {
        // if ((FMLLoader.getCurrent().getDist() == Dist.CLIENT) == event.getLevel().isClientSide()
        // ) {
        //     MapChecker.clearChunk(event.getChunk().getLevel(),event.getChunk().getPos());
        // }
        MapChecker.unloadChunk(level, levelChunk.getPos());
        CropGrowthHandler.unloadChunk(level, levelChunk.getPos());
    }


    public static void onLevelTick(Level level) {
        SolarDataManager data = SolarHolders.getSaveData(level);
        if (data != null) {
            data.tickLevel(level);
        }
        MapChecker.tickLevel(level);
    }


    public static void onLevelTickPre(Level serverLevel) {
    }


    public static void onPlayerLoggedIn(ServerGamePacketListenerImpl handler, PacketSender packetSender, MinecraftServer minecraftServer) {
        ServerPlayer player = handler.player;
        WeatherManager.onLoggedIn(player, true);
    }


    public static void onPlayerChangedDimension(ServerPlayer serverPlayer, ServerPlayer serverPlayer1, boolean b) {
        {
            WeatherManager.onLoggedIn(serverPlayer, false);
        }
    }


    public static void onPlayerChangedDimension2(ServerPlayer serverPlayer, ServerPlayer serverPlayer1, boolean b) {
        {
            // WeatherManager.onLoggedIn(serverPlayer, false);
            // 不知道为什么要多线程来避免问题
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
                WeatherManager.onLoggedIn(serverPlayer, false);
            });
            t.start();

        }
    }


    public static void onCropGrowUp(CanPlantGrowEvent event) {
        CropGrowthHandler.beforeCropGrowUp(event);
    }


    public static void onCropGrowUp(CropGrowEvent.Pre event) {
        if (!CommonConfig.isForceCropCompatMode())
            CropGrowthHandler.beforeCropGrowUp(event);
    }


    public static void onCropGrowUp(BlockGrowFeatureEvent event) {
        CropGrowthHandler.beforeCropGrowUp(event);
    }


    public static void onCropGrowUp(BonemealEvent event) {
        CropGrowthHandler.beforeCropGrowUp(event);
    }


    public static void onPlayerTickPre(ServerPlayer serverPlayer) {
        {
            WeatherManager.tickPlayerSeasonEffect(serverPlayer);
            // WeatherManager.tickPlayerForSeasonCheck(serverPlayer);
        }
    }


    public static void onPlayerTickPost(ServerPlayer serverPlayer) {
        {
            Level level = serverPlayer.level();
            if (level.getGameTime() % 20 == 0) {
                ModAdvancements.PARENT_NEED.trigger(serverPlayer);

                SolarDataManager data = SolarHolders.getSaveData(level);
                if (data != null) {
                    float v = data.calculateHumidityModification(serverPlayer.blockPosition(), false);
                    SimpleNetworkHandler.send(serverPlayer, new HumidModifyMessage(
                            serverPlayer.blockPosition(), v
                    ));
                }
            }
        }
    }


    public static void onChunkWatch(ServerLevel serverLevel, LevelChunk chunk, ChunkPos
            chunkPos, ServerPlayer player) {
        MapChecker.sendChunkLoginInfo(serverLevel, chunk, chunkPos, player);
    }

    // Not do anything here would cause dead lock
    public static void onChunkLoad(Level level, ChunkAccess chunk) {
        onChunkLoad(level, chunk, false);
    }

    public static void onChunkLoad(Level level, ChunkAccess chunk, boolean isNewChunk) {
        BiomeHolder biomeHolder = null;
        if (level instanceof ServerLevel serverLevel) {
            if (isNewChunk) {
                MapChecker.setNewChunk(serverLevel, chunk);
            }
            biomeHolder = MapChecker.getOrUpdateChunkBiomeData(serverLevel, chunk, chunk.getPos());
        }

        // if (event.getLevel() instanceof Level level)
        {
            ChunkInfoMap chunkInfoMap = MapChecker.forceChunkUpdateHeight(level, chunk);

            if (EclipticUtil.canSnowyBlockInteract() && biomeHolder != null && level instanceof ServerLevel serverLevel) {
                int biomeDataVersion = EclipticUtil.getBiomeDataVersion(level);
                if (biomeHolder.version() != biomeDataVersion || !biomeHolder.hasUpdated()) biomeHolder = null;
                if (biomeHolder != null) {
                    // SnowyMapChecker.forceChunkUpdateHeight(level, chunk, chunkInfoMap, biomeHolder, true);
                }
            }
        }

        // updateChunk(event.getLevel(), chunk);
    }


    public static void onSolarTermChangeEvent(SolarTermChangeEvent event) {
        SolarTime.updateTimeMarks(event.getLevel());
    }


}
