package net.neoforged.neoforge.event.level.block;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.Event;

@Data
public abstract class CropGrowEvent implements Event {
    private final LevelAccessor level;
    private final BlockPos pos;
    private final BlockState state;


    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Pre extends CropGrowEvent {
        private Result result = Result.DEFAULT;

        public Pre(Level level, BlockPos pos, BlockState state) {
            super(level, pos, state);
        }

        public static enum Result {
            GROW,
            DEFAULT,
            DO_NOT_GROW;
        }
    }
}
