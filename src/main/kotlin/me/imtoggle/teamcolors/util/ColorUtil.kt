@file:JvmName("ColorUtil")

package me.imtoggle.teamcolors.util

import me.imtoggle.teamcolors.config.CategoryConfig
import me.imtoggle.teamcolors.config.ColorConfig
import me.imtoggle.teamcolors.config.ConfigEntry
import me.imtoggle.teamcolors.config.ModConfig
import me.imtoggle.teamcolors.data.ColorEntry
import org.polyfrost.compose.render.PolyColor
import java.awt.Color

val vanillaColors = getVanilla()

val colorMap = vanillaColors.map { it.value to ColorEntry() }.toMap()

fun updateColorMap() {
    ModConfig.categoriesConfig.values.forEach { category ->
        updateCategory(category)
    }
}

fun updateCategory(category: CategoryConfig) {
    category.individual.forEach { (_, config) ->
        updateIndividual(category, config.vanillaColor, config)
    }
}

fun updateIndividual(category: CategoryConfig, vanillaRGB: Int, config: ColorConfig) {
    val colorEntry = colorMap[vanillaRGB] ?: return
    colorEntry.colors[category.id] = if (config.overrideGlobal) {
        config.color.copy()
    } else {
        PolyColor(modifySB(vanillaRGB, category))
    }
}

fun PolyColor.copy(): PolyColor = PolyColor(argb, chroma, chromaSpeed)

fun Int.red() = this and 0x00FF0000 shr 16

fun Int.green() = this and 0x0000FF00 shr 8

fun Int.blue() = this and 0x000000FF

private fun modify(input: Float, entry: ConfigEntry): Float {
    val value = entry.value / 100f
    return if (entry.mode == 1) value else input * value
}

private fun modifySB(vanilla: Int, category: CategoryConfig): Int {
    val hsb = FloatArray(3)
    Color.RGBtoHSB(vanilla.red(), vanilla.green(), vanilla.blue(), hsb)
    hsb[1] = modify(hsb[1], category.global["saturation"]!!)
    hsb[2] = modify(hsb[2], category.global["brightness"]!!)
    return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]) and 0x00FFFFFF
}

private fun getVanilla(): Map<String, Int> {
    //? if >= 26.2 {
    return net.minecraft.world.scores.TeamColor.entries.associate {
        it.name.lowercase() to it.rgb()
    }
    //? } else {
    /*return ChatFormatting.entries.filter { it.isColor && it.color != null }.associate {
        it.name to it.color!!
    }
    *///? }
}