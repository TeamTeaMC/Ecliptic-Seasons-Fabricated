/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package warp.net.neoforged.neoforge.registries.holdersets;

import net.minecraft.core.HolderSet;
import net.minecraft.resources.Identifier;

/**
 * Interface for mods' custom holderset types
 */
public interface ICustomHolderSet<T> extends HolderSet<T> {
    /**
     * {@return HolderSetType registered to {@link com.teamtea.eclipticseasons.compat.fabric.HolderSetTypes#register(Identifier, HolderSetType)}}
     */
    HolderSetType type();

    // @Override
    default SerializationType serializationType() {
        return SerializationType.OBJECT;
    }

    public static SerializationType getSerializationType(HolderSet<?> holders) {
        if (holders instanceof ICustomHolderSet<?>) return ((ICustomHolderSet<?>) holders).serializationType();
        // handle vanilla holderset types
        return holders instanceof ListBacked<?> listBacked
                ? listBacked.unwrap().map(
                // serializes as tag name if this holderset is named
                tag -> SerializationType.STRING,
                list -> list.size() == 1
                        // if list has exactly one element then we have to check what kind, otherwise it's a list
                        ? list.get(0).unwrap().map(
                        // if holder has a key bound then it's serialized as that string, otherwise it's inlined as an object
                        key -> key == null ? SerializationType.OBJECT : SerializationType.STRING,
                        value -> SerializationType.OBJECT)
                        : SerializationType.LIST)
                : SerializationType.UNKNOWN; // unsupported holderset impl, could be anything
    }

    public static enum SerializationType {
        /**
         * Unhandled/unsupported holderset implementation, could serialize as potentially anything
         **/
        UNKNOWN,
        STRING,
        LIST,
        OBJECT
    }
}
