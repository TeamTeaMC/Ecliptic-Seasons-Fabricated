package com.teamtea.eclipticseasons.mixin.common.level;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.teamtea.eclipticseasons.api.misc.test.ResourceKeyHolder;
import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.common.environment.SolarTime;
import com.teamtea.eclipticseasons.common.core.biome.WeatherManager;
import com.teamtea.eclipticseasons.common.core.crop.CropGrowthHandler;
import com.teamtea.eclipticseasons.common.core.map.MapChecker;
import com.teamtea.eclipticseasons.common.handler.CustomRandomTickHandler;
import com.teamtea.eclipticseasons.config.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.attribute.EnvironmentAttributeSystem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ServerLevel.class})
public abstract class MixinServerLevel extends Level {


    protected MixinServerLevel(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    @Shadow
    public abstract ServerLevel getLevel();

    @WrapOperation(at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;")},
            method = {"advanceWeatherCycle"})
    private <T> T eclipticseasons$wether(GameRules instance, GameRule<T> gameRule, Operation<T> original) {
        if (gameRule == GameRules.ADVANCE_WEATHER)
            return (T) (Boolean) false;
        return original.call(instance, gameRule);
    }

    @Inject(at = {@At("HEAD")}, method = {"advanceWeatherCycle"})
    public void eclipticseasons$advanceWeatherCycle(CallbackInfo ci) {
        WeatherManager.agentAdvanceWeatherCycle(getLevel(), random);
    }

    @WrapOperation(at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/attribute/EnvironmentAttributeSystem$Builder;build()Lnet/minecraft/world/attribute/EnvironmentAttributeSystem;")},
            method = {"<init>"})
    private EnvironmentAttributeSystem eclipticseasons$addEnvironmentAttributeLayers(EnvironmentAttributeSystem.Builder instance, Operation<EnvironmentAttributeSystem> original) {
        SolarTime.attachSolarLayer(this, instance);
        EnvironmentAttributeSystem call = original.call(instance);
        if (call instanceof ResourceKeyHolder rk) rk.setResourceKey(dimension());
        return call;
    }


    @Inject(
            method = "tickChunk",
            at = @At(value = "TAIL")
    )
    private void eclipticseasons$tickChunk_end(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        CropGrowthHandler.handleChunkTick(this, chunk);
    }


    @Inject(
            method = "tickPrecipitation",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;")
    )
    private void eclipticseasons$tickPrecipitation_setBiome_before(BlockPos blockPos, CallbackInfo ci, @Share("biome_holder") LocalRef<Holder<Biome>> biome, @Local(ordinal = 1) BlockPos posAbove) {
        biome.set(MapChecker.getSurfaceBiome(this, posAbove));
    }

    @WrapOperation(
            method = "tickPrecipitation",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;")
    )
    private Holder<Biome> eclipticseasons$tickPrecipitation_setBiome(ServerLevel instance, BlockPos pos, Operation<Holder<Biome>> original, @Share("biome_holder") LocalRef<Holder<Biome>> biome) {
        if (biome.get() == null) {
            biome.set(original.call(instance, pos));
        }
        return biome.get();
    }

    @Inject(
            method = "tickPrecipitation",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/biome/Biome;shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z")
    )
    private void eclipticseasons$tickPrecipitation_melt(BlockPos blockPos, CallbackInfo ci, @Local(ordinal = 1) BlockPos aboveGroundPos, @Share("biome_holder") LocalRef<Holder<Biome>> biome) {
        if (CommonConfig.Temperature.iceMelt.get()) {
            // if((getLevel()).isAreaLoaded(blockPos, 1))
            {
                CustomRandomTickHandler.SNOW_MELT_2.tick(getLevel(), biome.get(), aboveGroundPos);
            }
        }
    }

    @WrapOperation(
            method = "tickPrecipitation",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/biome/Biome;shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z")
    )
    private boolean eclipticseasons$tickPrecipitation_freeze(Biome instance, LevelReader pLevel, BlockPos pPos, Operation<Boolean> original, @Local(ordinal = 2) BlockPos groundPos, @Share("biome_holder") LocalRef<Holder<Biome>> biome) {
        return CustomRandomTickHandler.checkExtraFreezeCondition(getLevel(), biome.get(), groundPos);
    }

    @WrapOperation(
            method = "tickPrecipitation",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/biome/Biome;shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z")
    )
    private boolean eclipticseasons$tickPrecipitation_snow(Biome instance, LevelReader levelReader, BlockPos level, Operation<Boolean> original, @Local(ordinal = 1) BlockPos aboveGroundPos, @Share("biome_holder") LocalRef<Holder<Biome>> biome) {
        return CustomRandomTickHandler.checkExtraSnowCondition(getLevel(), biome.get(), aboveGroundPos);
    }


    @WrapOperation(
            method = "tickPrecipitation",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;I)Lnet/minecraft/world/level/biome/Biome$Precipitation;")
    )
    private Biome.Precipitation eclipticseasons$tickPrecipitation_getPrecipitationAt(Biome instance, BlockPos pos, int seaLevel, Operation<Biome.Precipitation> original) {
        var serverLevel = getLevel();
        return WeatherManager.getPrecipitationAt(serverLevel, instance, pos);
    }
}
