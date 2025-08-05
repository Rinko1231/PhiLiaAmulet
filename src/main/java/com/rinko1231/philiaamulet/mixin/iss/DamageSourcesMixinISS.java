package com.rinko1231.philiaamulet.mixin.iss;

import com.rinko1231.philiaamulet.config.PhiliaAmuletConfig;
import com.rinko1231.philiaamulet.init.itemRegistry;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.UUID;

@Mixin(value = DamageSources.class, remap = false, priority = 2000)
public abstract class DamageSourcesMixinISS {

    @Inject(
            method = "isFriendlyFireBetween(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void philia$isFriendlyFireBetween(Entity attacker, Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (attacker == null || target == null) return;
        if (!(target instanceof LivingEntity livingTarget)) return;

        // 同乘载具直接判友
        if (attacker.isPassengerOfSameVehicle(target)) {
            cir.setReturnValue(true);
            return;
        }

        // 玩家直控攻击白名单
        if (attacker instanceof ServerPlayer player) {
            if (Philia$entityShouldNotBeHurt(player, livingTarget) && !PhiliaAmuletConfig.NoMeleeProtection.get()) {
                cir.setReturnValue(true); // 友方
                return;
            }
        }

        // 玩家控制的宠物 / 召唤物同主
        @Nullable var attackerOwner = Philia$getEntityOwnerUUID(attacker);
        @Nullable var targetOwner   = Philia$getEntityOwnerUUID(target);

        if (PhiliaAmuletConfig.petsFriendship.get() && attackerOwner != null && targetOwner != null
                && attackerOwner.equals(targetOwner)) {

            // 自残允许
            if (attacker == target && !PhiliaAmuletConfig.NoSelfHarm.get()) {
                return;
            }

            // 主人打宠物允许
            if (attacker instanceof ServerPlayer ownerPlayer) {
                if (PhiliaAmuletConfig.allowOwnerHurtPets.get() && !Philia$isEntityWhitelisted(ownerPlayer, livingTarget)) {
                    return;
                }
            }

            cir.setReturnValue(true);
            return;
        }

        // 宠物佩戴者扩展攻击白名单
        if (PhiliaAmuletConfig.petsPhilia.get() && attackerOwner != null) {
            ServerPlayer ownerPlayer = Philia$getOnlineOwner(target.level(), attackerOwner);
            if (ownerPlayer != null && Philia$isEquipAmulet(ownerPlayer) && Philia$isEntityWhitelisted(ownerPlayer, livingTarget)
                    && !PhiliaAmuletConfig.NoMeleeProtection.get()) {
                cir.setReturnValue(true);
                return;
            }
        }
    }


    @Unique
    private static boolean Philia$isEquipAmulet(LivingEntity livingEntity) {
        return CuriosApi.getCuriosInventory(livingEntity)
                .map(handler -> handler.isEquipped(itemRegistry.PHILIA_AMULET.get()))
                .orElse(false);
    }
    @Unique
    private static boolean Philia$entityShouldNotBeHurt(ServerPlayer player, LivingEntity entity) {
        return Philia$isEntityWhitelisted(player, entity) && Philia$isEquipAmulet(player);
    }

    @Unique
    private static boolean Philia$isEntityWhitelisted(LivingEntity attacker, LivingEntity target) {
        String targetId = ForgeRegistries.ENTITY_TYPES.getKey(target.getType()).toString();

        if (PhiliaAmuletConfig.entityWhitelist.get().contains(targetId)) {
            return true;
        }

        return CuriosApi.getCuriosInventory(attacker).map(handler -> {
            for (var found : handler.findCurios(itemRegistry.PHILIA_AMULET.get())) {
                CompoundTag tag = found.stack().getOrCreateTag();
                if (tag.contains("Whitelist", Tag.TAG_LIST)) {
                    ListTag list = tag.getList("Whitelist", Tag.TAG_STRING);
                    for (Tag id : list) {
                        if (id.getAsString().equals(targetId)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }).orElse(false);
    }



    @Unique
    @Nullable
    private static UUID Philia$getEntityOwnerUUID(Entity entity) {

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
        if (entity instanceof MagicSummon summon) {
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


    @Unique
    @Nullable
    private static ServerPlayer Philia$getOnlineOwner(Level level, java.util.UUID uuid) {
        if (level instanceof ServerLevel serverLevel) {
            return (ServerPlayer) serverLevel.getPlayerByUUID(uuid);
        }
        return null;
    }
}