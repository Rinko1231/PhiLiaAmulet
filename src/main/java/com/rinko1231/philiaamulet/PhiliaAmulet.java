package com.rinko1231.philiaamulet;


import com.rinko1231.philiaamulet.init.TabInit;
import com.rinko1231.philiaamulet.init.itemRegistry;
import com.rinko1231.philiaamulet.config.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.MinecraftForge;
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

    /*
    @SubscribeEvent
    public void hurtModify(LivingHurtEvent event)
    {
        LivingEntity victim = event.getEntity();
        DamageSource source = event.getSource();

        // 直接来自玩家
        if (source.getEntity() instanceof ServerPlayer player) {
            if (entityShouldNotBeHurt(player, victim) && !PhiliaAmuletConfig.NoMeleeProtection.get()) {
                event.setCanceled(true);
            }
            return;
        }

        // 投射物（如箭、雪球、火球等）
        if (source.getDirectEntity() instanceof Projectile projectile) {
            if (projectile.getOwner() instanceof ServerPlayer player) {
                if (entityShouldNotBeHurt(player, victim)) {
                    event.setCanceled(true);
                }
                return;
            }
        }

        // 投掷药水
        if (source.getDirectEntity() instanceof ThrownPotion potion) {
            if (potion.getOwner() instanceof ServerPlayer player) {
                if (entityShouldNotBeHurt(player, victim)) {
                    event.setCanceled(true);
                }
                return;
            }
        }

        // 效果云（药水云）
        if (source.getDirectEntity() instanceof AreaEffectCloud cloud) {
            if (cloud.getOwner() instanceof ServerPlayer player) {
                if (entityShouldNotBeHurt(player, victim)) {
                    event.setCanceled(true);
                }
            }
        }


    }

    private boolean entityShouldNotBeHurt(ServerPlayer player, LivingEntity entity)
    {
        return isEntityWhitelisted(entity) && isEquipAmulet(player);

    }
    public boolean isEntityWhitelisted(LivingEntity entity) {
        String entityId = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString();
        return PhiliaAmuletConfig.entityWhitelist.get().contains(entityId);
    }
    public static boolean isEquipAmulet(LivingEntity livingEntity) {
        Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(livingEntity).resolve();
        return curiosInventory.map(iCuriosItemHandler -> iCuriosItemHandler.isEquipped(itemRegistry.PHILIA_AMULET.get())).orElse(false);
    }
*/
