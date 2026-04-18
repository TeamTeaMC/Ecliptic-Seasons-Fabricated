package warp.net.neoforged.neoforge.event.entity.player;

import com.teamtea.eclipticseasons.api.event.IESEvent;
import lombok.Builder;
import lombok.Data;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import warp.net.neoforged.bus.api.Event;
import org.jspecify.annotations.Nullable;

@Data
public class BonemealEvent implements Event, IESEvent {
    private final Player player;
    private final Level level;
    private final BlockPos pos;
    private final BlockState state;
    private final ItemStack stack;
    private boolean isSuccess = false;
    private boolean canceled = false;

    public void setSuccessful(boolean aBoolean) {
        this.isSuccess = aBoolean;
    }
}
