package com.teamtea.eclipticseasons.client.mixin.compat.voxy;

import com.teamtea.eclipticseasons.common.mixin.condition.ConditionalMixin;
import me.cortex.voxy.client.core.gl.GlBuffer;
import me.cortex.voxy.client.core.model.ModelStore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ModelStore.class)
@ConditionalMixin(value = "voxy", version = "0.2.16-beta")
public interface ModelStoreAccessor {

    @Accessor("modelColourBuffer")
    GlBuffer eclipticseasons$getModelColourBuffer();
}