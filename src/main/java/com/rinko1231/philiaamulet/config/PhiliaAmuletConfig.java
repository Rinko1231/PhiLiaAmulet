package com.rinko1231.philiaamulet.config;


import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class PhiliaAmuletConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static ModConfigSpec SPEC;
    public static ModConfigSpec.ConfigValue<List<? extends String>> entityWhitelist;
    public static ModConfigSpec.BooleanValue NoMeleeProtection;
    public static ModConfigSpec.BooleanValue petsPhilia;
    public static ModConfigSpec.BooleanValue NoSelfHarm;
    public static ModConfigSpec.BooleanValue petsFriendship;
    public static ModConfigSpec.BooleanValue allowOwnerHurtPets;
    static {
        BUILDER.push("Config");

        entityWhitelist = BUILDER
                .comment("Living Entity Whitelist")
                //弃用说明还能用
                .defineList("Entity Whitelist",List.of("minecraft:villager", "minecraft:iron_golem", "minecraft:allay", "minecraft:wandering_trader", "guardvillagers:guard"),
                        element -> element instanceof String);

        NoSelfHarm = BUILDER
                .comment("You can not hurt yourself")
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

}