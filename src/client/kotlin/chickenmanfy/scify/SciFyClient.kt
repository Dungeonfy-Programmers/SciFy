package chickenmanfy.scify

import chickenmanfy.scify.modules.* // woo fancy import all modules
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
import net.minecraft.util.Identifier


object SciFyClient : ClientModInitializer {
    override fun onInitializeClient() {

        // Run the Modules
        Hotkey().hotkeys()
        HudElementRegistry.addLast(Identifier.of("scify", "cooldown"), Cooldown())
        ClientReceiveMessageEvents.GAME.register(Identifier.of("scify", "autowelcome"), AutoWelcome())
        HudElementRegistry.addLast(Identifier.of("scify", "bars"), Bars())
        HudElementRegistry.addLast(Identifier.of("scify", "watermark"), Watermark())
    }
}
