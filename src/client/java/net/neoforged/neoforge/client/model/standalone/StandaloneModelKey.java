/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.client.model.standalone;

import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.resources.Identifier;


public final class StandaloneModelKey<T> {
    private final ModelDebugName name;

    public StandaloneModelKey(ModelDebugName name) {
        this.name = name;
    }

    public String getName() {
        return this.name.debugName();
    }

    @Override
    public String toString() {
        return "StandaloneModelKey[name=" + this.name.debugName() + ']';
    }

    public Identifier to(){
        return Identifier.parse(getName());
    }

    public ExtraModelKey<T> toFabric(){
        return ExtraModelKey.create(name::debugName);
    }
}
