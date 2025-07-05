package com.rinko1231.philiaamulet.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.rinko1231.philiaamulet.PhiliaAmulet.MOD_ID;


public class itemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> PHILIA_AMULET = ITEMS.register("philia_amulet",
            () -> new PhiliaAmuletItem( new Item.Properties()));
}
