package com.teamtea.eclipticseasons.client;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import com.teamtea.eclipticseasons.api.data.misc.ESSortInfo;
import com.teamtea.eclipticseasons.api.event.SolarTermChangeEvent;
import com.teamtea.eclipticseasons.api.misc.client.IBiomeColorHolder;
import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.client.color.season.BiomeColorsHandler;
import com.teamtea.eclipticseasons.client.core.ClientWeatherChecker;
import com.teamtea.eclipticseasons.client.render.WorldRenderer;
import com.teamtea.eclipticseasons.client.render.chunk.CompilerCollector;
import com.teamtea.eclipticseasons.client.render.chunk.IceKeeper;
import com.teamtea.eclipticseasons.client.util.ClientCon;
import com.teamtea.eclipticseasons.client.util.ClientRef;
import com.teamtea.eclipticseasons.common.core.SolarHolders;
import com.teamtea.eclipticseasons.common.core.biome.BiomeClimateManager;
import com.teamtea.eclipticseasons.common.core.biome.WeatherManager;
import com.teamtea.eclipticseasons.common.core.crop.CropGrowthHandler;
import com.teamtea.eclipticseasons.common.core.crop.CropInfoManager;
import com.teamtea.eclipticseasons.common.core.crop.NaturalPlantHandler;
import com.teamtea.eclipticseasons.common.core.map.MapChecker;
import com.teamtea.eclipticseasons.common.core.snow.SnowChecker;
import com.teamtea.eclipticseasons.common.core.solar.extra.ClientSolarDataManager;
import com.teamtea.eclipticseasons.common.game.AnimalHooks;
import com.teamtea.eclipticseasons.common.misc.MapExporter;
import com.teamtea.eclipticseasons.config.ClientConfig;
import com.teamtea.eclipticseasons.config.CommonConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import warp.net.neoforged.neoforge.event.TagsUpdatedEvent;

import java.util.List;

public final class ClientEventHandler {

    public static void onRenderTick() {
        if (Minecraft.getInstance().player != null) {
            WorldRenderer.applyEffect(Minecraft.getInstance().gameRenderer, Minecraft.getInstance().player);
        }
        if (BiomeColorsHandler.needRefresh) {
            BiomeColorsHandler.reloadColors();
        }

    }


    public static void onSolarTermChangeEvent(SolarTermChangeEvent event) {
        if (event.getLevel().isClientSide()) {
            for (Biome biome : event.getLevel().registryAccess().lookupOrThrow(Registries.BIOME)) {
                if (((Object) biome) instanceof IBiomeColorHolder colorHolder) colorHolder.setSeasonChanged();
            }
        }
    }


    public static void addTooltips(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipFlag tooltipFlag, List<Component> list) {
        if (ClientConfig.GUI.agriculturalInformation.get()) {
            if (itemStack.getItem() instanceof BlockItem blockItem) {
                list.addAll(CropGrowthHandler.appendInfo(
                        null,
                        blockItem.getBlock().defaultBlockState()));
            } else {
                list.addAll(CropInfoManager.appendInfo(itemStack.getItem()));
            }
            if (itemStack.getItem() instanceof SpawnEggItem blockItem) {
                list.addAll(AnimalHooks.getBreedInfo(
                        blockItem.getType(itemStack)));
            }
        }
    }

    public static void onChunkUnloadEvent(Level level, LevelChunk levelChunk) {
        {
            CompilerCollector.clearChunk(levelChunk.getPos());
        }
    }

    public static void onLevelUnloadEvent(ClientLevel clientLevel) {
        {
            ClientCon.setUseLevel(null);
            CompilerCollector.clearAll();
            IceKeeper.clearAll();
        }
    }


    public static void onPlayerExit(ClientPacketListener clientPacketListener, Minecraft minecraft) {
        if (Minecraft.getInstance().player != null) {
            CropGrowthHandler.clearOnClientExitOrServerClose();
            NaturalPlantHandler.clearOnClientExitOrServerClose();
            BiomeClimateManager.clearOnClientExitOrServerClose(false);
            SnowChecker.clearOnClientExitOrServerClose();
            ClientRef.onClientPlayerExit();
            ClientCon.onClientPlayerExit();
            ESSortInfo.clearOnClientExitOrServerClose();
        }

        // ESConfigSync.INSTANCE.onClientPlayerExit();
    }


    public static void onLevelEventLoad(ClientLevel level) {
        if (CommonConfig.Season.validDimensions.get().contains(level.dimension().identifier().toString()))
            MapChecker.validDimension.add(level);

        ClientCon.setUseLevel(level);
        ClientCon.tick(level);
        // BiomeColorsHandler.reloadColors();
        // BiomeColorsHandler.needRefresh=true;

        WeatherManager.createLevelBiomeWeatherList(level);
        // 这里需要恢复一下数据
        // 客户端登录时同步天气数据，此处先放入
        SolarHolders.createSaveData(level, ClientSolarDataManager.get(level));
    }


    public static void onPlayerTick(Entity player) {
            IceKeeper.checkIfPlayerStepInFrozenWater(player);
    }

    private static long lastFreshTime = -1;


    public static void onLevelTick(ClientLevel clientLevel) {
        {
            ClientCon.tick(clientLevel);

            if ((!EclipticUtil.canSnowyBlockInteract() || ClientConfig.Renderer.enhancementChunkRenderUpdate.get())
                    && ClientConfig.Renderer.forceChunkRenderUpdate.get()) {
                if (clientLevel.getGameTime() - lastFreshTime > 80
                        || clientLevel.getGameTime() < lastFreshTime - 1) {
                    lastFreshTime = clientLevel.getGameTime();
                    if (Minecraft.getInstance().getCameraEntity() instanceof Player player) {
                        BlockPos pos = player.getOnPos();
                        SectionPos sectionPos = SectionPos.of(pos);
                        if (!ClientConfig.Renderer.enhancementChunkRenderUpdate.get()) {
                            WorldRenderer.setSectionDirtyWithNeighbors(sectionPos);
                            WorldRenderer.setSectionDirtyRandomly(sectionPos);
                        } else {
                            if (clientLevel.getRandom().nextInt(2) == 0) {
                                WorldRenderer.setAllDirty(sectionPos);
                            }
                        }
                    }

                }
            }
        }


    }


    public static void onRegisterClientCommandsEvent(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext context) {
        for (String modid : EclipticSeasonsApi.MODID_LIST) {
            dispatcher.register(ClientCommands.literal(modid)
                    .then(ClientCommands.literal("c_export")
                            .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                            .then(ClientCommands.argument("pos", BlockPosArgument.blockPos()).executes((stackCommandContext) ->
                                    MapExporter.exportMap((CommandSourceStack)stackCommandContext.getSource(), BlockPosArgument.getBlockPos((CommandContext)stackCommandContext, "pos"))))
                    )
                    .then(ClientCommands.literal("debug")
                            .then(ClientCommands.literal("info_hud")
                                    .then(ClientCommands.argument("info_enable", BoolArgumentType.bool()).executes((stackCommandContext) -> {
                                        ClientConfig.Debug.debugInfo.set(BoolArgumentType.getBool(stackCommandContext, "info_enable"));
                                        return 0;
                                    }))
                            )
                    )
            );
        }
    }


    public static void onLoggingIn(ClientPacketListener clientPacketListener, PacketSender packetSender, Minecraft minecraft) {
        ClientCon.ServerName =
                minecraft == null ||   minecraft.player == null ||minecraft.player.connection.getServerData() == null ? "Client" :
                        minecraft.player.connection.getServerData().name;
        // ClientCon.ServerName=event.getPlayer().connection.getConnection().getRemoteAddress().toString();
    }


    public static void onTagsUpdatedEvent(TagsUpdatedEvent tagsUpdatedEvent) {
        if (tagsUpdatedEvent.getUpdateCause() == TagsUpdatedEvent.UpdateCause.CLIENT_PACKET_RECEIVED) {
            ClientRef.updateClientSide(tagsUpdatedEvent.getLookupProvider());
        }
    }


}
