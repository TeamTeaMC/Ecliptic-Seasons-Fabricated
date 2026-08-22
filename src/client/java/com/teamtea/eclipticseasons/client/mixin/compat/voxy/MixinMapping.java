package com.teamtea.eclipticseasons.client.mixin.compat.voxy;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.teamtea.eclipticseasons.common.mixin.condition.ConditionalMixin;
import com.teamtea.eclipticseasons.compat.voxy.VoxyTool;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.cortex.voxy.common.world.other.Mapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({Mapper.class})
@ConditionalMixin(value = "voxy",version = "0.2.18-beta")
public abstract class MixinMapping {


    @WrapOperation(
            remap = false,
            method = "getBlockStateOpacity(I)I",
            at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectArrayList;get(I)Ljava/lang/Object;")
    )
    private <K> K eclipticseasons$getBlockStateOpacity_fixId(ObjectArrayList<K> instance, int index, Operation<K> original) {
        return original.call(instance, VoxyTool.fixId((Mapper) (Object) this, index));
    }

    @WrapOperation(
            remap = false,
            method = "getBlockStateFromBlockId",
            at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectArrayList;get(I)Ljava/lang/Object;")
    )
    private <K> K eclipticseasons$getBlockStateFromBlockId_fixId(ObjectArrayList<K> instance, int index, Operation<K> original) {
        return original.call(instance, VoxyTool.fixId((Mapper) (Object) this, index));
    }
}
