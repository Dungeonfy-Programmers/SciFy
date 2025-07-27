package chickenmanfy.scify.modules

import net.minecraft.client.MinecraftClient
import chickenmanfy.scify.Global

var fishingToggle: Boolean = false
class FishingNotif {
    // Fishing Notification Toggle
    fun toggleFishingNotif() {
        fishingToggle = !fishingToggle
    }


    // Code in Menu.kt:
    // toggleFishingNotif()

    fun fishingNotif() {
      if (Global().ipCheck()[0]) {
            if (fishingToggle) {
                return
            }
        }
    }
}
