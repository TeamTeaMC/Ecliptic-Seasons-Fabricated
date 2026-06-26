package com.teamtea.eclipticseasons.common.network;

import com.teamtea.eclipticseasons.common.core.map.MapChecker;
import com.teamtea.eclipticseasons.common.item.info.GrowthInfoResolver;
import com.teamtea.eclipticseasons.common.network.clientmesage.GrowthInfoQuery;
import com.teamtea.eclipticseasons.common.network.message.*;
import com.teamtea.eclipticseasons.config.sync.ESConfigFilePayload;
import com.teamtea.eclipticseasons.config.sync.ESConfigSync;
import com.teamtea.eclipticseasons.config.sync.ESConfigToClientPayload;
import com.teamtea.eclipticseasons.config.sync.ESConfigToServerPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;
import java.util.Optional;

public final class SimpleNetworkHandler {


    public static void init() {
        PayloadTypeRegistry.clientboundPlay().register(SolarTermsMessage.TYPE, SolarTermsMessage.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(EmptyMessage.TYPE, EmptyMessage.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(BiomeWeatherMessage.TYPE, BiomeWeatherMessage.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ChunkUpdateMessage.TYPE, ChunkUpdateMessage.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ChunkBiomeUpdateMessage.TYPE, ChunkBiomeUpdateMessage.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(HumidModifyMessage.TYPE, HumidModifyMessage.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(UpdateTempChangeMessage.TYPE, UpdateTempChangeMessage.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(GrowthInfoMessage.TYPE, GrowthInfoMessage.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(GrowthInfoQuery.TYPE, GrowthInfoQuery.STREAM_CODEC);


        PayloadTypeRegistry.clientboundConfiguration().register(ESConfigFilePayload.TYPE, ESConfigFilePayload.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ESConfigToClientPayload.TYPE, ESConfigToClientPayload.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ESConfigToServerPayload.TYPE, ESConfigToServerPayload.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(
                ESConfigToServerPayload.TYPE,
                (payload, context) -> {
                    context.server().execute(() -> {
                        Player player = context.player();
                        if (player instanceof ServerPlayer serverPlayer)
                            ESConfigSync.INSTANCE.syncToSever(payload, serverPlayer);
                    });
                }
        );

        ServerPlayNetworking.registerGlobalReceiver(
                GrowthInfoQuery.TYPE,
                (payload, context) -> {
                    Player player = context.player();
                    context.server().execute(() -> {
                        if (player instanceof ServerPlayer serverPlayer
                                && MapChecker.isLoadedOnlyServer(serverPlayer.level(), payload.getPos()))
                            send(serverPlayer, new GrowthInfoMessage(Optional.ofNullable(GrowthInfoResolver.resolve(
                                    serverPlayer.level(), payload.getPos(), serverPlayer.level().getBlockState(payload.getPos())
                            ))));
                    });
                }
        );
    }


    public static <MSG extends CustomPacketPayload> void send(ServerPlayer player, MSG msg) {
        ServerPlayNetworking.send(player, msg);
    }

    public static <MSG extends CustomPacketPayload> void send(Collection<ServerPlayer> players, MSG msg) {
        players.forEach(player -> ServerPlayNetworking.send(player, msg));
    }
}