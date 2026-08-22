package com.teamtea.eclipticseasons.client.mixin.compat.voxy;

import com.teamtea.eclipticseasons.common.mixin.condition.ConditionalMixin;
import me.cortex.voxy.client.core.VoxyRenderSystem;
import me.cortex.voxy.client.core.model.ModelBakerySubsystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(VoxyRenderSystem.class)
@ConditionalMixin(value = "voxy",version = "0.2.18-beta")
public interface MixinAccessorVoxyRenderSystem {

    @Accessor("modelService")
    ModelBakerySubsystem getModelBakerySubsystem();
}
