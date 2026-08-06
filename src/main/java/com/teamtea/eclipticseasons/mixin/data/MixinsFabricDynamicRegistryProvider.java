package com.teamtea.eclipticseasons.mixin.data;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;


@Mixin({FabricDynamicRegistryProvider.class})
public abstract class MixinsFabricDynamicRegistryProvider {
    @WrapOperation(at = {@At(value = "INVOKE", target = "Lnet/minecraft/data/DataProvider;saveStable(Lnet/minecraft/data/CachedOutput;Lcom/google/gson/JsonElement;Ljava/nio/file/Path;)Ljava/util/concurrent/CompletableFuture;")}, method = {"writeToPath"})
    private static CompletableFuture<?> eclipticseasons$writeToPath_saveStable(CachedOutput cache, JsonElement root, Path path, Operation<CompletableFuture<?>> original) {
        // if ("true".equals(System.getProperty("eclipticseasons.runs.runData")))
        return eclipticseasons$lambda$static$0(cache, path, root);
    }

    @Unique
    private static CompletableFuture<?> eclipticseasons$lambda$static$0(CachedOutput output, Path path, JsonElement json) {
        return CompletableFuture.runAsync(() -> {
            try {

                ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                HashingOutputStream hashingoutputstream = new HashingOutputStream(Hashing.sha1(), bytearrayoutputstream);
                try (JsonWriter jsonwriter = new JsonWriter(new OutputStreamWriter(bytearrayoutputstream, StandardCharsets.UTF_8))) {
                    jsonwriter.setSerializeNulls(false);
                    jsonwriter.setIndent("  ");

                    // 使用 Gson 直接写入
                    Gson gson = new Gson();
                    gson.toJson(json, jsonwriter);
                }

                // 直接保存
                output.writeIfNeeded(path, bytearrayoutputstream.toByteArray(), hashingoutputstream.hash());
            } catch (IOException e) {
                DataProvider.LOGGER.error("Failed to save file to {}", path, e);
            }
        }, Util.backgroundExecutor().forName("saveStable"));
    }


}
