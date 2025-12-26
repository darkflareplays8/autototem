/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.shedaniel.autoconfig.ConfigData
 *  me.shedaniel.autoconfig.annotation.Config
 *  me.shedaniel.autoconfig.annotation.ConfigEntry$BoundedDiscrete
 *  me.shedaniel.autoconfig.annotation.ConfigEntry$Gui$Tooltip
 */
package com.example.autototem;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="autototem")
public class ModConfig
implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean enabled = true;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min=0L, max=20L)
    public int tickDelay = 0;
    @ConfigEntry.Gui.Tooltip
    public int toggleKeyCode = 82;
}

