package com.teamtea.eclipticseasons.data.sound;

import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ESSoundDefinitionsProvider extends FabricSoundsProvider {

    private final String modId;

    public ESSoundDefinitionsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registriesFuture
    ) {
        super(output, registriesFuture);
        this.modId = EclipticSeasonsApi.MODID;
    }

    @Override
    protected void configure(
            HolderLookup.@NonNull Provider registryLookup,
            @NonNull SoundExporter exporter
    ) {
        BuiltInRegistries.SOUND_EVENT.stream()
                .filter(sound -> sound.location()
                        .getNamespace()
                        .equals(modId))
                .forEach(sound -> {
                    exporter.add(
                            sound,
                            SoundTypeBuilder.of()
                                    .sound(
                                            SoundTypeBuilder.RegistrationBuilder.ofFile(fixPath(sound.location()))
                                                    .stream(true)
                                    )
                    );
                });
    }

    private Identifier fixPath(Identifier input) {
        return Identifier.fromNamespaceAndPath(
                input.getNamespace(),
                input.getPath().replace('.', '/')
        );
    }

    @Override
    public @NonNull String getName() {
        return EclipticSeasonsApi.MODID+" Sounds";
    }
}