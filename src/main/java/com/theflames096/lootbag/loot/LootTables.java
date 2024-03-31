package com.theflames096.lootbag.loot;

import com.google.common.collect.Sets;
import com.theflames096.lootbag.LootBag;

import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.Set;

public class LootTables {
    private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();

    private static final Set<ResourceLocation> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);

    public static ResourceLocation register(String p_186373_0_) {
        return register(new ResourceLocation(LootBag.MOD_ID, p_186373_0_));
    }

    private static ResourceLocation register(ResourceLocation p_186375_0_) {
        if (LOCATIONS.add(p_186375_0_)) {
            return p_186375_0_;
        } else {
            throw new IllegalArgumentException(p_186375_0_ + " is already a registered built-in loot table");
        }
    }

    public static Set<ResourceLocation> all() {
        return IMMUTABLE_LOCATIONS;
    }
}
