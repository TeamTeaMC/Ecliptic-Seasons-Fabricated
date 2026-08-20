package com.teamtea.eclipticseasons.common.registry;

import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.common.resource.conditions.SeasonalSimulationLevelCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

public class ResourceConditionRegistry {


    public static final ResourceConditionType<SeasonalSimulationLevelCondition> SEASONAL_SIMULATION_LEVEL = ResourceConditionType.create(EclipticSeasons.rl("seasonal_simulation_level"), SeasonalSimulationLevelCondition.CODEC);

    public static void init() {
        ResourceConditions.register(SEASONAL_SIMULATION_LEVEL);
    }
}
