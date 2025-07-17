package com.rinko1231.philiaamulet.init;

import java.util.function.UnaryOperator;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    //将entity_whitelist改名为normal_nbt就是模拟旧版本通用nbt了
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, "philiaamulet");

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CompoundTag>> ENTITY_WHITELIST;

    static {
        ENTITY_WHITELIST = register(builder -> builder.persistent(CompoundTag.CODEC));
  }

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register("entity_whitelist", () -> ((DataComponentType.Builder)builderOperator.apply(DataComponentType.builder())).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
    /* 工具方法，回来吧我的getTag!!
    public static boolean containsTag(@Nonnull ItemStack stack) {
        return stack.has((Supplier)ModDataComponents.NORMAL_NBT);
    }
    public static CompoundTag getTag(ItemStack stack) {
        return containsTag(stack) ? (CompoundTag)stack.get((Supplier)ModDataComponents.NORMAL_NBT) : new CompoundTag();
    }
    public static void setTag(@Nonnull ItemStack stack, CompoundTag tag) {
        stack.set((Supplier)ModDataComponents.NORMAL_NBT, tag);
    }*/
}
