@file:JvmName("Util")

package me.imtoggle.teamcolors.util

import me.imtoggle.teamcolors.config.ModConfig
import me.imtoggle.teamcolors.data.ColorEntry
import me.imtoggle.teamcolors.hook.ColorHook
import net.minecraft.world.entity.Entity

fun isEnabled(category: String) = ModConfig.categoriesConfig[category]?.enabled == true

fun isDisabled(category: String) = ModConfig.categoriesConfig[category]?.enabled == false

fun Entity.hasTeamColor(): Boolean {
    //? if >= 26.2 {
    return team?.color?.isPresent ?: false
    //? } else {
    /*return team?.color?.isColor ?: false
    *///? }
}

fun Entity.getColorEntry(): ColorEntry? {
    return (team as? ColorHook)?.`teamColors$getColorEntry`()
}

fun String.capitalize() = replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

fun String.toTitleCase() = split("_").joinToString(" ") { word ->
    word.lowercase().capitalize()
}