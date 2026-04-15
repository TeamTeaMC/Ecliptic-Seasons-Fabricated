/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package com.teamtea.eclipticseasons.client.model.block.unbake.standalone;

import net.fabricmc.fabric.api.client.model.loading.v1.UnbakedExtraModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvableModel;
import org.jspecify.annotations.NonNull;

import java.util.function.BiFunction;

public class CustomUnbakeModel<T> implements UnbakedExtraModel<T> {

    private final ResolvableModel unbaked;
    private final BiFunction<ResolvableModel, ModelBaker, T> bake;

    public CustomUnbakeModel(ResolvableModel unbaked, BiFunction<ResolvableModel, ModelBaker, T> bake) {
        this.unbaked = unbaked;
        this.bake = bake;
    }

    @Override
    public T bake(@NonNull ModelBaker baker) {
        return bake.apply(unbaked, baker);
    }

    @Override
    public void resolveDependencies(@NonNull Resolver resolver) {
        // resolver.markDependency(modelId);
        unbaked.resolveDependencies(resolver);
    }
}
