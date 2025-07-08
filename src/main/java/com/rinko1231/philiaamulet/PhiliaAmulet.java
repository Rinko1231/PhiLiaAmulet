package com.rinko1231.philiaamulet;


import com.rinko1231.philiaamulet.init.TabInit;
import com.rinko1231.philiaamulet.init.itemRegistry;
import com.rinko1231.philiaamulet.config.*;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.MinecraftForge;
/*
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
*/
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PhiliaAmulet.MOD_ID)
public class PhiliaAmulet {
    public static final String MOD_ID = "philiaamulet";

    public PhiliaAmulet() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        itemRegistry.ITEMS.register(modEventBus);
        TabInit.TABS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);

        PhiliaAmuletConfig.setup();
    }

    //测试
/*
    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {

        if (event.getSide().isClient()) return;

        Entity target = event.getTarget();
        Level level = event.getLevel();

        if (!(target instanceof LivingEntity owner)) return;

        Arrow arrow = new Arrow(level, 0, 80, 0);
        arrow.setOwner(owner);

        level.addFreshEntity(arrow);

    }*/


}
