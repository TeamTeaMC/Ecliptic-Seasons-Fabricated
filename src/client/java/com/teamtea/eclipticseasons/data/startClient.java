package com.teamtea.eclipticseasons.data;

import com.teamtea.eclipticseasons.data.font.ESFontProvider;
import com.teamtea.eclipticseasons.data.model.ES2ModelProvider;
import com.teamtea.eclipticseasons.data.sound.ESSoundDefinitionsProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import org.jspecify.annotations.NonNull;

public class startClient implements DataGeneratorEntrypoint {

    com.teamtea.eclipticseasons.data.start start = new start();

    @Override
    public void onInitializeDataGenerator(@NonNull FabricDataGenerator generator) {
        start.onInitializeDataGenerator(generator);

        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(ESSoundDefinitionsProvider::new);
        pack.addProvider(ES2ModelProvider::new);
        pack.addProvider(ESFontProvider::new);
    }

    @Override
    public void buildRegistry(@NonNull RegistrySetBuilder registryBuilder) {
        start.buildRegistry(registryBuilder);
    }
}
