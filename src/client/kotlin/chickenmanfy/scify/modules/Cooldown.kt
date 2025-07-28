package chickenmanfy.scify.modules

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.RenderTickCounter
import net.minecraft.text.Text
import java.awt.Color

var cooldownToggle: Boolean = false
class Cooldown: HudElement {
    // Fishing Notification Toggle
    fun toggleCooldown() {
        cooldownToggle = !cooldownToggle
    }


    // Code in Menu.kt:
    // toggleFishingNotif()

    fun cooldown() {
      if (Global().ipCheck()[0]) {
            if (cooldownToggle) {
                MinecraftClient.getInstance().player?.sendMessage(Text.literal(MinecraftClient.getInstance().player?.itemCooldownManager?.getCooldownProgress(MinecraftClient.getInstance().player?.mainHandStack, 1F).toString()), false)
            }
        }
    }

    override fun render(context: DrawContext?, tickCounter: RenderTickCounter?) {
        if (!Global().ipCheck()[0] || !cooldownToggle || !MinecraftClient.getInstance().player?.itemCooldownManager?.isCoolingDown(MinecraftClient.getInstance().player?.mainHandStack)!!) { return }
        val client = MinecraftClient.getInstance()

        val cooldown = MinecraftClient.getInstance().player?.itemCooldownManager?.getCooldownProgress(MinecraftClient.getInstance().player?.mainHandStack, 1F).toString()

        context?.drawTextWithShadow(client.textRenderer, cooldown, 5, client.window.scaledHeight - client.textRenderer.fontHeight - 5, Color(255,0,255).rgb)
    }
}
