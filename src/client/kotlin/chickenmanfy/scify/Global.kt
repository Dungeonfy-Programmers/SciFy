package chickenmanfy.scify

class Global {
  /*
   * ipCheck: returns Array:
   * [is_dungeonfy: Boolean, is_dungeonfy_2: Boolean]*/
  fun ipCheck(): Array {
    // TODO: Dynamic update in case of ip list change
    var dfy1_ips = ["dungeonfy.minehut.gg", "minehut.com", "localhost", "51.222.121.148:25599", "miningfy.minehut.gg"]
    var dfy2_ips = ["no dungeonfy 2 :("]
    for (ip in dfy1_ips) {
      if (ip = MinecraftClient.getInstance().networkHandler?.serverInfo?.address) {
        return [true, false]
      }
    }
    for (ip in dfy2_ips) {
      if (ip == MinecraftClient.getInstance().networkHandler?.serverInfo?.address) {
        return [true, true]
      }
    }
    if (modDisableOverride) {
      return [true, true]
    }
    return [false, false]
  }
}
