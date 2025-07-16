package com.rinko1231.philiaamulet.init;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class PhiliaAmuletItem extends Item implements ICurioItem {

    public PhiliaAmuletItem(Properties properties) {
        super(properties.stacksTo(1).defaultDurability(0));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public ICurio.@NotNull DropRule getDropRule(SlotContext slotContext, DamageSource source, int lootingLevel, boolean recentlyHit, ItemStack stack) {
        return ICurio.DropRule.DEFAULT;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        tooltip.add(Component.translatable("item.philiaamulet.philia_amulet.tooltip")
                .withStyle(ChatFormatting.ITALIC, ChatFormatting.LIGHT_PURPLE));
        tooltip.add(Component.translatable("item.philiaamulet.philia_amulet.tooltip2")
                .withStyle(ChatFormatting.ITALIC, ChatFormatting.LIGHT_PURPLE));

        if (Screen.hasShiftDown()) {
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.contains("Whitelist", Tag.TAG_LIST)) {
                ListTag list = tag.getList("Whitelist", Tag.TAG_STRING);

                tooltip.add(Component.translatable("item.philiaamulet.philia_amulet.whitelist_title")
                        .withStyle(ChatFormatting.GRAY));

                for (Tag id : list) {
                    String entityIdStr = id.getAsString();
                    ResourceLocation rl = ResourceLocation.tryParse(entityIdStr);
                    if (rl != null && ForgeRegistries.ENTITY_TYPES.containsKey(rl)) {
                        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(rl);
                        if (type != null) {
                            MutableComponent localizedName = Component.translatable(type.getDescriptionId());
                            tooltip.add(Component.literal("  - ").append(localizedName).withStyle(ChatFormatting.DARK_GRAY));
                        } else {
                            tooltip.add(Component.literal("  - " + entityIdStr).withStyle(ChatFormatting.DARK_GRAY));
                        }
                    } else {
                        tooltip.add(Component.literal("  - " + entityIdStr).withStyle(ChatFormatting.DARK_GRAY));
                    }
                }
            }
        } else {
            tooltip.add(Component.translatable("item.philiaamulet.philia_amulet.hold_shift")
                    .withStyle(ChatFormatting.GRAY));
        }
    }


}
