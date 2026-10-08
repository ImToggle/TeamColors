@file:JvmName("ColorUtil")

package me.imtoggle.teamcolors.util

import androidx.compose.runtime.mutableStateMapOf
import me.imtoggle.teamcolors.data.ColorEntry

val vanillaColors = getVanilla()

val colorMap = mutableStateMapOf<Int, ColorEntry>()

private fun getVanilla(): Map<String, Int> {
    return net.minecraft.world.scores.TeamColor.entries.associate {
        it.name to it.rgb()
    }
}