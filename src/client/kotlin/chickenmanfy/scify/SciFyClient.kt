package chickenmanfy.scify

import chickenmanfy.scify.modules.* // woo fancy import all modules
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.impl.util.SystemProperties
import net.minecraft.util.Identifier
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileWriter
import kotlin.io.path.createFile
import kotlin.io.path.exists

@Environment(EnvType.CLIENT)
object SciFyClient : ClientModInitializer {
    override fun onInitializeClient() {
        // Initialize Config
        if (!FabricLoader.getInstance().configDir.resolve("scify-config.yaml").toFile().exists()) {
            println("Config file does not exist, creating one...")
            val fileCreated = FabricLoader.getInstance().configDir.resolve("scify-config.yaml").toFile().createNewFile()
            print(fileCreated)

            val defaultConfig = HashMap<String, Boolean>()
            defaultConfig["watermarkEnabled"] = true
            defaultConfig["barsEnabled"] = true
            defaultConfig["autoWelcomeEnabled"] = false
            defaultConfig["cooldownTimerEnabled"] = false
            val yaml = Yaml()
            val fileWriter = FileWriter(FabricLoader.getInstance().configDir.resolve("scify-config.yaml").toFile())
            yaml.dump(defaultConfig, fileWriter)
            fileWriter.close()
        }

        // Run the Modules
        Hotkey().hotkeys()
        HudElementRegistry.attachElementAfter(Identifier.of("minecraft", "hotbar"), Identifier.of("scify", "cooldown"), Cooldown())
        ClientReceiveMessageEvents.GAME.register(Identifier.of("scify", "autowelcome"), AutoWelcome())
        HudElementRegistry.addLast(Identifier.of("scify", "bars"), Bars())
        HudElementRegistry.addFirst(Identifier.of("scify", "watermark"), Watermark())
    }
}
