package com.rinko1231.philiaamulet;

import net.minecraftforge.fml.ModList;

public class CompatHandler {
    private static CompatHandler instance;
    public final boolean ironMagicLoaded;

    public static CompatHandler getInstance() {
        if (instance == null) instance = new CompatHandler();
        return instance;
    }

    public CompatHandler() {
        this.ironMagicLoaded = ModList.get().isLoaded("ironsspellbooks");
    }
}