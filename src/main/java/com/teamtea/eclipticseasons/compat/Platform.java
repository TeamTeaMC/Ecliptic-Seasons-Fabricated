package com.teamtea.eclipticseasons.compat;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class Platform {

    public static boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    public static boolean isModsLoaded(List<String> ids) {
        return ids.stream().allMatch(Platform::isModLoaded);
    }

    public static boolean isPhysicalClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    public static MinecraftServer getServer() {
        return null;
    }

    public static boolean isProduction() {
        return !FabricLoader.getInstance().isDevelopmentEnvironment();
    }


    public static Optional<ModContainer> getModContainer(String id) {
        return FabricLoader.getInstance().getModContainer(id);
    }

    public static Path getModPath(String id) {
        return FabricLoader.getInstance().getModContainer(id)
                .map(container -> container.getRootPaths().get(0))
                .orElse(null);
    }
}