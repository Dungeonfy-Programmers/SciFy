package chickenmanfy.scify.modules

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text

var autoWelcomeToggle: Boolean = false

fun toggleAutoWelcome() {
    autoWelcomeToggle = !autoWelcomeToggle
}

class AutoWelcome: ClientReceiveMessageEvents.Game {

    override fun onReceiveGameMessage(message: Text?, overlay: Boolean) {
        if (overlay || !Global().ipCheck()[0] || !autoWelcomeToggle) { return }
        //MinecraftClient.getInstance().networkHandler?.sendChatMessage("hello")
        println("WOAAAH")
        println(message?.string)
        println(message?.style?.color?.name)
        if (message?.style?.color?.name == "#52FFAE") {
            if ("has arrived, welcome :D" in message.string || "magically appeared, welcome :)" in message.string || "joined the game, welcome :O" in message.string) {
                MinecraftClient.getInstance().networkHandler?.sendChatMessage("Welcome " + message.string?.split(" ")[0] + "!")
            }
        }
        if (message?.style?.color?.name == "yellow") {
            if ("joined the game" in message.string) {
                MinecraftClient.getInstance().networkHandler?.sendChatMessage("wb " + message.string?.split(" ")[0])
            }
        }
    }
}
