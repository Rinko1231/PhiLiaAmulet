package com.rinko1231.philiaamulet.mixin.nomagic;

import com.rinko1231.philiaamulet.config.PhiliaAmuletConfig;
import com.rinko1231.philiaamulet.init.itemRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
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

import static com.rinko1231.philiaamulet.init.PhiliaAmuletItem.getEntityData;

@Mixin(value = LivingEntity.class, priority = 2000)
public abstract class LivingEntityMixin {

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void philiaAmulet_preHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity victim = (LivingEntity) (Object) this;
        Entity trueAttacker = source.getEntity();
        if (victim.level().isClientSide) return;

        // 先处理攻击者是玩家或其控制的实体佩戴护符并试图攻击白名单生物（最高优先）
        if (trueAttacker instanceof ServerPlayer player) {
            if (Philia$entityShouldNotBeHurt(player, victim) && !PhiliaAmuletConfig.NoMeleeProtection.get()) {
                cir.setReturnValue(false);
                return;
            }
        }

        if (source.getDirectEntity() instanceof Projectile projectile) {
            if (projectile.getOwner() instanceof ServerPlayer player) {
                if (Philia$entityShouldNotBeHurt(player, victim)) {
                    cir.setReturnValue(false);
                    return;
                }
            }
        }

        if (source.getDirectEntity() instanceof ThrownPotion potion) {
            if (potion.getOwner() instanceof ServerPlayer player) {
                if (Philia$entityShouldNotBeHurt(player, victim)) {
                    cir.setReturnValue(false);
                    return;
                }
            }
        }

        if (source.getDirectEntity() instanceof AreaEffectCloud cloud) {
            if (cloud.getOwner() instanceof ServerPlayer player) {
                if (Philia$entityShouldNotBeHurt(player, victim)) {
                    cir.setReturnValue(false);
                    return;
                }
            }
        }

        if (PhiliaAmuletConfig.petsFriendship.get() && trueAttacker instanceof LivingEntity attacker) {
            @Nullable UUID attackerOwner = Philia$getEntityOwnerUUID(attacker);
            @Nullable UUID victimOwner = Philia$getEntityOwnerUUID(victim);

            if (attackerOwner != null && victimOwner != null && attackerOwner.equals(victimOwner)) {
                if (attacker == victim && !PhiliaAmuletConfig.NoSelfHarm.get()) {
                    return;
                }


                if (attacker instanceof ServerPlayer ownerPlayer) {
                    if (Philia$isEquipAmulet(ownerPlayer) && Philia$isEntityWhitelisted(ownerPlayer, victim)) {
                        // 如果在护符白名单中阻止
                        cir.setReturnValue(false);
                        return;
                    }

                    // 不在白名单，允许打自己宠物（如果配置开启）
                    if (PhiliaAmuletConfig.allowOwnerHurtPets.get()) {
                        return;
                    }
                }

                // 没有护符或者不允许打宠物
                cir.setReturnValue(false);
                return;
            }
        }

        // 最后处理“宠物佩戴者攻击白名单”
        if (PhiliaAmuletConfig.petsPhilia.get() && trueAttacker instanceof LivingEntity attacker) {
            UUID ownerUUID = Philia$getEntityOwnerUUID(attacker);
            if (ownerUUID != null && victim instanceof LivingEntity) {
                ServerLevel level = (ServerLevel) victim.level();
                ServerPlayer ownerPlayer = (ServerPlayer) level.getPlayerByUUID(ownerUUID);
                if (ownerPlayer != null && Philia$isEquipAmulet(ownerPlayer) && Philia$isEntityWhitelisted(ownerPlayer, victim)) {
                    cir.setReturnValue(false);
                    return;
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
    private boolean Philia$isEntityWhitelisted(LivingEntity attacker, LivingEntity target) {
        String entityId = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType()).toString();


        // 全局白名单
        if (PhiliaAmuletConfig.entityWhitelist.get().contains(entityId)) {
            return true;
        }

        // 检查佩戴护符的私有白名单
        Optional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(attacker).stream().findAny();
        if (curios.isPresent()) {
            ICuriosItemHandler handler = curios.get();
            for (var stack : handler.findCurios(itemRegistry.PHILIA_AMULET.get())) {
                CompoundTag tag = getEntityData(stack.stack());
                if (tag.contains("Whitelist", Tag.TAG_LIST)) {
                    ListTag list = tag.getList("Whitelist", Tag.TAG_STRING);
                    for (Tag id : list) {
                        if (id.getAsString().equals(entityId)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
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

        // 通用可拥有者接口（部分模组）
        if (entity instanceof OwnableEntity ownable) {
            return ownable.getOwnerUUID();
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