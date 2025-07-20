package chickenmanfy.scify.modules

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import chickenmanfy.scify.Global

var watermarkToggle = true
class Watermark {
    fun toggleWaterMark() {
        watermarkToggle = !watermarkToggle
    }

    fun watermark() {
            HudRenderCallback.EVENT.register(HudRenderCallback { drawContext: DrawContext?, _: Float ->
                  if (Global().ipCheck()[0])
                    if (watermarkToggle) {
                        val minecraftClient = MinecraftClient.getInstance()
                        val scifyVersion = "0.3.0"
                        drawContext?.drawTextWithShadow(minecraftClient.textRenderer,"§nSciFy v${scifyVersion}",minecraftClient.window.scaledWidth - minecraftClient.textRenderer.getWidth("SciFy v${scifyVersion}") - 5, minecraftClient.window.scaledHeight - minecraftClient.textRenderer.fontHeight - 5, 0xff00ff)
                    }
                }
            })
    }
}
