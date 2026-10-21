package me.imtoggle.teamcolors.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.polyfrost.compose.render.PolyColor

class ColorEntry {

    var enabled = true

    var hitboxColor by mutableStateOf(PolyColor())

    var nametagColor by mutableStateOf(PolyColor())

}