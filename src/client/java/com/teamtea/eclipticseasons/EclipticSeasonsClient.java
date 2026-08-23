package com.teamtea.eclipticseasons;

import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import com.teamtea.eclipticseasons.client.ClientEventHandler;
import com.teamtea.eclipticseasons.client.ClientSetup;
import com.teamtea.eclipticseasons.common.AllListener;
import com.teamtea.eclipticseasons.common.hook.ESEventHook;
import com.teamtea.eclipticseasons.common.network.SimpleNetworkHandlerClient;
import com.teamtea.eclipticseasons.compat.CompatModule;
import com.teamtea.eclipticseasons.compat.Platform;
import com.teamtea.eclipticseasons.compat.voxy.client.VoxyEsHandler;
import com.teamtea.eclipticseasons.config.ClientConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLevelEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.fabric.impl.resource.ResourceLoaderImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import warp.net.neoforged.neoforge.event.TagsUpdatedEvent;

public class EclipticSeasonsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.INIT.register((a, b) -> ClientConfig.UpdateConfig());

        ClientSetup.addRegisterRangeSelectItemModelPropertyEvent();
        ClientSetup.onRegisterKeyMappingsEvent();
        ClientSetup.onParticleProviderRegistry();
        ClientSetup.onRegisterColorHandlersEvent_Block();
        ClientSetup.onRegisterRenderers();
        ClientSetup.onRegisterClientReloadListeners();

        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipFlag, list) ->
                ClientEventHandler.addTooltips(itemStack, tooltipContext, tooltipFlag, list));

        // This entrypoint is suitable for setting up client-specific logic, such as rendering.

        if (CompatModule.isVoxy()) {
            ESEventHook.SOLAR_TERM_CHANGE.register(VoxyEsHandler.INSTANCE::onSolarTermChangeEvent);
            ClientPlayConnectionEvents.DISCONNECT.register(VoxyEsHandler.INSTANCE::onLoggingIn);
        }
        if (CompatModule.isSodium()) {
            ModContainer container = FabricLoader.getInstance()
                    .getModContainer(EclipticSeasonsApi.MODID)
                    .orElseThrow();


            if (Platform.isPhysicalClient()) {
                // ResourceLoader.registerBuiltinPack(
                //         EclipticSeasons.rl("legacy_snowy_block"),
                //         container,
                //         Component.translatable("pack.eclipticseasons.legacy_snowy_block"),
                //         PackActivationType.NORMAL
                // );
                ResourceLoaderImpl.registerBuiltinPack(
                        EclipticSeasons.rl("snowy_sodium_stairs"),
                        "resourcepacks/SnowySodiumStairs",
                        container,
                        Component.translatable("pack.eclipticseasons.snowy_sodium_stairs"),
                        PackActivationType.DEFAULT_ENABLED);
            }
        }

        registerEvent();

        ClientSetup.onClientEvent();
        SimpleNetworkHandlerClient.initClient();


        ClientCommandRegistrationCallback.EVENT.register(ClientEventHandler::onRegisterClientCommandsEvent);

        ModelLoadingPlugin.register(new ClientSetup.ModelImpl());
    }

    private void registerEvent() {
        ClientPlayConnectionEvents.JOIN.register((listener, sender, client) -> {
            TagsUpdatedEvent tagsUpdatedEvent = TagsUpdatedEvent.ClientPacketReceived.builder()
                    .registries(listener.registryAccess())
                    .integratedServer(Minecraft.getInstance().isLocalServer())
                    // .updateCause(TagsUpdatedEvent.UpdateCause.CLIENT_PACKET_RECEIVED)
                    .build();
            AllListener.onTagsUpdatedEventEarly(tagsUpdatedEvent);
            AllListener.onTagsUpdatedEvent(tagsUpdatedEvent);
            ClientEventHandler.onTagsUpdatedEvent(tagsUpdatedEvent);
        });

        ClientTickEvents.END_LEVEL_TICK.register(AllListener::onLevelTick);

        ClientTickEvents.END_LEVEL_TICK.register(ClientEventHandler::onLevelTick);
        ClientTickEvents.END_LEVEL_TICK.register(c -> c.tickingEntities.forEach(ClientEventHandler::onPlayerTick));

        // ClientLevelEvents.AFTER_CLIENT_LEVEL_CHANGE.register((s, l)->AllListener.onLevelUnloadEvent(l));

        ClientLevelEvents.AFTER_CLIENT_LEVEL_CHANGE.register((s, l) -> ClientEventHandler.onLevelEventLoad(l));


        ClientChunkEvents.CHUNK_LOAD.register(AllListener::onChunkLoad);
        ClientChunkEvents.CHUNK_UNLOAD.register(AllListener::onChunkUnloadEvent);
        ClientChunkEvents.CHUNK_UNLOAD.register(ClientEventHandler::onChunkUnloadEvent);

        ClientTickEvents.END_CLIENT_TICK.register(_ -> ClientEventHandler.onRenderTick());
        ESEventHook.SOLAR_TERM_CHANGE.register(ClientEventHandler::onSolarTermChangeEvent);

        ClientPlayConnectionEvents.DISCONNECT.register(ClientEventHandler::onPlayerExit);
        ClientPlayConnectionEvents.JOIN.register(ClientEventHandler::onLoggingIn);

    }
}