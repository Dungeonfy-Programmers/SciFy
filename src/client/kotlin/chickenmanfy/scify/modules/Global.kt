package chickenmanfy.scify.modules

import net.minecraft.client.MinecraftClient

class Global {
  /*
   * ipCheck: returns Array:
   * [is_dungeonfy: Boolean, is_dungeonfy_2: Boolean]*/
  fun ipCheck(): Array<Boolean> {
    // TODO: Dynamic update in case of ip list change
    val dfy1Ips = arrayOf("dungeonfy.minehut.gg", "minehut.com", "localhost", "51.222.121.148:25599", "miningfy.minehut.gg", "dfymuseum.minehut.gg")
    val dfy2Ips = arrayOf("no dungeonfy 2 :(")
    for (ip in dfy1Ips) {
      if (ip == MinecraftClient.getInstance().networkHandler?.serverInfo?.address) {
        return arrayOf(true, false)
      }
    }
    for (ip in dfy2Ips) {
      if (ip == MinecraftClient.getInstance().networkHandler?.serverInfo?.address) {
        return arrayOf(true, true)
      }
    }
    if (modDisableOverride) {
      return arrayOf(true, true)
    }
    return arrayOf(false, false)
  }
}
