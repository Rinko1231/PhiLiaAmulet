package com.rinko1231.philiaamulet.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;


public class PhiliaAmuletConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec SPEC;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> entityWhitelist;
    public static ForgeConfigSpec.BooleanValue NoMeleeProtection;
    public static ForgeConfigSpec.BooleanValue petsFriendship;

    static {
        BUILDER.push("Config");

        entityWhitelist = BUILDER
                .comment("Living Entity Whitelist")
                .defineList("Entity Whitelist", List.of("minecraft:villager", "minecraft:iron_golem", "minecraft:allay", "minecraft:wandering_trader", "guardvillagers:guard"),
                        element -> element instanceof String);
        NoMeleeProtection = BUILDER
                .comment("Do not enable protection as for Melee Damage")
                .define("No Melee Damage Protection", false);
        petsFriendship = BUILDER
                .comment("Disable damage between pets or summons which have the same owner")
                .define("Pets are friendly to each other", true);

        SPEC = BUILDER.build();
    }

    public static void setup() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "PhiliaAmulet.toml");
    }

}