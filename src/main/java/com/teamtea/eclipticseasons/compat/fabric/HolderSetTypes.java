package com.teamtea.eclipticseasons.compat.fabric;

import net.minecraft.resources.Identifier;
import warp.net.neoforged.neoforge.registries.holdersets.AndHolderSet;
import warp.net.neoforged.neoforge.registries.holdersets.HolderSetType;
import warp.net.neoforged.neoforge.registries.holdersets.NotHolderSet;
import warp.net.neoforged.neoforge.registries.holdersets.OrHolderSet;

import java.util.HashMap;
import java.util.Map;

public final class HolderSetTypes {
    public static final Map<Identifier, HolderSetType> TYPES = new HashMap<>();
    public static final Map<HolderSetType, Identifier> TYPES_R = new HashMap<>();

    public static final HolderSetType AND = register(id("and"), new AndHolderSet.Type());
    public static final HolderSetType OR = register(id("or"), new OrHolderSet.Type());
    public static final HolderSetType NOT = register(id("not"), new NotHolderSet.Type());

    public static HolderSetType get(Identifier id) {
        HolderSetType type = TYPES.get(id);
        if (type == null) {
            throw new IllegalArgumentException("Unknown holder set type: " + id);
        }
        return type;
    }

    public static Identifier get(HolderSetType type) {
        Identifier id = TYPES_R.get(type);
        if (type == null) {
            throw new IllegalArgumentException("Unknown holder set type: " + id);
        }
        return id;
    }

    private static HolderSetType register(Identifier id, HolderSetType type) {
        if (TYPES.putIfAbsent(id, type) != null) {
            throw new IllegalStateException("Duplicate holder set type: " + id);
        }
        if (TYPES_R.putIfAbsent(type, id) != null) {
            throw new IllegalStateException("Duplicate holder set type: " + id);
        }
        return type;
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath("neoforge", path);
    }

    private HolderSetTypes() {
    }
}
