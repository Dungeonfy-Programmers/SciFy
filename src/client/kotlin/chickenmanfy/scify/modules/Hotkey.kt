package chickenmanfy.scify.modules

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.util.Identifier
import org.lwjgl.glfw.GLFW


class Hotkey {
    fun hotkeys() {
        val category = KeyBinding.Category.create(Identifier.of("scify", "keybind"))
        // Register Keys
        val warp = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "Open Warp Menu (/warp)",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                category
            )
        )
        val enderchest = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "Open Ender Chest (/ec)",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                category
            )
        )
        val guide = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "Open Guide Book (/guide)",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_SEMICOLON,
                category
            )
        )
        // Open Menu Hotkey
        val menu = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "Menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                category
            )
        )

        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick {
            while (warp.wasPressed()) {
                MinecraftClient.getInstance().networkHandler!!.sendChatCommand("warp")
            }
            while (enderchest.wasPressed()) {
                MinecraftClient.getInstance().networkHandler!!.sendChatCommand("ec")
            }
            while (guide.wasPressed()) {
                MinecraftClient.getInstance().networkHandler!!.sendChatCommand("guide")
            }
            while (menu.wasPressed()) {
                MinecraftClient.getInstance().setScreen(Menu())
            }
        })

    }
}
