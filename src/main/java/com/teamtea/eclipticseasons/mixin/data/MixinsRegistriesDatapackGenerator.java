package com.teamtea.eclipticseasons.mixin.data;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import net.minecraft.core.Holder;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Mixin({RegistriesDatapackGenerator.class})
public abstract class MixinsRegistriesDatapackGenerator {

    // @Inject(at = {@At("HEAD")}, method = {"lambda$dumpRegistryCap$1"}, cancellable = true)
    // private static void eclipticseasons$lambda$dumpRegistryCap$11(PackOutput.PathProvider pathProvider, CachedOutput cache, DynamicOps writeOps, RegistryDataLoader.RegistryData v, Holder.Reference e, CallbackInfoReturnable<CompletableFuture> cir) {
    //     if ("true".equals(System.getProperty("eclipticseasons.runs.runData")))
    //         cir.setReturnValue(dumpValue2(
    //                 pathProvider.json(e.key().identifier()),
    //                 cache,
    //                 writeOps,
    //                 conditionalCodec,
    //                 Optional.of(new net.neoforged.neoforge.common.conditions.WithConditions<>(conditions.getOrDefault(p_256105_.key(), List.of()), p_256105_.value()))
    //         ));
    // }
    //
    // @Unique
    // private static <E> CompletableFuture<?> dumpValue2(
    //         Path p_255678_, CachedOutput p_256438_, DynamicOps<JsonElement> p_256127_, Encoder<Optional<WithConditions<E>>> p_255938_, Optional<net.neoforged.neoforge.common.conditions.WithConditions<E>> p_256590_
    // ) {
    //     return p_255938_.encodeStart(p_256127_, p_256590_)
    //             .mapOrElse(
    //                     p_351699_ -> eclipticseasons$lambda$static$0(p_256438_, p_255678_, p_351699_),
    //                     p_351701_ -> CompletableFuture.failedFuture(new IllegalStateException("Couldn't generate file '" + p_255678_ + "': " + p_351701_.message()))
    //             );
    // }

    @Inject(at = {@At("HEAD")}, method = {"lambda$dumpValue$0"}, cancellable = true)
    private static void eclipticseasons$lambda$dumpRegistryCap$11(CachedOutput cache, Path path, JsonElement result, CallbackInfoReturnable<CompletableFuture> cir) {
        // if ("true".equals(System.getProperty("eclipticseasons.runs.runData")))
        cir.setReturnValue(eclipticseasons$lambda$static$0(cache, path, result));
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
