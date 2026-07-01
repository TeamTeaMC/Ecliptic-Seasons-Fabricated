package warp.net.neoforged.neoforge.server;

import com.teamtea.eclipticseasons.common.core.biome.WeatherManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

public final class ServerLifecycleHooks {
    public static MinecraftServer SERVER;

    public static MinecraftServer getCurrentServer() {
        MinecraftServer server = SERVER;
        if (server == null) {
            ServerLevel mainServerLevel = WeatherManager.getMainServerLevel();
            if (mainServerLevel != null)
                return mainServerLevel.getServer();
        }
        return server;
    }
}
