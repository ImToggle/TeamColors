package me.imtoggle.teamcolors.config

import me.imtoggle.teamcolors.util.capitalize
import me.imtoggle.teamcolors.util.vanillaColors
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch

class CategoryConfig(val id: String) {

    val name = id.capitalize()

    @Switch(
        title = "Enabled"
    )
    var enabled = false

    @PreviewOption
    val preview = PreviewVisualizer.PreviewState(id)

    var global = listOf("saturation", "brightness").associateWith { _ -> ConfigEntry() }

    var individual = vanillaColors.mapValues { (name, rgb) -> ColorConfig(rgb) }

}