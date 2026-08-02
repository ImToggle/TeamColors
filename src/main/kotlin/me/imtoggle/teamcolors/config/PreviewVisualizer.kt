package me.imtoggle.teamcolors.config

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.imtoggle.teamcolors.util.colorMap
import me.imtoggle.teamcolors.util.vanillaColors
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.Visualizer
import org.polyfrost.oneconfig.internal.ui.themes.LocalTheme

class PreviewVisualizer : Visualizer {

    @Composable
    override fun visualize(prop: Property<*>) {
        val isHitbox = prop.get() == true
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 0..1) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    for (vanillaColor in vanillaColors) {
                        val color = Color(
                            when {
                                i == 0 -> vanillaColor
                                else -> {
                                    colorMap[vanillaColor]?.let {
                                        if (isHitbox) {
                                            it.hitboxColor
                                        } else {
                                            it.nametagColor
                                        }
                                    } ?: vanillaColor
                                }
                            } or 0xFF000000.toInt()
                        )
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = color,
                                    shape = LocalTheme.current.sideBarNavigationEntryShape
                                )
                                .border(
                                    width = 1.dp,
                                    color = LocalTheme.current.borderColor,
                                    shape = LocalTheme.current.sideBarNavigationEntryShape
                                )
                        )
                    }
                }
            }
        }
    }
}