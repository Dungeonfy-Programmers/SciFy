package chickenmanfy.scify.modules

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gl.RenderPipelines
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.RenderTickCounter
import net.minecraft.component.DataComponentTypes
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.yaml.snakeyaml.Yaml
import java.awt.Color
import java.io.FileReader
import java.io.FileWriter

// Load cooldown value
val cooldownYaml = Yaml()
val cooldownFileReader = FileReader(FabricLoader.getInstance().configDir.resolve("scify-config.yaml").toFile())
val cooldownConfig: HashMap<String, Boolean> = cooldownYaml.load(cooldownFileReader)
var cooldownToggle: Boolean? = cooldownConfig.get("cooldownTimerEnabled")

// Saving was a ridiculous idea
fun saveCooldownData() {
    val yaml = Yaml()
    cooldownToggle?.let { config["cooldownTimerEnabled"] = it }
    val fileWriter = FileWriter(FabricLoader.getInstance().configDir.resolve("scify-config.yaml").toFile())
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
        if (!Global().ipCheck()[0] || !cooldownToggle!!) { return }
        val client = MinecraftClient.getInstance()

        // Legacy Code

        /*val cooldown = String.format("%.1f",MinecraftClient.getInstance().player?.itemCooldownManager?.getCooldownProgress(MinecraftClient.getInstance().player?.mainHandStack, 1F)?.times(
            MinecraftClient.getInstance().player?.mainHandStack?.components?.get(DataComponentTypes.USE_COOLDOWN)?.seconds!!))

        context?.drawTextWithShadow(client.textRenderer, cooldown + "s", 282, client.window.scaledHeight - client.textRenderer.fontHeight - 21, Color(255,0,255).rgb)*/

        for (i in 0..8) {
            var y: Int
            if (i % 2 == 0) {
                y = -21
            } else {
                y = -37
            }
            client.player?.inventory?.getStack(i)?.isEmpty?.let {
                if (!it && client.player?.itemCooldownManager?.getCooldownProgress(client.player!!.inventory.getStack(i), 1F) != 0f) {
                    val cooldown = String.format(
                        "%.1f",
                        client.player?.itemCooldownManager?.getCooldownProgress(client.player!!.inventory.getStack(i), 1F)
                            ?.times(client.player!!.inventory.getStack(i).components.get(DataComponentTypes.USE_COOLDOWN)!!.seconds)
                            /*?.times(
                                client.player!!.inventory.getStack(i).components.get(DataComponentTypes.USE_COOLDOWN)!!.seconds
                            )*/
                    )
                    val identifier = client.player!!.inventory.getStack(i).registryEntry.idAsString.split(":")
                    context?.drawTexture(RenderPipelines.GUI_TEXTURED, Identifier.of(identifier[0], "textures/item/" + identifier[1] + ".png"), 0, i*16 + 28,
                        0F, 0F, 16, 16, 16, 16)
                    context?.drawText(
                        client.textRenderer,
                        client.player!!.inventory.getStack(i).name.string + ": " + cooldown + "s",
                        18,
                        i*16 + 32,
                        Color(100, 100, 100).rgb,
                        false
                    )
                }
            }
        }
    }
}
