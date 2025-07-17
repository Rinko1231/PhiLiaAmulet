package com.rinko1231.philiaamulet.init;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;

public class PhiliaAmuletItem extends Item implements ICurioItem {

    public PhiliaAmuletItem(Properties properties) {
        super(properties.stacksTo(1));
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
    public static boolean containsEntity(@Nonnull ItemStack stack) {
        return stack.has((Supplier)ModDataComponents.ENTITY_WHITELIST);
    }

    public static CompoundTag getEntityData(ItemStack stack) {
        return containsEntity(stack) ? (CompoundTag)stack.get((Supplier)ModDataComponents.ENTITY_WHITELIST) : new CompoundTag();
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag tooltipFlag) {

        super.appendHoverText(stack,context,tooltip,tooltipFlag);
        tooltip.add(Component.translatable("item.philiaamulet.philia_amulet.tooltip")
                .withStyle(ChatFormatting.ITALIC, ChatFormatting.LIGHT_PURPLE));
        tooltip.add(Component.translatable("item.philiaamulet.philia_amulet.tooltip2")
                .withStyle(ChatFormatting.ITALIC, ChatFormatting.LIGHT_PURPLE));

        if (Screen.hasShiftDown()) {
            CompoundTag tag = getEntityData(stack);
            if (tag != null && tag.contains("Whitelist", Tag.TAG_LIST)) {
                ListTag list = tag.getList("Whitelist", Tag.TAG_STRING);

                tooltip.add(Component.translatable("item.philiaamulet.philia_amulet.whitelist_title")
                        .withStyle(ChatFormatting.GRAY));

                for (Tag id : list) {
                    String entityIdStr = id.getAsString();
                    ResourceLocation rl = ResourceLocation.tryParse(entityIdStr);
                    if (rl != null && BuiltInRegistries.ENTITY_TYPE.containsKey(rl)) {
                        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(rl);
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
