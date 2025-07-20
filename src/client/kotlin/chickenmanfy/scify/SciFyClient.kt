package chickenmanfy.scify

import chickenmanfy.scify.modules.* // woo fancy import all modules
import chickenmanfy.scify.Global
import net.fabricmc.api.ClientModInitializer


object SciFyClient : ClientModInitializer {
    override fun onInitializeClient() {

        // Run the Modules
        Hotkey().hotkeys()
        AutoWelcome().autoWelcome()
        FishingNotif().fishingNotif()
        Bars().bars()
        Watermark().watermark()
        LivelyMode().livelyMode()
    }
}
