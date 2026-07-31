package com.teamtea.eclipticseasons.data;

import com.teamtea.eclipticseasons.data.datapack.DatapackRegistryGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class start implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(@NonNull FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();


        pack.addProvider(
                (output, registriesFuture) ->
                {
                    // CompletableFuture<RegistrySetBuilder.PatchedRegistries> tradeRebalanceWorldRegistries = TradeRebalanceRegistries.createPatchedWorldRegistries(
                    //         registriesFuture
                    // );
                    // RegistrySetBuilder.PatchedRegistries::patches
                    // CompletableFuture<RegistrySetBuilder.PatchedRegistries> tradeRebalanceReloadableRegistries = TradeRebalanceRegistries.createPatchedReloadable(
                    //         tradeRebalanceWorldRegistries.thenApply(RegistrySetBuilder.PatchedRegistries::full), registriesFuture
                    // );
                    return new DatapackRegistryGenerator(output, registriesFuture);
                });


    }

    @Override
    public void buildRegistry(@NonNull RegistrySetBuilder registryBuilder) {
      // DatapackRegistryGenerator.apply(registryBuilder);
      registryBuilder.entries.addAll(DatapackRegistryGenerator.REGISTRY_SET_BUILDER.entries);
      // registryBuilder.add(DatapackRegistryGenerator.REGISTRY_SET_BUILDER.);
    }

    // private static <T extends DataProvider> DataProvider.Factory<T> bindRegistries(
    //         final BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, T> target, final CompletableFuture<HolderLookup.Provider> registries
    // ) {
    //     return output -> target.apply(output, registries);
    // }

    // public static HolderLookup.Provider createReloadableLookup() {
    //     RegistryAccess.Frozen staticRegistries = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
    //     HolderLookup.Provider newRegistries = DatapackRegistryGenerator.REGISTRY_SET_BUILDER.build(staticRegistries);
    //     validateLootData(newRegistries);
    //     return newRegistries;
    // }

}
