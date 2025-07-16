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
    public static ForgeConfigSpec.BooleanValue petsPhilia;
    public static ForgeConfigSpec.BooleanValue NoSelfHarm;
    public static ForgeConfigSpec.BooleanValue petsFriendship;
    public static ForgeConfigSpec.BooleanValue allowOwnerHurtPets;

    static {
        BUILDER.push("Config");

        entityWhitelist = BUILDER
                .comment("Living Entity Whitelist")
                .defineList("Entity Whitelist", List.of("minecraft:villager", "minecraft:iron_golem", "minecraft:allay", "minecraft:wandering_trader", "guardvillagers:guard"),
                        element -> element instanceof String);
        NoSelfHarm = BUILDER
                .comment("You can not hurt yourself")
                .comment("And your pets can not hurt themselves")
                .comment("PS. Regardless of whether NoSelfHarm is enabled, pets will not harm you.")
                .define("No Self-Harm", false);
        NoMeleeProtection = BUILDER
                .comment("Do not enable protection as for Melee Damage")
                .define("No Melee Damage Protection", false);
        petsPhilia = BUILDER
                .comment("Pets and Summons won't hurt whitelisted mobs when putting on PhiLia Amulet")
                .define("Pets are friendly to whitelisted mobs", true);
        petsFriendship = BUILDER
                .comment("Disable damage between pets or summons which have the same owner")
                .define("Pets are friendly to each other", true);
        allowOwnerHurtPets = BUILDER
                .comment("If enabled, this mod won't stop you from hurting your pets or summons.")
                .comment("But other mods might still care about them.")
                .define("I don't care", true);

        SPEC = BUILDER.build();
    }

    public static void setup() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "PhiliaAmulet.toml");
    }

}