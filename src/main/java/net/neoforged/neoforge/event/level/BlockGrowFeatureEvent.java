package net.neoforged.neoforge.event.level;

import lombok.Data;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.neoforged.bus.api.Event;
import org.jspecify.annotations.Nullable;

@Data
public class BlockGrowFeatureEvent implements Event {
    private final LevelAccessor level;
    private final RandomSource rand;
    private final BlockPos pos;
    @Nullable
    private Holder<ConfiguredFeature<?, ?>> feature;
    private boolean canceled;
}
