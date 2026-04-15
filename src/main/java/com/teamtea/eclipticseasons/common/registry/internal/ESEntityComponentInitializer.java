package com.teamtea.eclipticseasons.common.registry.internal;

import com.teamtea.eclipticseasons.common.advancement.SolarTermsRecord;
import com.teamtea.eclipticseasons.common.misc.HeatStrokeTicker;
import com.teamtea.eclipticseasons.common.registry.AttachmentRegistry;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class ESEntityComponentInitializer implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(AttachmentRegistry.SOLAR_TERMS_RECORD, _ -> new SolarTermsRecord(new Object2IntLinkedOpenHashMap<>()), RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerFor(Player.class, AttachmentRegistry.HEAT_STROKE_TICKER, _ -> HeatStrokeTicker.empty());
    }
}
