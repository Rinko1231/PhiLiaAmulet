package com.rinko1231.philiaamulet.mixin;

import com.rinko1231.philiaamulet.CompatHandler;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class PhiliaMixinPlugin implements IMixinConfigPlugin {

       public PhiliaMixinPlugin() {
        // 必须有这个 public 无参构造器
           // ↑真的吗
        }

        @Override
        public void onLoad(String mixinPackage) {
            CompatHandler.getInstance();
        }

        @Override
        public String getRefMapperConfig() {
            return null;
        }

        @Override
        public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
            if (mixinClassName.contains("iss")) return CompatHandler.getInstance().ironMagicLoaded;
            if (mixinClassName.contains("nomagic")) return !CompatHandler.getInstance().ironMagicLoaded;
            return true;
        }

        @Override
        public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

        }

        @Override
        public List<String> getMixins() {
            return null;
        }

        @Override
        public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

        }

        @Override
        public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

        }



    }