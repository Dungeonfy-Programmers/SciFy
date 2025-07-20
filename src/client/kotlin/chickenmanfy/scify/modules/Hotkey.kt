package chickenmanfy.scify.modules

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW
import chickenmanfy.scify.Global


class Hotkey {
    fun hotkeys() {

        // Register Keys
        val warp = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "Open Warp Menu (/warp)",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "SciFy"
            )
        )
        val enderchest = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "Open Ender Chest (/ec)",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                "SciFy"
            )
        )
        val guide = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "Open Guide Book (/guide)",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_SEMICOLON,
                "SciFy"
            )
        )
        // Open Menu Hotkey
        val menu = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "Menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "SciFy"
            )
        )

        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick {
            while (warp.wasPressed()) {
                if (Global().ipCheck()[0]) {
                    MinecraftClient.getInstance().networkHandler!!.sendChatCommand("warp")
                }

            }
            while (enderchest.wasPressed()) {
                if (Global().ipCheck()[0]) {
                    MinecraftClient.getInstance().networkHandler!!.sendChatCommand("ec")
                    println(ClientReceiveMessageEvents.CHAT)
                }
            }
            while (guide.wasPressed()) {
                if (Global().ipCheck()[1]) {
                    MinecraftClient.getInstance().networkHandler!!.sendChatCommand("guide")
                }
            }
            while (menu.wasPressed()) {
                MinecraftClient.getInstance().setScreen(Menu())
            }
        })

    }
}
