package com.teamtea.eclipticseasons.common.network;

import com.teamtea.eclipticseasons.common.network.message.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public final class SimpleNetworkHandler {

    /**
     * 在 ModInitializer.onInitialize() 中调用
     * 负责注册所有数据包的序列化逻辑 (StreamCodec)
     */
    public static void init() {
        // 注册发送给客户端的数据包类型 (S2C)
        PayloadTypeRegistry.clientboundPlay().register(SolarTermsMessage.TYPE, SolarTermsMessage.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(EmptyMessage.TYPE, EmptyMessage.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(BiomeWeatherMessage.TYPE, BiomeWeatherMessage.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ChunkUpdateMessage.TYPE, ChunkUpdateMessage.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ChunkBiomeUpdateMessage.TYPE, ChunkBiomeUpdateMessage.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(HumidModifyMessage.TYPE, HumidModifyMessage.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(UpdateTempChangeMessage.TYPE, UpdateTempChangeMessage.STREAM_CODEC);
    }

    /**
     * 在 ClientModInitializer.onInitializeClient() 中调用
     * 负责注册客户端如何处理收到的服务器数据包
     */

    // --- 发送工具类 ---

    public static <MSG extends CustomPacketPayload> void send(ServerPlayer player, MSG msg) {
        ServerPlayNetworking.send(player, msg);
    }

    public static <MSG extends CustomPacketPayload> void send(Collection<ServerPlayer> players, MSG msg) {
        players.forEach(player -> ServerPlayNetworking.send(player, msg));
    }
}