package com.teamtea.eclipticseasons.data.general.advancement;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Advancements extends AdvancementProvider {
    public Advancements() {
        super(List.of(
                // ESAdvancementGenerator::new
        ));
    }

    // @Override
    // public CompletableFuture<?> run(CachedOutput cache) {
    //     return null;
    // }
    //
    // @Override
    // public String getName() {
    //     return "";
    // }
}
