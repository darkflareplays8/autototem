package com.example.autototem;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "autototem")
public class ModConfig implements ConfigData {
    
    @ConfigEntry.Gui.Tooltip
    public boolean enabled = true;
    
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
    public int tickDelay = 0;
    
    @ConfigEntry.Gui.Tooltip
    public int toggleKeyCode = 82; // 82 is the key code for R
}