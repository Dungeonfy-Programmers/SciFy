package chickenmanfy.scify

import net.minecraft.client.MinecraftClient
//import chickenmanfy.scify.modules.Menu

class Global {
  /*
   * ipCheck: returns Array:
   * [is_dungeonfy: Boolean, is_dungeonfy_2: Boolean]*/
  fun ipCheck(): Array<Boolean> {
    // TODO: Dynamic update in case of ip list change
    var dfy1_ips = arrayOf("dungeonfy.minehut.gg", "minehut.com", "localhost", "51.222.121.148:25599", "miningfy.minehut.gg")
    var dfy2_ips = arrayOf("no dungeonfy 2 :(")
    for (ip in dfy1_ips) {
      if (ip == MinecraftClient.getInstance().networkHandler?.serverInfo?.address) {
        return arrayOf(true, false)
      }
    }
    for (ip in dfy2_ips) {
      if (ip == MinecraftClient.getInstance().networkHandler?.serverInfo?.address) {
        return arrayOf(true, true)
      }
    }
//    if (modDisableOverride) {
//      return arrayOf(true, true)
//    }
    return arrayOf(false, false)
  }
}
