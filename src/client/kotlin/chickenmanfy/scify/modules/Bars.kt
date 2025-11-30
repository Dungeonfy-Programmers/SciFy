package chickenmanfy.scify.modules

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gl.RenderPipelines
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.*
import net.minecraft.util.Identifier
import org.yaml.snakeyaml.Yaml
import java.io.FileReader
import java.io.FileWriter
import java.util.regex.Pattern
import kotlin.math.roundToInt

// Load bars value
val barsYaml = Yaml()
val barsFileReader = FileReader(FabricLoader.getInstance().configDir.resolve("scify-config.yaml").toFile())
val barsConfig: HashMap<String, Boolean> = barsYaml.load(barsFileReader)
var barsToggle: Boolean? = barsConfig.get("barsEnabled")

// Saving was a ridiculous idea
fun saveBarsData() {
    val yaml = Yaml()
    barsToggle?.let { config["barsEnabled"] = it }
    val fileWriter = FileWriter(FabricLoader.getInstance().configDir.resolve("scify-config.yaml").toFile())
    yaml.dump(config, fileWriter)
}

fun toggleBars() {
    barsToggle = !barsToggle!!
    saveBarsData()
}

class Bars: HudElement {
    private var mana = 0
    init {
        ClientReceiveMessageEvents.GAME.register { actionbar, _ ->

            val pattern: Pattern = Pattern.compile("\\[(\\d+)/(\\d+)]")
            val matcher = pattern.matcher(actionbar.string)

            var lastFirstNumber = 0
            var lastSecondNumber = 0

            while (matcher.find()) {
                lastFirstNumber = matcher.group(1).toInt()
                lastSecondNumber = matcher.group(2).toInt()
            }

            // Final mana value
            if (lastSecondNumber != 0) {
                val divided = lastSecondNumber.toFloat() / 20
                val final = (lastFirstNumber.toFloat() / divided).roundToInt()
                mana = if (final <= 20) final else 20
            }

        }
    }

    // old bar implementation
    // minecraft is the stupidest thing to ever exist
    // stop touching the rendering implementation
    // please

    /*fun bars() {
        val width = 98f
        val height = 18f
        val xHealth = 5f
        val yHealth = 5f
        val xReality = 5f
        val yReality = 10f + height
            val tessellator: Tessellator = Tessellator.getInstance()
            val buffer: BufferBuilder = tessellator.buffer
            val positionMatrix = drawContext?.matrices?.peek()?.positionMatrix
            if (Global().ipCheck()[0]) {
                if (barsToggle) {
                    if (Global().ipCheck()[1]) {
                        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE)
                        buffer.vertex(positionMatrix, xReality, yReality, 0f).color(1f, 1f, 1f, 1f).texture(0f, 0f).next()
                        buffer.vertex(positionMatrix, xReality, yReality + height, 0f).color(1f, 1f, 1f, 1f).texture(0f, 1f).next()
                        buffer.vertex(positionMatrix, xReality + width, yReality + height, 0f).color(1f, 1f, 1f, 1f).texture(1f, 1f).next()
                        buffer.vertex(positionMatrix, xReality + width, yReality, 0f).color(1f, 1f, 1f, 1f).texture(1f, 0f).next()

                        RenderSystem.setShader { GameRenderer.getPositionColorTexProgram() }
                        RenderSystem.setShaderTexture(0, Identifier("scify", "healthmana/mana/mana_${mana}.png"))
                        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)

                        tessellator.draw()
                    }
                    buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE)
                    buffer.vertex(positionMatrix, xHealth, yHealth, 0f).color(1f, 1f, 1f, 1f).texture(0f, 0f).next()
                    buffer.vertex(positionMatrix, xHealth, yHealth+height, 0f).color(1f, 1f, 1f, 1f).texture(0f, 1f).next()
                    buffer.vertex(positionMatrix, xHealth+width, yHealth+height, 0f).color(1f, 1f, 1f, 1f).texture(1f, 1f).next()
                    buffer.vertex(positionMatrix, xHealth+width, yHealth, 0f).color(1f, 1f, 1f, 1f).texture(1f, 0f).next()
                    val maxHealthDivided = MinecraftClient.getInstance().player?.maxHealth?.div(20)?.toInt()
                    val health = if (MinecraftClient.getInstance().player?.health?.toInt()?.div(maxHealthDivided!!)!! <= 20) MinecraftClient.getInstance().player?.health?.toInt()?.div(maxHealthDivided!!) else 20
                    RenderSystem.setShader { GameRenderer.getPositionColorTexProgram() }
                    RenderSystem.setShaderTexture(0, Identifier("scify", "healthmana/health/health_${health}.png"))
                    RenderSystem.setShaderColor(1f, 1f, 1f, 1f)

                    tessellator.draw()
                }
            }
    }*/

    override fun render(context: DrawContext?, tickCounter: RenderTickCounter?) {
        if (!barsToggle!!) { return } // make sure this is supposed to render

        val width = 98
        val height = 18
        val xHealth = 5
        val yHealth = 5

        val maxHealthDivided = MinecraftClient.getInstance().player?.maxHealth?.div(20)?.toInt()
        val health = if (MinecraftClient.getInstance().player?.health?.toInt()?.div(maxHealthDivided!!)!! <= 20) MinecraftClient.getInstance().player?.health?.toInt()?.div(maxHealthDivided) else 20
        val healthbar = Identifier.of("scify", "healthmana/health/health_${health}.png")
        context?.drawTexture(RenderPipelines.GUI_TEXTURED, healthbar, xHealth, yHealth, 0f, 0f, width, height, width, height)
    }
}
