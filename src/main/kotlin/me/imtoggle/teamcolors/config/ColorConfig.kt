package me.imtoggle.teamcolors.config

import org.polyfrost.compose.render.PolyColor
import org.polyfrost.oneconfig.api.config.v1.annotations.Checkbox
import org.polyfrost.oneconfig.api.config.v1.annotations.Color

class ColorConfig(val vanillaColor: Int) {

    @Checkbox(
        title = "Override Global Settings"
    )
    var overrideGlobal = false

    @Color(
        title = "Color",
        alpha = false
    )
    var color = PolyColor(vanillaColor)

}