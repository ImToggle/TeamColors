package me.imtoggle.teamcolors.data

import androidx.compose.runtime.toMutableStateMap
import me.imtoggle.teamcolors.config.ModConfig
import org.polyfrost.compose.render.PolyColor

class ColorEntry {

    val colors = ModConfig.CATEGORIES.associateWith { _ -> PolyColor() }.toList().toMutableStateMap()

    fun getColor(category: String): PolyColor {
        return colors[category]!!
    }

}