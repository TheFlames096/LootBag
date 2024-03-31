package com.theflames096.lootbag.config;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class Config {
    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec.IntValue DROP_CHANCE;
    public static ForgeConfigSpec.BooleanValue ONLY_DROP_BY_NATURAL_ENTITY;
    public static ForgeConfigSpec.BooleanValue BIOME_MODE;
    public static ForgeConfigSpec.BooleanValue SLOT_MODE;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLACK_LIST;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }
}
