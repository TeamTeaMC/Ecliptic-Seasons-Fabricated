package net.neoforged.neoforge.event;


import lombok.Builder;
import lombok.Data;
import net.minecraft.core.HolderLookup;
import net.neoforged.bus.api.Event;

@Data
@Builder
public class TagsUpdatedEvent implements Event {

    private final HolderLookup.Provider lookupProvider;
    private final UpdateCause updateCause;
    private final boolean integratedServer;

    public enum UpdateCause {
        SERVER_DATA_LOAD,
        CLIENT_PACKET_RECEIVED
    }
}
