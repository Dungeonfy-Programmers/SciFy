package chickenmanfy.scify.modules

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gl.RenderPipelines
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.tooltip.Tooltip
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.resource.language.I18n.translate
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.awt.Color

var modDisableOverride = false
@Environment(EnvType.CLIENT)
class Menu : Screen(Text.translatable("scify.menu.title")) {
    private var dynamicBars: ButtonWidget? = null
    private var fishingNotif: ButtonWidget? = null
    private var watermark: ButtonWidget? = null
    private var autoWelcome: ButtonWidget? = null
    private var resourcePack: ButtonWidget? = null
    private var forceMod: ButtonWidget? = null

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)
        context.drawCenteredTextWithShadow(
            textRenderer,
            Text.translatable("scify.menu.title"),
            width / 2,
            5,
            Color(255,0,255).rgb
        )

        val texture = Identifier.of("scify", "scify.png")
        context.drawTexture(RenderPipelines.GUI_TEXTURED, texture, width / 2 - 32, 20, 0F, 0F, 64, 64, 64, 64)
    }
    public override fun init() {

        dynamicBars = ButtonWidget.builder(Text.translatable("scify.menu.health.title")) {
            toggleBars() // Call the function toggleBars() from the bars.kt module
            MinecraftClient.getInstance().setScreen(Menu())
        }
            .dimensions(width / 2 - 205, 84, 200, 20)
            .tooltip(Tooltip.of(Text.literal(translate("scify.menu.health.description") + translate(if (barsToggle) "scify.menu.enabled" else "scify.menu.disabled"))))
            .build()
        fishingNotif = ButtonWidget.builder(Text.translatable("scify.menu.fish.title")) {
            FishingNotif().toggleFishingNotif() // Call the function toggleFishingNotif() from the FishingNotif.kt module
            MinecraftClient.getInstance().setScreen(Menu())
        }
            .dimensions(width / 2 + 5, 84, 200, 20)
            .tooltip(Tooltip.of(Text.literal(translate("scify.menu.fish.description") + translate(if (fishingToggle) "scify.menu.enabled" else "scify.menu.disabled"))))
            .build()
        fishingNotif?.active = false
        watermark = ButtonWidget.builder(Text.translatable("scify.menu.watermark.title")) {
            toggleWaterMark() // Call the function toggleWaterMark() from the Watermark.kt module
            MinecraftClient.getInstance().setScreen(Menu())
        }
            .dimensions(width / 2 - 205, 164, 200, 20)
            .tooltip(Tooltip.of(Text.literal(translate("scify.menu.watermark.description") + translate(if (watermarkToggle) "scify.menu.enabled" else "scify.menu.disabled"))))
            .build()
        autoWelcome = ButtonWidget.builder(Text.translatable("scify.menu.welcome.title")) {
            toggleAutoWelcome() // Call the function toggleAutoWelcome() from the AutoWelcome.kt module
            MinecraftClient.getInstance().setScreen(Menu())
        }
            .dimensions(width / 2 - 205, 124, 200, 20)
            .tooltip(Tooltip.of(Text.literal(translate("scify.menu.welcome.description") + translate(if (autoWelcomeToggle) "scify.menu.enabled" else "scify.menu.disabled"))))
            .build()
        resourcePack = ButtonWidget.builder(Text.translatable("scify.menu.resourcepack.title")) {
            // TODO: Implement Resource pack
            MinecraftClient.getInstance().setScreen(Menu())
        }
            .dimensions(width / 2 + 5, 124, 200, 20)
            .tooltip(Tooltip.of(Text.literal(translate("scify.menu.resourcepack.description") + translate("scify.menu.disabled"))))
            .build()
        resourcePack?.active = false
        forceMod = ButtonWidget.builder(Text.translatable("scify.menu.forcemod.title")) {
            modDisableOverride = !modDisableOverride
            println(modDisableOverride)
            MinecraftClient.getInstance().setScreen(Menu())
        }
            .dimensions(width / 2 - 100, height - 40, 200, 20)
            .tooltip(Tooltip.of(Text.literal(translate("scify.menu.forcemod.description") + translate(if (modDisableOverride) "scify.menu.enabled" else "scify.menu.disabled"))))
            .build()
        addDrawableChild(dynamicBars)
        addDrawableChild(fishingNotif)
        addDrawableChild(watermark)
        addDrawableChild(autoWelcome)
        addDrawableChild(resourcePack)
        addDrawableChild(forceMod)
    }
}
