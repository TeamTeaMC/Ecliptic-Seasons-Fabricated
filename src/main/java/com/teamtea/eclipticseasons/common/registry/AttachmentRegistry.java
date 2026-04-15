package com.teamtea.eclipticseasons.common.registry;

import com.teamtea.eclipticseasons.EclipticSeasons;
import com.teamtea.eclipticseasons.common.advancement.SolarTermsRecord;
import com.teamtea.eclipticseasons.common.core.map.BiomeHolder;
import com.teamtea.eclipticseasons.common.misc.HeatStrokeTicker;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentInitializer;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class AttachmentRegistry {

    public static final ComponentKey<BiomeHolder> BIOME_HOLDER =
            ComponentRegistryV3.INSTANCE.getOrCreate(
                    EclipticSeasons.rl("biome_holder"),
                    BiomeHolder.class);

    public static void init() {
    }


    public static final AttachmentType<SolarTermsRecord> SOLAR_TERMS_RECORD =
            net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry
                    .create(EclipticSeasons.rl("solar_terms_record"), (builder ->
                            builder.initializer(SolarTermsRecord::empty)
                                    .persistent(SolarTermsRecord.CODEC.codec())
                                    // .syncWith()
                                    .copyOnDeath()));

    public static final AttachmentType<HeatStrokeTicker> HEAT_STROKE_TICKER =
            net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry
                    .create(EclipticSeasons.rl("heat_stroke_ticker"), (builder ->
                            builder.initializer(HeatStrokeTicker::empty)
                                    .copyOnDeath()));
}
