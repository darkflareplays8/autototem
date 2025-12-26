package com.example.autototem;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

public class AutoTotemMod implements ClientModInitializer {

    private static KeyBinding toggleKey;
    private static boolean enabled = false;
    private static int tickDelay = 1; // ticks between checks
    private int tickCounter = 0;

    @Override
    public void onInitializeClient() {

        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.autototem.toggle",
                InputUtil.Type.KEYSYM,
                InputUtil.GLFW_KEY_R,
                "key.categories.inventory" // string instead of enum
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            // Toggle key press
            while (toggleKey.wasPressed()) {
                enabled = !enabled;
                client.player.sendMessage(
                        Text.literal("Auto Totem: " + (enabled ? "§aEnabled" : "§cDisabled")),
                        true
                );
            }

            if (!enabled) return;

            tickCounter++;
            if (tickCounter < tickDelay) return;
            tickCounter = 0;

            if (client.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING)
                return;

            equipTotem(client);
        });
    }

    private void equipTotem(MinecraftClient client) {
        if (client.interactionManager == null) return;
        if (!(client.player.currentScreenHandler instanceof PlayerScreenHandler handler))
            return;

        PlayerInventory inv = client.player.getInventory();

        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStack(i);

            if (stack.getItem() == Items.TOTEM_OF_UNDYING) {

                int slotId = i < 9 ? i + 36 : i;
                int offhandSlot = 45; // hardcoded offhand slot

                client.interactionManager.clickSlot(
                        handler.syncId,
                        slotId,
                        0,
                        SlotActionType.PICKUP,
                        client.player
                );

                client.interactionManager.clickSlot(
                        handler.syncId,
                        offhandSlot,
                        0,
                        SlotActionType.PICKUP,
                        client.player
                );

                if (!handler.getCursorStack().isEmpty()) {
                    client.interactionManager.clickSlot(
                            handler.syncId,
                            slotId,
                            0,
                            SlotActionType.PICKUP,
                            client.player
                    );
                }

                break;
            }
        }
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setTickDelay(int delay) {
        tickDelay = Math.max(1, delay); // prevent 0
    }
}