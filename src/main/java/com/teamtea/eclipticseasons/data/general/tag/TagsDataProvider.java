package com.teamtea.eclipticseasons.data.general.tag;

import com.teamtea.eclipticseasons.api.constant.tag.ClimateTypeBiomeTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TagsDataProvider extends TagsProvider<Biome> {


    public TagsDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.BIOME, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {

        for (TagKey<Biome> biomeType : ClimateTypeBiomeTags.OVERWORLD_AGRO_BIOME_TYPES) {
            tag(biomeType);
        }


        for (TagKey<Biome> biomeType : ClimateTypeBiomeTags.BIOME_TYPES) {
            TagKey<Biome> oldTag = ClimateTypeBiomeTags.create(biomeType.location().getPath().replace("rain/", ""));
            tag(oldTag);
            tag(biomeType).addTag(oldTag);
        }

        for (TagKey<Biome> biomeType : ClimateTypeBiomeTags.BIOME_COLOR_TYPES) {
            tag(biomeType);
        }

        tag(ClimateTypeBiomeTags.IS_SMALL).add(Biomes.RIVER);

        List<TagKey<Biome>> isIcy = List.of(ConventionalBiomeTags.IS_ICY, ConventionalBiomeTags.IS_SNOWY, ConventionalBiomeTags.IS_MOUNTAIN_PEAK, ConventionalBiomeTags.IS_AQUATIC_ICY);
        for (TagKey<Biome> biomeTagKey : isIcy) {
            tag(ClimateTypeBiomeTags.EXTREME_COLD).addTag(biomeTagKey);
        }
        // tag(ClimateTypeBiomeTags.SEASONAL).addTags(ConventionalBiomeTags.IS_OVERWORLD, ConventionalBiomeTags.IS_VOID);
        // tag(ClimateTypeBiomeTags.SEASONAL_HOT).addTags(ConventionalBiomeTags.IS_HOT_OVERWORLD);
        // tag(ClimateTypeBiomeTags.SEASONAL_COLD).addTags(ConventionalBiomeTags.IS_MOUNTAIN_PEAK, ConventionalBiomeTags.IS_SNOWY, ConventionalBiomeTags.IS_ICY);
        //
        // tag(ClimateTypeBiomeTags.MONSOONAL).addTags(ConventionalBiomeTags.IS_SAVANNA);
        // tag(ClimateTypeBiomeTags.RAINLESS).addTags(ConventionalBiomeTags.IS_CAVE);
        // tag(ClimateTypeBiomeTags.ARID).addTags(ConventionalBiomeTags.IS_BADLANDS, ConventionalBiomeTags.IS_DESERT);
        // tag(ClimateTypeBiomeTags.DROUGHTY).addTags();
        // tag(ClimateTypeBiomeTags.SOFT).addTags(ConventionalBiomeTags.IS_BEACH, ConventionalBiomeTags.IS_OCEAN);
        // tag(ClimateTypeBiomeTags.RAINY).add(Biomes.JUNGLE);
        //
        // tag(ClimateTypeBiomeTags.IS_SMALL).addTags(ConventionalBiomeTags.IS_RIVER);
        //
        // // Biome Color
        // tag(ClimateTypeBiomeTags.SEASONAL_COLOR_CHANGE).addTags(ConventionalBiomeTags.IS_OVERWORLD);
        // tag(ClimateTypeBiomeTags.SEASONAL_HOT_COLOR_CHANGE).addTags(ConventionalBiomeTags.IS_HOT_OVERWORLD);
        // tag(ClimateTypeBiomeTags.SEASONAL_COLD_COLOR_CHANGE).addTags(ConventionalBiomeTags.IS_MOUNTAIN_PEAK, ConventionalBiomeTags.IS_SNOWY, ConventionalBiomeTags.IS_ICY);
        //
        // tag(ClimateTypeBiomeTags.MONSOONAL_COLOR_CHANGE).addTags(ClimateTypeBiomeTags.MONSOONAL);
        // tag(ClimateTypeBiomeTags.NONE_COLOR_CHANGE).addTags(ConventionalBiomeTags.IS_CAVE, ConventionalBiomeTags.IS_BADLANDS, ConventionalBiomeTags.IS_DESERT, ConventionalBiomeTags.IS_VOID);
        // tag(ClimateTypeBiomeTags.SLIGHTLY_COLOR_CHANGE).addTags(ConventionalBiomeTags.IS_BEACH, ConventionalBiomeTags.IS_OCEAN).add(Biomes.JUNGLE);


    }
}
