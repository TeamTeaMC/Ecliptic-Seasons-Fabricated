package com.teamtea.eclipticseasons.client.mixin.client;


import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.client.core.ClientWeatherChecker;
import com.teamtea.eclipticseasons.common.core.map.MapChecker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public class MixinClientLevel {

    @Inject(at = {@At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;")},
            method = {"precipitationAt"}, cancellable = true)
    private void eclipticseasons$client$precipitationAt_endBiomeCheck(BlockPos pos, CallbackInfoReturnable<Biome.Precipitation> cir) {
        if ((Object) this instanceof ClientLevel level) {
            Biome biome = MapChecker.getSurfaceBiome(level, pos).value();
            Biome.Precipitation precipitation = EclipticUtil.getRainOrSnow(level, biome, pos);
            cir.setReturnValue(precipitation);
        }
    }
}
