@file:JvmName("Util")

package me.imtoggle.teamcolors.util

import me.imtoggle.teamcolors.config.ModConfig
import me.imtoggle.teamcolors.data.ColorEntry
import me.imtoggle.teamcolors.hook.PlayerTeamHook
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity

@JvmField
var hasPolyNametag = false

//? if > 1.8.9 {
fun pack(component: net.minecraft.network.chat.Component?, entity: Entity): net.minecraft.network.chat.Component? {
    val nametag = component ?: return null
    val entry = entity.getColorEntry() ?: return component
    return me.imtoggle.teamcolors.data.TagComponent(nametag, entry.nametagColor.argb and 0x00FFFFFF)
}
//? }

//? if = 1.8.9 {
/*val colorCodes = "0123456789abcdef"

fun getFormattingID(prefix: String?): Int {
    if (prefix == null || prefix.length < 2) return -1
    val range = when (ModConfig.prefixMode) {
        0 -> 0..prefix.length - 2
        else -> prefix.length - 2 downTo 0
    }
    for (i in range) {
        if (prefix[i] == '§') {
            colorCodes.indexOf(prefix[i + 1].lowercaseChar()).let {
                if (it != -1) return it
            }
        }
    }
    return -1
}
*///? }

fun Entity.getColorEntry(): ColorEntry? {
    //? if = 1.8.9 {
    /*if (this !is LivingEntity) return null
    val team = scoreboardTeam ?: return null
    *///? }
    val entry = (team as? PlayerTeamHook)?.`teamColors$getColorEntry`() ?: return null
    if (!entry.enabled) return null
    return entry
}

fun String.capitalize() = replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

fun String.toTitleCase() = split("_").joinToString(" ") { word ->
    word.lowercase().capitalize()
}

fun isLegacy(): Boolean {
    //? if = 1.8.9 {
    /*return true
    *///? } else {
    return false
    //? }
}