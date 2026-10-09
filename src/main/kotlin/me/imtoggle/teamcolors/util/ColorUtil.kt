@file:JvmName("ColorUtil")

package me.imtoggle.teamcolors.util

import me.imtoggle.teamcolors.config.ModConfig
import me.imtoggle.teamcolors.data.ColorEntry
import org.polyfrost.compose.render.PolyColor
import org.polyfrost.oneconfig.api.config.v1.Tree
import java.awt.Color

val vanillaColors = getVanilla()

val colorMap = vanillaColors.map { it.value to ColorEntry() }.toMap()

fun updateColorMap() {
    ModConfig.CATEGORIES.forEach { category ->
        updateCategory(category)
    }
}

fun updateCategory(category: String) {
    vanillaColors.forEach { (id, vanillaRGB) ->
        updateIndividual(category, vanillaRGB, ModConfig.tree.get("${category}_${id}") as Tree)
    }
}

fun updateIndividual(category: String, vanillaRGB: Int, tree: Tree) {
    val colorEntry = colorMap[vanillaRGB] ?: return
    colorEntry.colors[category] = if (tree.getProp("overrideGlobal").get() == true) {
        tree.getProp("color").getAs<PolyColor>().argb
    } else {
        modifySB(vanillaRGB, category)
    }
}

fun Int.red() = this and 0x00FF0000 shr 16

fun Int.green() = this and 0x0000FF00 shr 8

fun Int.blue() = this and 0x000000FF

private fun modify(input: Float, category: String, id: String): Float {
    val configEntry = ModConfig.tree.get("${category}_${id}") as Tree
    val mode = configEntry.getProp("mode").getAs<Int>() == 1
    val value = configEntry.getProp("value").getAs<Int>() / 100f
    return if (mode) value else input * value
}

private fun modifySB(vanilla: Int, category: String): Int {
    val hsb = FloatArray(3)
    Color.RGBtoHSB(vanilla.red(), vanilla.green(), vanilla.blue(), hsb)
    hsb[1] = modify(hsb[1], category, "saturation")
    hsb[2] = modify(hsb[2], category, "brightness")
    return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]) and 0x00FFFFFF
}

private fun getVanilla(): Map<String, Int> {
    return net.minecraft.world.scores.TeamColor.entries.associate {
        it.name.lowercase() to it.rgb()
    }
}