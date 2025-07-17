package com.rinko1231.philiaamulet.init;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.rinko1231.philiaamulet.PhiliaAmulet.MOD_ID;


public class itemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredHolder<Item, Item> PHILIA_AMULET = ITEMS.register("philia_amulet",
            () -> new PhiliaAmuletItem( new Item.Properties()));
}
