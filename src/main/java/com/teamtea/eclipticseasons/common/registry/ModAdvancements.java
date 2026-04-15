package com.teamtea.eclipticseasons.common.registry;

import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import com.teamtea.eclipticseasons.common.advancement.ParentNeedCriterion;
import com.teamtea.eclipticseasons.common.advancement.SolarTermsCriterion;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

public class ModAdvancements {

    // 1. 定义具体的触发器实例
    public static final SolarTermsCriterion HEAT_STROKE = register("heat_stroke", new SolarTermsCriterion());
    public static final SolarTermsCriterion SOLAR_TERMS = register("solar_terms", new SolarTermsCriterion());
    public static final SolarTermsCriterion GREENHOUSE = register("greenhouse", new SolarTermsCriterion());
    public static final ParentNeedCriterion PARENT_NEED = register("parent", new ParentNeedCriterion());


    private static <T extends CriterionTrigger<?>> T register(String name, T trigger) {
        var id = EclipticSeasons.rl( name);
        ResourceKey<CriterionTrigger<?>> key = ResourceKey.create(Registries.TRIGGER_TYPE, id);

        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, key, trigger);
    }

    public static void init() {
    }
}