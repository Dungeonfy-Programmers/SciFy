package chickenmanfy.scify.modules

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.RenderTickCounter
import org.yaml.snakeyaml.Yaml
import java.awt.Color
import java.io.FileReader
import java.io.FileWriter

// Load watermark value
val watermarkYaml = Yaml()
val watermarkFileReader = FileReader(FabricLoader.getInstance().configDir.resolve("scify-config.yaml").toFile())
val watermarkConfig: HashMap<String, Boolean> = watermarkYaml.load(watermarkFileReader)
var watermarkToggle: Boolean? = watermarkConfig.get("watermarkEnabled")

// Saving was a ridiculous idea
fun saveWatermarkData() {
    val yaml = Yaml()
    watermarkToggle?.let { config["watermarkEnabled"] = it }
    val fileWriter = FileWriter(FabricLoader.getInstance().configDir.resolve("scify-config.yaml").toFile())
    yaml.dump(config, fileWriter)
}

fun toggleWatermark() {
    watermarkToggle = !watermarkToggle!!
    saveWatermarkData()
}

class Watermark: HudElement {
    // old system
    /*HudRenderCallback.EVENT.register(HudRenderCallback { drawContext: DrawContext?, _: Float ->
          if (Global().ipCheck()[0])
            if (watermarkToggle) {
                val minecraftClient = MinecraftClient.getInstance()
                val scifyVersion = "0.3.0"
                drawContext?.drawTextWithShadow(minecraftClient.textRenderer,"§nSciFy v${scifyVersion}",minecraftClient.window.scaledWidth - minecraftClient.textRenderer.getWidth("SciFy v${scifyVersion}") - 5, minecraftClient.window.scaledHeight - minecraftClient.textRenderer.fontHeight - 5, 0xff00ff)
            }
        }
    })*/
    override fun render(context: DrawContext?, tickCounter: RenderTickCounter?) {
        if (!Global().ipCheck()[0] || !watermarkToggle!!) { return } // make sure this is supposed to render

        val client = MinecraftClient.getInstance()
        val scifyVersion = "0.4.0"
        context?.drawTextWithShadow(client.textRenderer, "§nSciFy v${scifyVersion}",client.window.scaledWidth - client.textRenderer.getWidth("SciFy v${scifyVersion}") - 5, client.window.scaledHeight - client.textRenderer.fontHeight - 5, Color(255,0,255).rgb)
    }
}
