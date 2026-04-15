package com.teamtea.eclipticseasons.common.network;

import com.teamtea.eclipticseasons.common.network.message.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class SimpleNetworkHandlerClient {
    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(SolarTermsMessage.TYPE, (payload, context) -> {
            context.client().execute(() -> NetworkUtil.processSolarTermsMessage2(payload, context));
        });

        ClientPlayNetworking.registerGlobalReceiver(EmptyMessage.TYPE, (payload, context) -> {
            context.client().execute(() -> NetworkUtil.processEmptyMessage(payload, context));
        });

        ClientPlayNetworking.registerGlobalReceiver(BiomeWeatherMessage.TYPE, (payload, context) -> {
            context.client().execute(() -> NetworkUtil.processBiomeWeatherMessage(payload, context));
        });

        ClientPlayNetworking.registerGlobalReceiver(ChunkUpdateMessage.TYPE, (payload, context) -> {
            context.client().execute(() -> NetworkUtil.processChunkUpdateMessage(payload, context));
        });

        ClientPlayNetworking.registerGlobalReceiver(ChunkBiomeUpdateMessage.TYPE, (payload, context) -> {
            context.client().execute(() -> NetworkUtil.processChunkBiomeUpdateMessage(payload, context));
        });

        ClientPlayNetworking.registerGlobalReceiver(HumidModifyMessage.TYPE, (payload, context) -> {
            context.client().execute(() -> NetworkUtil.processHumidModifyMessage(payload, context));
        });

        ClientPlayNetworking.registerGlobalReceiver(UpdateTempChangeMessage.TYPE, (payload, context) -> {
            context.client().execute(() -> NetworkUtil.processUpdateTempChangeMessage(payload, context));
        });
    }

}
