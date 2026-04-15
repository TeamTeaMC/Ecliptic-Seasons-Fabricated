package com.teamtea.eclipticseasons.mixin.common.level;


import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.common.core.biome.WeatherManager;
import com.teamtea.eclipticseasons.config.CommonConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MinecraftServer.class})
public abstract class MixinMinecraftServer {


    @Shadow
    public abstract Iterable<ServerLevel> getAllLevels();

    @Inject(at = {@At("HEAD")}, method = {"setWeatherParameters"}, cancellable = true)
    public void eclipticseasons$setWeatherParameters(int pClearTime, int pWeatherTime, boolean pIsRaining, boolean pIsThundering, CallbackInfo ci) {
        for (ServerLevel allLevel : getAllLevels()) {
            if (EclipticUtil.hasLocalWeather(allLevel)) {
                WeatherManager.onSetWeatherParameters(allLevel, pClearTime, pWeatherTime, pIsRaining, pIsThundering);
            }
        }
        if (CommonConfig.Weather.useSolarWeather.get())
            ci.cancel();
    }
}
