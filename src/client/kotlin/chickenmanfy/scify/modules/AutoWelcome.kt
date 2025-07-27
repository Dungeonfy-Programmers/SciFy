package chickenmanfy.scify.modules

var autoWelcomeToggle: Boolean = false
class AutoWelcome {
    fun toggleAutoWelcome() {
        autoWelcomeToggle = !autoWelcomeToggle
        // Debug
        println("Auto Welcome ${if (autoWelcomeToggle) "enabled" else "disabled"}")
    }

    fun autoWelcome() {
      if (Global().ipCheck()[0]) {
            if (autoWelcomeToggle) {
                return
            }
        }
    }
}
