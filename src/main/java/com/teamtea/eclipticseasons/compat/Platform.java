package com.teamtea.eclipticseasons.compat;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class Platform {

    public static boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id)
                || FabricLoader.getInstance().isModLoaded(id.replaceAll("_", "-"));
    }

    public static String getModName(String id, int index) {
        if (index != 0) {
            return "";
        }

        FabricLoader loader = FabricLoader.getInstance();
        return loader.getModContainer(id)
                .or(() -> loader.getModContainer(id.replace('_', '-')))
                .map(container -> container.getMetadata().getName())
                .orElse("");
    }


    public static boolean isModsLoaded(List<String> ids) {
        return ids.stream().allMatch(Platform::isModLoaded);
    }

    public static boolean isPhysicalClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    // public static MinecraftServer getServer() {
    //     return null;
    // }

    public static boolean isProduction() {
        return !FabricLoader.getInstance().isDevelopmentEnvironment();
    }


    public static Optional<ModContainer> getModFile(String id) {
        return FabricLoader.getInstance().getModContainer(id);
    }

    public static Path getModPath(String id) {
        return FabricLoader.getInstance().getModContainer(id)
                .map(container -> container.getRootPaths().get(0))
                .orElse(null);
    }

    public static boolean isVersionSatisfied(String modId, String require) {
        return FabricLoader.getInstance().getModContainer(modId)
                .map(container -> {
                    Version currentVersion = container.getMetadata().getVersion();
                    try {
                        VersionPredicate predicate = VersionPredicate.parse(">=" + require);
                        return predicate.test(currentVersion);
                    } catch (VersionParsingException e) {
                        return false;
                    }
                })
                .orElse(false);
    }
}