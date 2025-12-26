/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.shedaniel.autoconfig.AutoConfig
 *  me.shedaniel.autoconfig.serializer.GsonConfigSerializer
 *  net.fabricmc.api.ClientModInitializer
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1713
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_2561
 *  net.minecraft.class_304
 *  net.minecraft.class_310
 */
package com.example.autototem;

import com.example.autototem.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_304;
import net.minecraft.class_310;

public class AutoTotemMod
implements ClientModInitializer {
    private static class_304 toggleKey;
    private static ModConfig config;
    private int tickCounter = 0;

    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        config = (ModConfig)AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        toggleKey = KeyBindingHelper.registerKeyBinding((class_304)new class_304("key.autototem.toggle", AutoTotemMod.config.toggleKeyCode, "category.autototem"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.method_1436()) {
                AutoTotemMod.config.enabled = !AutoTotemMod.config.enabled;
                AutoConfig.getConfigHolder(ModConfig.class).save();
                if (client.field_1724 == null) continue;
                client.field_1724.method_7353((class_2561)class_2561.method_43470((String)("Auto Totem: " + (AutoTotemMod.config.enabled ? "\u00a7aEnabled" : "\u00a7cDisabled"))), true);
            }
            if (!AutoTotemMod.config.enabled) {
                return;
            }
            if (client.field_1724 == null || client.field_1687 == null) {
                return;
            }
            ++this.tickCounter;
            if (this.tickCounter < AutoTotemMod.config.tickDelay) {
                return;
            }
            this.tickCounter = 0;
            class_1799 offhand = client.field_1724.method_6079();
            if (offhand.method_7960() || offhand.method_7909() != class_1802.field_8288) {
                this.equipTotem(client);
            }
        });
    }

    private void equipTotem(class_310 client) {
        class_1661 inv = client.field_1724.method_31548();
        for (int i = 0; i < 36; ++i) {
            class_1799 stack = inv.method_5438(i);
            if (stack.method_7909() != class_1802.field_8288) continue;
            if (client.field_1761 == null) break;
            int slot = i < 9 ? i + 36 : i;
            client.field_1761.method_2906(client.field_1724.field_7512.field_7763, slot, 0, class_1713.field_7790, (class_1657)client.field_1724);
            client.field_1761.method_2906(client.field_1724.field_7512.field_7763, 45, 0, class_1713.field_7790, (class_1657)client.field_1724);
            client.field_1761.method_2906(client.field_1724.field_7512.field_7763, slot, 0, class_1713.field_7790, (class_1657)client.field_1724);
            break;
        }
    }

    public static ModConfig getConfig() {
        return config;
    }
}

