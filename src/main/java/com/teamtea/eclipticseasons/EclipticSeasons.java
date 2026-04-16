package com.teamtea.eclipticseasons;


import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import com.teamtea.eclipticseasons.common.AllListener;
import com.teamtea.eclipticseasons.common.block.IceOrSnowCauldronBlock;
import com.teamtea.eclipticseasons.common.command.CommandHandler;
import com.teamtea.eclipticseasons.common.hook.ESEventHook;
import com.teamtea.eclipticseasons.common.network.SimpleNetworkHandler;
import com.teamtea.eclipticseasons.common.registry.*;
import com.teamtea.eclipticseasons.compat.CompatModule;
import com.teamtea.eclipticseasons.compat.eclipticseasons_bundles.EclipticSeasonsBundles;
import com.teamtea.eclipticseasons.config.ClientConfig;
import com.teamtea.eclipticseasons.config.CommonConfig;
import com.teamtea.eclipticseasons.config.StartConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.resources.Identifier;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.config.ModConfig;
import warp.net.neoforged.neoforge.event.TagsUpdatedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladysnake.cca.api.v3.chunk.ChunkSyncCallback;

import java.util.List;
import java.util.Locale;


public class EclipticSeasons implements ModInitializer {
    public static final String MODID = EclipticSeasonsApi.MODID;
    public static final String SMODID = EclipticSeasonsApi.SMODID;

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger(EclipticSeasonsApi.MODID);
    public static final String NETWORK_VERSION = "1.0";


    public static String defaultConfigName(ModConfig.Type type, String modId) {
        // config file name would be "forge-client.toml" and "forge-server.toml"
        return String.format(Locale.ROOT, "%s-%s.toml", modId, type.extension());
    }

    public static Identifier rl(String id) {
        return Identifier.fromNamespaceAndPath(EclipticSeasonsApi.MODID, id);
    }

    public static Identifier erl(String modid, String id) {
        return Identifier.fromNamespaceAndPath(modid, id);
    }

    public static Identifier parse(String id) {
        return Identifier.parse(id);
    }


    public static void logger(Exception exception) {
        LOGGER.error(exception);
    }


    public static void logger(String x) {
        LOGGER.info(x);
    }

    public static void logger(Object... x) {
        extraLogger(false, x);
    }

    public static void extraLogger(boolean debug, Object... x) {

        // if (!FMLEnvironment.production||General.bool.get())
        {
            StringBuilder output = new StringBuilder();

            for (Object i : x) {
                if (i == null) output.append(", ").append("null");
                else if (i.getClass().isArray()) {
                    output.append(", [");
                    if (i instanceof Object[] objects) {
                        for (Object c : objects) {
                            output.append(c).append(",");
                        }
                    } else if (i instanceof float[] objects) {
                        for (float c : objects) {
                            output.append(c).append(",");
                        }
                    } else if (i instanceof int[] objects) {
                        for (int c : objects) {
                            output.append(c).append(",");
                        }
                    } else if (i instanceof double[] objects) {
                        for (double c : objects) {
                            output.append(c).append(",");
                        }
                    } else if (i instanceof long[] objects) {
                        for (long c : objects) {
                            output.append(c).append(",");
                        }
                    } else if (i instanceof boolean[] objects) {
                        for (boolean c : objects) {
                            output.append(c).append(",");
                        }
                    }
                    output.append("]");
                } else if (i instanceof List list) {
                    output.append(", [");
                    for (Object c : list) {
                        output.append(c);
                    }
                    output.append("]");
                } else
                    output.append(", ").append(i);
            }
            if (debug) {
                LOGGER.debug(output.substring(1));
            } else {
                LOGGER.info(output.substring(1));
            }
        }

    }

    @Override
    public void onInitialize() {

        fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.COMMON, CommonConfig.COMMON_CONFIG);
        fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.CLIENT, ClientConfig.CLIENT_CONFIG);
        fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.STARTUP, StartConfig.START_CONFIG);

        fuzs.forgeconfigapiport.fabric.api.v5.ModConfigEvents.loading(MODID).register(CommonConfig::UpdateConfig);
        // fuzs.forgeconfigapiport.fabric.api.v5.ModConfigEvents.reloading(MODID).register(CommonConfig::UpdateConfig);

        fuzs.forgeconfigapiport.fabric.api.v5.ModConfigEvents.loading(MODID).register(ClientConfig::UpdateConfig);
        // fuzs.forgeconfigapiport.fabric.api.v5.ModConfigEvents.reloading(MODID).register(ClientConfig::UpdateConfig);


        CompatModule.setup();

        ModContents.registerBuiltinResourcePacks();
        ParticleRegistry.init();
        SoundEventsRegistry.init();
        ModContents.onNewRegistry();
        IceOrSnowCauldronBlock.init();
        EffectRegistry.init();

        CommandRegistrationCallback.EVENT.register(CommandHandler::onRegisterCommands);



        BlockRegistry.init();
        ItemRegistry.init();
        BlockEntityRegistry.init();
        ModAdvancements.init();
        AttachmentRegistry.init();

        EnvironmentAttributeRegistry.init();
        AttributeTypeRegistry.init();


        LootItemConditionRegistry.init();

        SimpleNetworkHandler.init();


        registerEvent();

        EclipticSeasonsBundles.init();
    }

    private void registerEvent() {
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
            if (success) {
                fireTagUpdated(server);
            }
        });
        ServerLifecycleEvents.SERVER_STARTED.register(EclipticSeasons::fireTagUpdated);

        // ESEventHook.TAG_UPDATED.register(AllListener::onTagsUpdatedEventEarly);
        // ESEventHook.TAG_UPDATED.register(AllListener::onTagsUpdatedEvent);

        ServerLifecycleEvents.SERVER_STARTING.register(AllListener::onServerAboutToStartEvent);
        ServerLifecycleEvents.SERVER_STOPPING.register(AllListener::onServerStoppingEvent);

        ServerTickEvents.END_LEVEL_TICK.register(AllListener::onLevelTick);
        ServerTickEvents.START_LEVEL_TICK.register(AllListener::onLevelTickPre);

        ESEventHook.SOLAR_TERM_CHANGE.register(AllListener::onSolarTermChangeEvent);
        ESEventHook.CHECK_PLANT_GROWTH.register(AllListener::onCropGrowUp);

        ServerLevelEvents.LOAD.register((s, l) -> AllListener.onLevelLoad(l));
        ServerLevelEvents.UNLOAD.register((s, l) -> AllListener.onLevelUnloadEvent(l));


        ServerTickEvents.START_SERVER_TICK.register(
                e -> {
                    for (ServerPlayer player : e.getPlayerList().getPlayers()) {
                        AllListener.onPlayerTickPre(player);
                    }
                }
        );
        ServerTickEvents.END_SERVER_TICK.register(
                e -> {
                    for (ServerPlayer player : e.getPlayerList().getPlayers()) {
                        AllListener.onPlayerTickPost(player);
                    }
                }
        );

        ServerChunkEvents.CHUNK_LOAD.register(AllListener::onChunkLoad);
        ServerChunkEvents.CHUNK_UNLOAD.register(AllListener::onChunkUnloadEvent);

        ServerPlayConnectionEvents.JOIN.register(AllListener::onPlayerLoggedIn);

        ServerPlayerEvents.AFTER_RESPAWN.register(AllListener::onPlayerChangedDimension);
        ServerPlayerEvents.COPY_FROM.register(AllListener::onPlayerChangedDimension2);

        // cca
        ChunkSyncCallback.EVENT.register((player, chunk) ->
                AllListener.onChunkWatch(player.level(), chunk, chunk.getPos(), player));
    }

    private static void fireTagUpdated(MinecraftServer server) {
        TagsUpdatedEvent tagsUpdatedEvent = TagsUpdatedEvent.builder()
                .lookupProvider(server.registryAccess())
                .integratedServer(server instanceof DedicatedServer)
                .updateCause(TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD)
                .build();
        AllListener.onTagsUpdatedEventEarly(tagsUpdatedEvent);
        AllListener.onTagsUpdatedEvent(tagsUpdatedEvent);
    }
}
