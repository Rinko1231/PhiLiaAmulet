package com.rinko1231.philiaamulet;



//测试用
/*
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
*/

import net.neoforged.bus.api.IEventBus;
import com.rinko1231.philiaamulet.init.TabInit;
import com.rinko1231.philiaamulet.init.itemRegistry;
import com.rinko1231.philiaamulet.config.*;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;


@Mod(PhiliaAmulet.MOD_ID)
public class PhiliaAmulet {
    public static final String MOD_ID = "philiaamulet";

    public PhiliaAmulet(IEventBus modEventBus, ModContainer modContainer) {
        itemRegistry.ITEMS.register(modEventBus);
        TabInit.TABS.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, PhiliaAmuletConfig.SPEC,"PhiLiaAmuletConfig.toml");
        //NeoForge.EVENT_BUS.register(this);
    }

    //测试
/*
    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {

        if (event.getSide().isClient()) return;

        Entity target = event.getTarget();
        Level level = event.getLevel();
        if (!(target instanceof LivingEntity owner)) return;
        ItemStack stack = new ItemStack(Items.ARROW);
        Arrow arrow = new Arrow(level, owner, stack, null);
        arrow.setOwner(owner);

        level.addFreshEntity(arrow);
        arrow.setPos(0,80,0);

    }*/


}
