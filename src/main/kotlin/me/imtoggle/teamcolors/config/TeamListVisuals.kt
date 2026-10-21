package me.imtoggle.teamcolors.config

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.imtoggle.teamcolors.util.toTitleCase
import me.imtoggle.teamcolors.util.vanillaColors
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.Visualizer
import org.polyfrost.oneconfig.internal.ui.api.settings.TileListOptionData
import org.polyfrost.oneconfig.internal.ui.components.picker.PickerEntry
import org.polyfrost.oneconfig.internal.ui.components.picker.PickerTileIcon
import org.polyfrost.oneconfig.internal.ui.components.settings.TileListOption
import org.polyfrost.oneconfig.internal.ui.themes.LocalTheme

private data class Swatch(override val id: String, override val displayName: String, val color: Color) : PickerEntry

private val Swatches = vanillaColors.map {
    Swatch(it.key, it.key.toTitleCase(), Color(it.value).copy(alpha = 1f))
}

private val SwatchShape = RoundedCornerShape(4.dp)

private val SwatchIcon: PickerTileIcon<Swatch> = { swatch, modifier, alpha ->
    Box(
        modifier
            .aspectRatio(1f)
            .alpha(alpha)
            .clip(SwatchShape)
            .background(swatch.color, SwatchShape)
            .border(1.dp, LocalTheme.current.borderColor, SwatchShape)
    )
}

class TeamListVisualizer : Visualizer {

    @Composable
    override fun visualize(prop: Property<*>) {
        TileListOption(
            TileListOptionData(prop),
            Swatches,
            SwatchIcon,
            titleKey = "Select teams",
            searchPlaceholderKey = "Search teams",
            emptyKey = "No matching teams",
            unavailableKey = "Unknown team",
        )
    }

}