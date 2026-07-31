package com.teamtea.eclipticseasons.data.model;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

public class ES2ModelProvider extends FabricModelProvider {

    public ES2ModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModels) {
        ESBlockModelGenerators.builder().models(blockModels).build().run();
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModels) {
        ESItemModelGenerators.builder().models(itemModels).build().run();

    }
}
