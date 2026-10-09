package me.imtoggle.teamcolors.config

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import me.imtoggle.teamcolors.util.colorMap
import me.imtoggle.teamcolors.util.vanillaColors
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.oneconfig.api.config.v1.Visualizer
import org.polyfrost.oneconfig.internal.ui.components.Icon
import org.polyfrost.oneconfig.internal.ui.components.onClick
import org.polyfrost.oneconfig.internal.ui.components.rememberInteractionSource
import org.polyfrost.oneconfig.internal.ui.themes.LocalTheme

class PreviewVisualizer : Visualizer {

    class PreviewState(var category: String, var currentColor: Int? = null)

    @Composable
    override fun visualize(prop: Property<*>) {
        val state = prop.getAs<PreviewState>()
        var selectedColor by remember { mutableStateOf(state.currentColor) }

        fun setCurrentColor(newColor: Int?) {
            state.currentColor = newColor
            selectedColor = newColor
            ModConfig.tree.onAll { _, node ->
                if (node is Tree) {
                    node.onAllProps { _, property ->
                        property.revaluateDisplay()
                    }
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val interactionSource = rememberInteractionSource()
            if (selectedColor != null) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .pointerHoverIcon(PointerIcon.Hand)
                        .onClick(interactionSource) {
                            setCurrentColor(null)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    val isHovered by interactionSource.collectIsHoveredAsState()
                    val color = if (isHovered) LocalTheme.current.textColor else LocalTheme.current.textColorSecondary
                    Icon("undo",
                        color = color,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            for (vanillaColor in vanillaColors) {
                val vanillaRGB = vanillaColor.value
                Box {
                    val interactionSource = rememberInteractionSource()
                    val isHovered by interactionSource.collectIsHoveredAsState()
                    val isSelected = vanillaRGB == selectedColor
                    val bgColor by animateColorAsState(
                        when {
                            isSelected -> LocalTheme.current.chipBackground
                            isHovered -> LocalTheme.current.modCardBackground
                            else -> LocalTheme.current.modCardBackground.copy(alpha = 0f)
                        }
                    )
                    Column(
                        modifier = Modifier
                            .background(bgColor, shape = LocalTheme.current.sideBarNavigationEntryShape)
                            .width(40.dp).height(72.dp)
                            .pointerHoverIcon(PointerIcon.Hand)
                            .onClick(interactionSource = interactionSource) {
                                setCurrentColor(vanillaRGB)
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
                    ) {
                        for (i in 0..1) {
                            val color = Color(
                                when {
                                    i == 0 -> vanillaRGB
                                    else -> colorMap[vanillaRGB]?.colors[state.category]!!.rawArgb
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
}