package com.teamtea.eclipticseasons.data.general.recipe;


import com.teamtea.eclipticseasons.common.registry.BlockRegistry;
import com.teamtea.eclipticseasons.common.registry.ItemRegistry;
import com.teamtea.eclipticseasons.api.constant.simulation.SeasonalSimulationLevel;
import com.teamtea.eclipticseasons.common.resource.conditions.SeasonalSimulationLevelCondition;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

import java.util.concurrent.CompletableFuture;

public class ESRecipeProvider extends VanillaRecipeProvider {

    protected Runner runner;

    public ESRecipeProvider(final BootstrapContext<Recipe<?>> recipeOutput, final BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    public static class Runner extends FabricRecipeProvider {

        public Runner(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
            ESRecipeProvider esRecipeProvider = new ESRecipeProvider(recipes, advancements);
            esRecipeProvider.runner = this;
            return esRecipeProvider;
        }

        @Override
        public RecipeOutput withConditions(RecipeOutput output, ResourceCondition... conditions) {
            return super.withConditions(output, conditions);
        }
    }

    // public static MultiRegistryBootstrap create() {
    //     return new MultiRegistryBootstrap() {
    //         public @NonNull Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
    //             return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
    //         }
    //
    //         public void run(final MultiRegistryBootstrap.@NonNull BootstrapGetter registries) {
    //             (new ESRecipeProvider(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT), )).buildRecipes();
    //         }
    //     };
    // }

    @Override
    public void buildRecipes() {
        // HolderLookup.RegistryLookup<Item> items = registries.lookupOrThrow(Registries.ITEM);

        shaped(RecipeCategory.DECORATIONS, ItemRegistry.calendar_item)
                .define('x', Items.PAPER)
                .define('y', Items.BOOK)
                .define('z', ConventionalItemTags.FEATHERS)
                .pattern("xx")
                .pattern("yz")
                .group("calendar")
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(output);
        // shaped(RecipeCategory.DECORATIONS, BlockRegistry.pinwheel_blue.value())
        //         .define('x', Items.PAPER)
        //         .define('y', Items.DYE.blue())
        //         .define('z', ConventionalItemTags.RODS_WOODEN)
        //         .pattern("xy")
        //         .pattern("z ")
        //         .group("pinwheel")
        //         .unlockedBy("has_paper", has(Items.PAPER))
        //         .save(output);
        // shaped(RecipeCategory.DECORATIONS, BlockRegistry.pinwheel_lime.value())
        //         .define('x', Items.PAPER)
        //         .define('y', Items.DYE.lime())
        //         .define('z', ConventionalItemTags.RODS_WOODEN)
        //         .pattern("xy")
        //         .pattern("z ")
        //         .group("pinwheel")
        //         .unlockedBy("has_paper", has(Items.PAPER))
        //         .save(output);
        // shaped(RecipeCategory.DECORATIONS, BlockRegistry.pinwheel_orange.value())
        //         .define('x', Items.PAPER)
        //         .define('y', Items.DYE.orange())
        //         .define('z', ConventionalItemTags.RODS_WOODEN)
        //         .pattern("xy")
        //         .pattern("z ")
        //         .group("pinwheel")
        //         .unlockedBy("has_paper", has(Items.PAPER))
        //         .save(output);
        //
        // shaped(RecipeCategory.DECORATIONS, BlockRegistry.wind_chimes.value())
        //         .define('x', ConventionalItemTags.STRINGS)
        //         .define('z', Items.BAMBOO)
        //         .pattern(" x ")
        //         .pattern("zzz")
        //         .group("wind_chimes")
        //         .unlockedBy("has_string", has(ConventionalItemTags.STRINGS))
        //         .save(output);
        // shaped(RecipeCategory.DECORATIONS, BlockRegistry.bamboo_wind_chimes.value())
        //         .define('x', ConventionalItemTags.STRINGS)
        //         .define('z', Items.BAMBOO_BLOCK)
        //         .define('y', Items.PAPER)
        //         .pattern(" x ")
        //         .pattern(" z ")
        //         .pattern(" y ")
        //         .group("wind_chimes")
        //         .unlockedBy("has_string", has(ConventionalItemTags.STRINGS))
        //         .save(output);
        // shaped(RecipeCategory.DECORATIONS, BlockRegistry.paper_wind_chimes.value())
        //         .define('x', ConventionalItemTags.STRINGS)
        //         .define('y', Items.PAPER)
        //         .define('i', Items.DYE.blue())
        //         .define('j', Items.DYE.yellow())
        //         .pattern("xyi")
        //         .pattern(" yj")
        //         .pattern(" y ")
        //         .group("wind_chimes")
        //         .unlockedBy("has_paper", has(ConventionalItemTags.STRINGS))
        //         .save(output);
        //
        // shaped(RecipeCategory.TOOLS, ItemRegistry.broom.get())
        //         .define('h', Items.HAY_BLOCK)
        //         .define('r', ConventionalItemTags.RODS_WOODEN)
        //         .pattern(" h")
        //         .pattern("r ")
        //         .group("broom")
        //         .unlockedBy("has_hay_block", has(Items.HAY_BLOCK))
        //         .save(output);
        //
        // shaped(RecipeCategory.TOOLS, ItemRegistry.hyetometer.get())
        //         .define('x', ConventionalItemTags.DUSTS_REDSTONE)
        //         .define('y', Items.GLASS_BOTTLE)
        //         .define('z', ConventionalItemTags.INGOTS_COPPER)
        //         .pattern("xz")
        //         .pattern(" y")
        //         .group("hyetometer")
        //         .unlockedBy("has_glass_bottle", has(Items.GLASS_BOTTLE))
        //         .unlockedBy("self", has(ItemRegistry.hyetometer.get()))
        //         .save(output);
        //
        //
        // shaped(RecipeCategory.TOOLS, ItemRegistry.thermometer.get())
        //         .define('x', ConventionalItemTags.DUSTS_REDSTONE)
        //         .define('y', DataComponentIngredient.of(false, () -> DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER), Items.POTION))
        //         .pattern(" x")
        //         .pattern("y ")
        //         .group("thermometer")
        //         .unlockedBy("has_glass", has(Items.GLASS_BOTTLE))
        //         .unlockedBy("self", has(ItemRegistry.thermometer.get()))
        //         .save(output);

        shaped(RecipeCategory.TOOLS, ItemRegistry.hygrometer)
                .define('x', ConventionalItemTags.AMETHYST_GEMS)
                .define('y', ConventionalItemTags.COPPER_INGOTS)
                .define('z', ConventionalItemTags.REDSTONE_DUSTS)
                .define('g', ConventionalItemTags.GLASS_PANES)
                .define('c', Items.CALCITE)
                .pattern("yxy")
                .pattern("gcg")
                .pattern("czc")
                .group("hygrometer")
                .unlockedBy("has_amethyst", has(ConventionalItemTags.AMETHYST_GEMS))
                .save(output);

        shaped(RecipeCategory.TOOLS, ItemRegistry.growth_detector)
                .define('x', ConventionalItemTags.GLASS_PANES)
                .define('y', ConventionalItemTags.GLASS_BLOCKS)
                .define('z', ConventionalItemTags.WOODEN_RODS)
                .pattern("  x")
                .pattern(" y ")
                .pattern("z  ")
                .group("growth_detector")
                .unlockedBy("has_glass", has(ConventionalItemTags.GLASS_BLOCKS))
                .save(runner.withConditions(
                        output,
                        SeasonalSimulationLevelCondition.builder().level(SeasonalSimulationLevel.AGRICULTURE).build()
                ));

        // shaped(RecipeCategory.TOOLS, ItemRegistry.seasonal_prayer_scroll_item.get())
        //         .define('x', ConventionalItemTags.SEEDS)
        //         .define('y', Items.PAPER)
        //         .pattern("xx")
        //         .pattern("xy")
        //         .group("seasonal_prayer_scroll")
        //         .unlockedBy("has_seeds", has(ConventionalItemTags.SEEDS))
        //         .save(output);
        //
        // shaped(RecipeCategory.TOOLS, ItemRegistry.greenhouse_core_container_item.get())
        //         .define('x', ConventionalItemTags.GLASS_BLOCKS_TINTED)
        //         .define('z', ConventionalItemTags.INGOTS_COPPER)
        //         .pattern("zxz")
        //         .pattern("x x")
        //         .pattern("zxz")
        //         .group("greenhouse_core_frame")
        //         .unlockedBy("has_amethyst", has(ConventionalItemTags.GEMS_AMETHYST))
        //         .save(output);
        //
        // shaped(RecipeCategory.TOOLS, ItemRegistry.block_in_wooden_grate_block_item.get(), 4)
        //         .define('r', ItemTags.LOGS)
        //         .pattern(" r ")
        //         .pattern("r r")
        //         .pattern(" r ")
        //         .group("block_in_wooden_grate_block")
        //         .unlockedBy("has_logs", has(ItemTags.LOGS))
        //         .save(output);
        //
        // ShapelessRecipeBuilder.shapeless(items, RecipeCategory.TOOLS, ItemRegistry.spring_greenhouse_core_item.get())
        //         .requires(ItemRegistry.spring_greenhouse_essence_item.get())
        //         .requires(ItemRegistry.greenhouse_core_container_item.get())
        //         .group("spring_greenhouse_core")
        //         .unlockedBy("has_amethyst", has(ConventionalItemTags.GEMS_AMETHYST))
        //         .save(output);
        //
        // ShapelessRecipeBuilder.shapeless(items, RecipeCategory.TOOLS, ItemRegistry.summer_greenhouse_core_item.get())
        //         .requires(ItemRegistry.summer_greenhouse_essence_item.get())
        //         .requires(ItemRegistry.greenhouse_core_container_item.get())
        //         .group("summer_greenhouse_core")
        //         .unlockedBy("has_amethyst", has(ConventionalItemTags.GEMS_AMETHYST))
        //         .save(output);
        //
        // ShapelessRecipeBuilder.shapeless(items, RecipeCategory.TOOLS, ItemRegistry.autumn_greenhouse_core_item.get())
        //         .requires(ItemRegistry.autumn_greenhouse_essence_item.get())
        //         .requires(ItemRegistry.greenhouse_core_container_item.get())
        //         .group("autumn_greenhouse_core")
        //         .unlockedBy("has_amethyst", has(ConventionalItemTags.GEMS_AMETHYST))
        //         .save(output);
        //
        // ShapelessRecipeBuilder.shapeless(items, RecipeCategory.TOOLS, ItemRegistry.winter_greenhouse_core_item.get())
        //         .requires(ItemRegistry.winter_greenhouse_essence_item.get())
        //         .requires(ItemRegistry.greenhouse_core_container_item.get())
        //         .group("winter_greenhouse_core")
        //         .unlockedBy("has_amethyst", has(ConventionalItemTags.GEMS_AMETHYST))
        //         .save(output);
        //
        //
        // if (ModList.get().isLoaded("patchouli")) {
        //     ItemStack defaultInstance = BuiltInRegistries.ITEM.get(Identifier.parse("patchouli:guide_book")).get().value().getDefaultInstance();
        //     defaultInstance.set((DataComponentType) BuiltInRegistries.DATA_COMPONENT_TYPE.get(Identifier.parse("patchouli:book")).get(), (Object) Identifier.parse("eclipticseasons:seasons_chronicle"));
        //     RecipeOutput conditionalRecipeOutput = output.withConditions(new ModLoadedCondition("patchouli"));
        //     ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ItemStackTemplate.fromNonEmptyStack(defaultInstance))
        //             .requires(Items.BOOK)
        //             .requires(ConventionalItemTags.SEEDS)
        //             .group("seasons_chronicle")
        //             .unlockedBy("has_seeds", has(ConventionalItemTags.SEEDS))
        //             .save(conditionalRecipeOutput, "seasons_chronicle");
        // }
        //
        // shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.humidity_tank.get())
        //         .pattern("SBS")
        //         .pattern("BCB")
        //         .pattern("SIS")
        //         .define('S', ItemTags.WOODEN_SLABS)
        //         .define('B', ItemTags.PLANKS)
        //         .define('C', Items.WATER_BUCKET)
        //         .define('I', Items.IRON_INGOT)
        //         .unlockedBy("has_water_bucket", has(Items.WATER_BUCKET))
        //         .save(output);
        //
        // shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.dehumidifier.get())
        //         .pattern("PPP")
        //         .pattern("PHN")
        //         .pattern("SSS")
        //         .define('P', ItemTags.PLANKS)
        //         .define('H', Blocks.HAY_BLOCK)
        //         .define('N', ItemTags.WOODEN_SLABS)
        //         .define('S', Items.IRON_NUGGET)
        //         .unlockedBy("has_hay_block", has(Blocks.HAY_BLOCK))
        //         .save(output);

        shaped(RecipeCategory.REDSTONE, BlockRegistry.season_sensor)
                .pattern("GCG")
                .pattern("SRS")
                .pattern("WWW")
                .define('G', ConventionalItemTags.GLASS_BLOCKS)
                .define('C', Items.COPPER_INGOT)
                .define('S', Items.REDSTONE)
                .define('R', Items.CLOCK)
                .define('W', ItemTags.PLANKS)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(output);

        // shaped(RecipeCategory.TOOLS, ItemRegistry.salt_wand.get())
        //         .pattern(" Q ")
        //         .pattern(" S ")
        //         .pattern(" T ")
        //         .define('Q', ConventionalItemTags.GEMS_QUARTZ)
        //         .define('S', ConventionalItemTags.INGOTS_GOLD)
        //         .define('T', Items.STICK)
        //         .unlockedBy("has_quartz", has(Items.QUARTZ))
        //         .save(output);
    }

}
