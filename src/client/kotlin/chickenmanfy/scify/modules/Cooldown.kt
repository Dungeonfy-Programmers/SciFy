package chickenmanfy.scify.modules

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.RenderTickCounter
import net.minecraft.component.DataComponentTypes
import net.minecraft.text.Text
import org.yaml.snakeyaml.Yaml
import java.awt.Color
import java.io.FileReader
import java.io.FileWriter

// Load cooldown value
val cooldownYaml = Yaml()
val cooldownFileReader = FileReader(FabricLoader.getInstance().configDir.resolve("scify/config.yaml").toFile())
val cooldownConfig: HashMap<String, Boolean> = cooldownYaml.load(cooldownFileReader)
var cooldownToggle: Boolean? = cooldownConfig.get("cooldownTimerEnabled")

// Saving was a ridiculous idea
fun saveCooldownData() {
    val yaml = Yaml()
    cooldownToggle?.let { config["cooldownTimerEnabled"] = it }
    val fileWriter = FileWriter(FabricLoader.getInstance().configDir.resolve("scify/config.yaml").toFile())
    yaml.dump(config, fileWriter)
}

fun toggleCooldown() {
    cooldownToggle = !cooldownToggle!!
    saveCooldownData()
}
class Cooldown: HudElement {


    fun cooldown() {
      if (Global().ipCheck()[0]) {
            if (cooldownToggle == true) {
                MinecraftClient.getInstance().player?.sendMessage(Text.literal(MinecraftClient.getInstance().player?.itemCooldownManager?.getCooldownProgress(MinecraftClient.getInstance().player?.mainHandStack, 1F).toString()), false)
            }
        }
    }

    override fun render(context: DrawContext?, tickCounter: RenderTickCounter?) {
        if (!Global().ipCheck()[0] || !cooldownToggle!! || !MinecraftClient.getInstance().player?.itemCooldownManager?.isCoolingDown(MinecraftClient.getInstance().player?.mainHandStack)!!) { return }
        val client = MinecraftClient.getInstance()

        val cooldown = String.format("%.1f",MinecraftClient.getInstance().player?.itemCooldownManager?.getCooldownProgress(MinecraftClient.getInstance().player?.mainHandStack, 1F)?.times(MinecraftClient.getInstance().player?.mainHandStack?.components?.get(DataComponentTypes.USE_COOLDOWN)?.seconds?.toFloat()!!))

        context?.drawTextWithShadow(client.textRenderer, cooldown, 5, client.window.scaledHeight - client.textRenderer.fontHeight - 5, Color(255,0,255).rgb)
    }
}
