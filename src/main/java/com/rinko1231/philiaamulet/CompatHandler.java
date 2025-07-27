package com.rinko1231.philiaamulet;

import net.minecraftforge.fml.loading.FMLLoader;

public class CompatHandler {
    private static CompatHandler instance;
    public final boolean ironMagicLoaded;
    public static CompatHandler getInstance() {
        if (instance == null) instance = new CompatHandler();
        return instance;
    }
    public CompatHandler() {
        ironMagicLoaded = getViaCfgAndClass();
    }
    private boolean getViaCfgAndClass() {
        return FMLLoader.getLoadingModList().getModFileById("irons_spellbooks") !=null;
    }

}