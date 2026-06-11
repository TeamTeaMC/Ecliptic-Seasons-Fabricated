package warp.net.neoforged.neoforge.event;


import com.teamtea.eclipticseasons.api.event.IESEvent;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import org.jetbrains.annotations.ApiStatus;
import warp.net.neoforged.bus.api.Event;

@Getter
@SuperBuilder
public abstract class TagsUpdatedEvent implements Event, IESEvent {

    private final RegistryAccess registries;
    private final boolean integratedServer;

    @Getter
    @SuperBuilder
    public static final class ServerDataLoad extends TagsUpdatedEvent {
    }

    @Getter
    @SuperBuilder
    public static final class ClientPacketReceived extends TagsUpdatedEvent {
    }
}
