package com.rinko1231.philiaamulet.mixin.iss;

import com.rinko1231.philiaamulet.config.PhiliaAmuletConfig;
import com.rinko1231.philiaamulet.init.itemRegistry;

import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

@Mixin(value = LivingEntity.class, priority = 2000)
public abstract class LivingEntityMixinISS {

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void philiaAmulet_preHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity victim = (LivingEntity) (Object) this;
        Entity trueAttacker = source.getEntity();
        if (victim.level().isClientSide) return;


        if (PhiliaAmuletConfig.petsFriendship.get() && trueAttacker instanceof LivingEntity attacker) {
            @Nullable UUID attackerOwner = Philia$getEntityOwnerUUID(attacker);
            @Nullable UUID victimOwner = Philia$getEntityOwnerUUID(victim);

            //主人相同或自己宠物打自己才阻止伤害
            if (attackerOwner != null && victimOwner != null && attackerOwner.equals(victimOwner)) {
                if (attacker == victim && !PhiliaAmuletConfig.NoSelfHarm.get()) {
                    // 允许自残
                    return;
                }
                cir.setReturnValue(false);
                return;
            }
        }

        // 攻击者的主人是佩戴了护符的玩家且受害者是白名单生物
        if (PhiliaAmuletConfig.petsPhilia.get() && trueAttacker instanceof LivingEntity attacker) {
            UUID ownerUUID = Philia$getEntityOwnerUUID(attacker);
            if (ownerUUID != null && victim instanceof LivingEntity) {
                ServerLevel level = (ServerLevel) victim.level();
                ServerPlayer ownerPlayer = (ServerPlayer) level.getPlayerByUUID(ownerUUID);
                if (ownerPlayer != null && Philia$isEquipAmulet(ownerPlayer) && Philia$isEntityWhitelisted(victim)) {
                    cir.setReturnValue(false);
                    return;
                }
            }
        }



        // 玩家
        if (trueAttacker instanceof ServerPlayer player) {
            if (Philia$entityShouldNotBeHurt(player, victim) && !PhiliaAmuletConfig.NoMeleeProtection.get()) {
                cir.setReturnValue(false);
                return;
            }
        }

        // 投射物
        if (source.getDirectEntity() instanceof Projectile projectile) {
            if (projectile.getOwner() instanceof ServerPlayer player) {
                if (Philia$entityShouldNotBeHurt(player, victim)) {
                    cir.setReturnValue(false);
                    return;
                }
            }
        }

        // 投掷药水
        if (source.getDirectEntity() instanceof ThrownPotion potion) {
            if (potion.getOwner() instanceof ServerPlayer player) {
                if (Philia$entityShouldNotBeHurt(player, victim)) {
                    cir.setReturnValue(false);
                    return;
                }
            }
        }

        // 效果云
        if (source.getDirectEntity() instanceof AreaEffectCloud cloud) {
            if (cloud.getOwner() instanceof ServerPlayer player) {
                if (Philia$entityShouldNotBeHurt(player, victim)) {
                    cir.setReturnValue(false);
                }
            }
        }


    }

    @Unique
    private boolean Philia$entityShouldNotBeHurt(ServerPlayer player, LivingEntity entity) {
        return Philia$isEntityWhitelisted(entity) && Philia$isEquipAmulet(player);
    }

    @Unique
    private boolean Philia$isEntityWhitelisted(LivingEntity entity) {
        String entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
        return PhiliaAmuletConfig.entityWhitelist.get().contains(entityId);
    }

    @Unique
    private boolean Philia$isEquipAmulet(LivingEntity livingEntity) {
        Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(livingEntity).stream().findAny();
        return curiosInventory
                .map(iCuriosItemHandler -> iCuriosItemHandler.isEquipped(itemRegistry.PHILIA_AMULET.get()))
                .orElse(false);
    }

    @Unique
    @Nullable
    private UUID Philia$getEntityOwnerUUID(Entity entity) {

        // 玩家自己就是自己的owner
        if (entity instanceof ServerPlayer player) {
            return player.getUUID();
        }
        // 驯服动物
        if (entity instanceof TamableAnimal tameable && tameable.isTame()) {
            return tameable.getOwnerUUID();
        }

        // 通用可拥有者接口
        if (entity instanceof OwnableEntity ownable) {
            return ownable.getOwnerUUID();
        }

        // 召唤生物
        if (entity instanceof IMagicSummon summon) {
            LivingEntity summoner = summon.getSummoner();
            if (summoner != null) {
                return summoner.getUUID();
            }
        }

        // 投射物、药水云，往上溯源
        if (entity instanceof Projectile projectile) {
            Entity owner = projectile.getOwner();
            if (owner instanceof LivingEntity livingOwner) {
                return Philia$getEntityOwnerUUID(livingOwner);
            }
        }

        if (entity instanceof AreaEffectCloud cloud) {
            Entity owner = cloud.getOwner();
            if (owner instanceof LivingEntity livingOwner) {
                return Philia$getEntityOwnerUUID(livingOwner);
            }
        }

        if (entity instanceof ThrownPotion potion) {
            Entity owner = potion.getOwner();
            if (owner instanceof LivingEntity livingOwner) {
                return Philia$getEntityOwnerUUID(livingOwner);
            }
        }
        return null;
    }

}