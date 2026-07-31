package com.teamtea.eclipticseasons.mixin.data;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.data.tags.TagsProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(TagsProvider.class)
public abstract class TagProviderMixin {

    @WrapOperation(method = {"lambda$run$5"},
            at = {@At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z")})
    private static <E> boolean eclipticseasons$buildPatch$fixError(List instance, Operation<Boolean> original) {
        // if ("true".equals(System.getProperty("eclipticseasons.runs.runData"))
        // )
        {
            return true;
        }
        // return original;
    }
}