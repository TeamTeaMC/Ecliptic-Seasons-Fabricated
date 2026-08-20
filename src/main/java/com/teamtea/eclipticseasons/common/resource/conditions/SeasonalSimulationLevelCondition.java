package com.teamtea.eclipticseasons.common.resource.conditions;

import com.mojang.serialization.MapCodec;
import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import com.teamtea.eclipticseasons.api.constant.simulation.SeasonalSimulationLevel;
import com.teamtea.eclipticseasons.common.registry.ResourceConditionRegistry;
import lombok.Builder;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.resources.RegistryOps;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Builder
public record SeasonalSimulationLevelCondition(
        SeasonalSimulationLevel level
) implements ResourceCondition {

    public static final MapCodec<SeasonalSimulationLevelCondition> CODEC =
            SeasonalSimulationLevel.CODEC
                    .fieldOf("level")
                    .xmap(SeasonalSimulationLevelCondition::new,
                            SeasonalSimulationLevelCondition::level);

    @Override
    public @NonNull ResourceConditionType<?> getType() {
        return ResourceConditionRegistry.SEASONAL_SIMULATION_LEVEL;
    }

    @Override
    public boolean test(RegistryOps.@Nullable RegistryInfoLookup registryInfo) {
        return EclipticSeasonsApi.getInstance().getSeasonalSimulationLevel().enable(level);
    }

    // public static class Type implements ResourceConditionType<SeasonalSimulationLevelCondition> {
    //
    //
    //     @Override
    //     public @NonNull Identifier id() {
    //         return EclipticSeasons.rl("seasonal_simulation_level");
    //     }
    //
    //     @Override
    //     public @NonNull MapCodec<SeasonalSimulationLevelCondition> codec() {
    //         return CODEC;
    //     }
    // }
}