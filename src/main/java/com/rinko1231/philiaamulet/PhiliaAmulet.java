package com.rinko1231.philiaamulet;


import com.rinko1231.philiaamulet.init.TabInit;
import com.rinko1231.philiaamulet.init.itemRegistry;
import com.rinko1231.philiaamulet.config.*;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

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


    @SubscribeEvent
    public void onEntityRightClick(PlayerInteractEvent.EntityInteractSpecific event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        ItemStack stack = event.getItemStack();

        // 必须是主手并且是友爱护符
        if (!stack.is(itemRegistry.PHILIA_AMULET.get()) || event.getHand() != InteractionHand.MAIN_HAND)
            return;

        if (!(event.getTarget() instanceof LivingEntity target))
            return;

        ResourceLocation entityId = ForgeRegistries.ENTITY_TYPES.getKey(target.getType());
        if (entityId == null) return;

        CompoundTag tag = stack.getOrCreateTag();
        ListTag list = tag.contains("Whitelist", Tag.TAG_LIST) ? tag.getList("Whitelist", Tag.TAG_STRING) : new ListTag();

        boolean isSneaking = player.isShiftKeyDown();
        boolean modified = false;

        MutableComponent entityName = Component.translatable(target.getType().getDescriptionId()); // 本地化名称

        if (isSneaking) {
            // 移除
            for (int i = 0; i < list.size(); i++) {
                if (list.getString(i).equals(entityId.toString())) {
                    list.remove(i);
                    modified = true;

                    player.displayClientMessage(
                            Component.translatable("item.philiaamulet.message.removed")
                                    .append(Component.literal(" ").append(entityName))
                                    .withStyle(ChatFormatting.RED),
                            true
                    );
                    break;
                }
            }
        } else {
            // 添加
            if (!list.contains(StringTag.valueOf(entityId.toString()))) {
                list.add(StringTag.valueOf(entityId.toString()));
                modified = true;

                player.displayClientMessage(
                        Component.translatable("item.philiaamulet.message.added")
                                .append(Component.literal(" ").append(entityName))
                                .withStyle(ChatFormatting.GREEN),
                        true
                );
            }
        }

        if (modified) {
            tag.put("Whitelist", list);
            stack.setTag(tag);
            event.setCanceled(true); //阻止默认交互
        }
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

    }
*/

}
