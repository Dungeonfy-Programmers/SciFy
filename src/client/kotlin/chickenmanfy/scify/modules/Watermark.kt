package chickenmanfy.scify.modules

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.RenderTickCounter
import java.awt.Color

var watermarkToggle = true

fun toggleWaterMark() {
    watermarkToggle = !watermarkToggle
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
        if (!Global().ipCheck()[0] || !watermarkToggle) { return } // make sure this is supposed to render

        val client = MinecraftClient.getInstance()
        val scifyVersion = "0.3.0"
        context?.drawTextWithShadow(client.textRenderer, "§nSciFy v${scifyVersion}",client.window.scaledWidth - client.textRenderer.getWidth("SciFy v${scifyVersion}") - 5, client.window.scaledHeight - client.textRenderer.fontHeight - 5, Color(255,0,255).rgb)
    }
}
