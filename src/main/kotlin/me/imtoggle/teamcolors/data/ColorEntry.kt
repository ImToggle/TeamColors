package me.imtoggle.teamcolors.data

import androidx.compose.runtime.toMutableStateMap
import me.imtoggle.teamcolors.config.ModConfig

class ColorEntry {

    val colors = ModConfig.CATEGORIES.associateWith { _ -> 0 }.toList().toMutableStateMap()

    fun getColor(category: String) = colors[category]!!

}