package com.teamtea.eclipticseasons.mixin.common.chunk;

import com.teamtea.eclipticseasons.common.AllListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.network.PlayerChunkSender;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PlayerChunkSender.class})
public class MixinPlayerChunkSender {
    @Inject(method = "sendChunk", at = @At("RETURN"))
    private static void eclipticseasons$sendChunkComponentsPackets(ServerGamePacketListenerImpl connection, ServerLevel level, LevelChunk chunk, CallbackInfo ci) {
        AllListener.onChunkWatch(level, chunk, chunk.getPos(), connection.player);
    }
}
