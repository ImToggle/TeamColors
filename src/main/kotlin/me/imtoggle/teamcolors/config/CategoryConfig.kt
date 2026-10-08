package me.imtoggle.teamcolors.config

import me.imtoggle.teamcolors.util.vanillaColors
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch

class CategoryConfig(val name: String) {

    @Switch(
        title = "Enabled"
    )
    var enabled = false

    @PreviewOption
    var preview = PreviewVisualizer.PreviewState(name, null)

    var global = mapOf(
        "Saturation" to ConfigEntry(),
        "Brightness" to ConfigEntry()
    )

    var individual = vanillaColors.map { (name, rgb) -> name to ColorConfig(rgb) }

}