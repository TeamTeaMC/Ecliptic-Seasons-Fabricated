package com.teamtea.eclipticseasons.client.mixin.compat.voxy;

import com.teamtea.eclipticseasons.common.mixin.condition.ConditionalMixin;
import me.cortex.voxy.client.core.gl.GlBuffer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "me.cortex.voxy.client.core.model.ModelFactory$ModelBlockStatePair")
@ConditionalMixin(value = "voxy", version = "0.2.19-beta")
public interface ModelFactoryModelBlockStatePairAccessor {

    @Accessor("model")
    int eclipticseasons$getModel();

    @Accessor("state")
    BlockState eclipticseasons$getState();
}