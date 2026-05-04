package com.teamtea.eclipticseasons.client;

import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.client.color.season.BiomeColorsHandler;
import com.teamtea.eclipticseasons.client.color.season.FoliageColorSource;
import com.teamtea.eclipticseasons.client.core.AttachModelManager;
import com.teamtea.eclipticseasons.client.itemproperties.CounterModelProperty;
import com.teamtea.eclipticseasons.client.particle.*;
import com.teamtea.eclipticseasons.client.registry.KeyMappingRegistry;
import com.teamtea.eclipticseasons.client.reload.ClientJsonCacheListener;
import com.teamtea.eclipticseasons.client.render.ber.*;
import com.teamtea.eclipticseasons.client.util.ClientClientAgent;
import com.teamtea.eclipticseasons.client.util.ClientCon;
import com.teamtea.eclipticseasons.common.registry.BlockEntityRegistry;
import com.teamtea.eclipticseasons.common.registry.ParticleRegistry;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.SimpleUnbakedExtraModel;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.level.block.Blocks;
import warp.net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.stream.Stream;

public class ClientSetup {


    public static void addRegisterRangeSelectItemModelPropertyEvent() {
        RangeSelectItemModelProperties.ID_MAPPER.put(EclipticSeasons.rl("meter"), CounterModelProperty.MAP_CODEC);
    }

    // public static void addRegisterPictureInPictureRenderersEvent(RegisterPictureInPictureRenderersEvent event) {
    //     event.register(GuiBlockRenderState.class, GuiBlockRenderer::new);
    //     event.register(GuiFluidRenderState.class, GuiFluidRenderer::new);
    // }

    public static void onRegisterKeyMappingsEvent() {
        KeyMappingHelper.registerKeyMapping(KeyMappingRegistry.DEBUG_KEY);
        KeyMappingHelper.registerKeyMapping(KeyMappingRegistry.DEBUG_KEY_1);
    }

    public static void onParticleProviderRegistry() {
        ParticleProviderRegistry.getInstance().register(ParticleRegistry.FIREFLY, (spriteSet) ->
                (type, level, x, y, z, dx, dy, dz, rand) -> new FireflyParticle(level, x, y, z, spriteSet));

        ParticleProviderRegistry.getInstance().register(ParticleRegistry.WILD_GOOSE, (spriteSet) ->
                (type, level, x, y, z, dx, dy, dz, rand) -> new WildGooseParticle(level, x, y, z, 0.01, 0.01, 0.01, spriteSet));

        ParticleProviderRegistry.getInstance().register(ParticleRegistry.BUTTERFLY, (spriteSet) ->
                (type, level, x, y, z, dx, dy, dz, rand) -> new ButterflyParticle(level, x, y, z, spriteSet));

        ParticleProviderRegistry.getInstance().register(ParticleRegistry.FALLEN_LEAVES, (spriteSet) ->
                (type, level, x, y, z, dx, dy, dz, rand) -> new FallenLeavesParticle(level, x, y, z, dx, dy, dz, type, spriteSet));

        ParticleProviderRegistry.getInstance().register(ParticleRegistry.FLYING_BLOOM, (spriteSet) ->
                (type, level, x, y, z, dx, dy, dz, rand) -> new FallenLeavesParticle(level, x, y, z, dx, dy, dz, type, spriteSet));

        ParticleProviderRegistry.getInstance().register(ParticleRegistry.GREENHOUSE, (spriteSet) ->
                (type, level, x, y, z, dx, dy, dz, rand) -> new GreenHouseParticle(level, x, y, z, dx, dy, dz, type, spriteSet));
    }


    public static void onClientEvent() {
        BiomeColors.GRASS_COLOR_RESOLVER = BiomeColorsHandler.GRASS_COLOR;
        BiomeColors.FOLIAGE_COLOR_RESOLVER = BiomeColorsHandler.FOLIAGE_COLOR;
        BiomeColors.DRY_FOLIAGE_COLOR_RESOLVER = BiomeColorsHandler.DRY_FOLIAGE_COLOR;
        ClientCon.agent = new ClientClientAgent();
    }


    public static void onRegisterRenderers() {
        BlockEntityRenderers.register(
                BlockEntityRegistry.calendar_entity_type,
                CalendarBlockEntityRenderer::new
        );
    }

    public static void registerExtraModels(ModelLoadingPlugin.@UnknownNullability Context event) {
        AttachModelManager.registerExtraSnowyModels(event::addModel);
        // event.register();
        // Minecraft.getInstance().getResourceManager().listPacks().toList().get(0).getResource(PackType.CLIENT_RESOURCES, ResourceLocation.withDefaultNamespace("textures/block/snow.png")).get()
        // IOUtils.toString(Minecraft.getInstance().getResourceManager().listPacks().toList().get(0).getResource(PackType.SERVER_DATA, ResourceLocation.withDefaultNamespace("recipe/yellow_terracotta.json")).get(), StandardCharsets.UTF_8)        event.register(ModelManager.snowy_fern);
        registerStandalone(event, AttachModelManager.snowy_custom);
        registerStandalone(event, AttachModelManager.snowy_custom_ao);

        registerStandalone(event, AttachModelManager.stairs_top);
        registerStandalone(event, AttachModelManager.snowy_leaves_attach);
        registerStandalone(event, AttachModelManager.snowy_leaves_top);
        registerStandalone(event, AttachModelManager.snowy_fern);
        registerStandalone(event, AttachModelManager.snowy_grass);
        registerStandalone(event, AttachModelManager.snowy_tall_grass_top);
        registerStandalone(event, AttachModelManager.snowy_tall_grass_bottom);
        registerStandalone(event, AttachModelManager.snowy_large_fern_top);
        // 注意这里使用地址和model地址效果不同，后者需要写blockstate
        registerStandalone(event, AttachModelManager.snowy_large_fern_bottom);
        registerStandalone(event, AttachModelManager.overlay_2);
        registerStandalone(event, AttachModelManager.snow_height2);
        registerStandalone(event, AttachModelManager.snow_height2_top);
        registerStandalone(event, AttachModelManager.grass_flower);

        for (var flowerOnGrass : Stream.of(AttachModelManager.flower_on_grass,
                        AttachModelManager.fourleaf_clovers,
                        AttachModelManager.snow_edge_overlays,
                        AttachModelManager.leaf_piles)
                .flatMap(List::stream)
                .toList()) {
            registerStandalone(event, flowerOnGrass);
        }

        registerStandalone(event, AttachModelManager.ice);
    }

    private static void registerStandalone(ModelLoadingPlugin.@UnknownNullability Context event, StandaloneModelKey<BlockStateModel> snowyCustom) {
        event.addModel(snowyCustom.toFabric(), SimpleUnbakedExtraModel.blockStateModel(Identifier.parse(snowyCustom.getName())) );
    }


    public static void onModelBaked(ModelBakery.BakingResult modelRegistry) {
        ParticleUtil.onReloadResource();
        AttachModelManager.clearForRebaked(modelRegistry);
    }

    public static class ModelImpl implements ModelLoadingPlugin {
        @Override
        public void initialize(@NonNull Context context) {
            registerExtraModels(context);
        }
    }


    public static void onRegisterColorHandlersEvent_Block() {
        BlockColorRegistry.register(List.of(new FoliageColorSource()),
                Blocks.SPRUCE_LEAVES,
                Blocks.BIRCH_LEAVES,
                Blocks.MANGROVE_LEAVES);
    }


    public static void onRegisterClientReloadListeners() {
        registerListener(EclipticSeasons.rl(ClientJsonCacheListener.DIRECTORY_BIOME.substring(16)), ClientJsonCacheListener.biomeCache);
        registerListener(EclipticSeasons.rl(ClientJsonCacheListener.DIRECTORY_LEAF.substring(16)), ClientJsonCacheListener.leafCache);
        registerListener(EclipticSeasons.rl(ClientJsonCacheListener.DIRECTORY_SNOW_DEFINITION.substring(16)), ClientJsonCacheListener.snowDefOverrideCache);
        registerListener(EclipticSeasons.rl(ClientJsonCacheListener.DIRECTORY_AMBIENT.substring(16)), ClientJsonCacheListener.ambientCache);
        registerListener(EclipticSeasons.rl(ClientJsonCacheListener.DIRECTORY_MODEL_DEFINITION.substring(16)), ClientJsonCacheListener.modelDefCache);
        registerListener(EclipticSeasons.rl(ClientJsonCacheListener.DIRECTORY_SEASON_TEXTURES.substring(16)), ClientJsonCacheListener.textureReMappingsCache);
        registerListener(EclipticSeasons.rl(ClientJsonCacheListener.DIRECTORY_SEASON_DEFINITION.substring(16)), ClientJsonCacheListener.seasonDefCache);
        registerListener(EclipticSeasons.rl(ClientJsonCacheListener.DIRECTORY_UI_PARSER.substring(16)), ClientJsonCacheListener.uiParserCache);
        registerListener(EclipticSeasons.rl(ClientJsonCacheListener.DIRECTORY_BACKGROUND_MUSIC.substring(16)), ClientJsonCacheListener.backgroundMusicCache);
    }

    private static void registerListener(Identifier id, PreparableReloadListener listener) {
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloadListener(id, listener);
    }

}
