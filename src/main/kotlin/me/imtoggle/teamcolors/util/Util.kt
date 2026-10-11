@file:JvmName("Util")

package me.imtoggle.teamcolors.util

import me.imtoggle.teamcolors.data.ColorEntry
import me.imtoggle.teamcolors.hook.PlayerTeamHook
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity

@JvmField
var hasPolyNametag = false

//? if > 1.8.9 {
fun pack(component: net.minecraft.network.chat.Component?, entity: Entity): net.minecraft.network.chat.Component? {
    val nametag = component ?: return null
    if (!entity.hasTeamColor()) return component
    val entry = entity.getColorEntry() ?: return component
    return me.imtoggle.teamcolors.data.TagComponent(nametag, entry.nametagColor.argb and 0x00FFFFFF)
}
//? }

//? if = 1.8.9 {
/*val colorCodes = "0123456789abcdef"

fun getFormattingID(prefix: String?): Int {
    if (prefix == null || prefix.length < 2) return -1
    for (i in prefix.length - 2 downTo 0) {
        if (prefix[i] == '§') {
            colorCodes.indexOf(prefix[i + 1].lowercaseChar()).let {
                if (it != -1) return it
            }
        }
    }
    return -1
}
*///? }

fun Entity.hasTeamColor(): Boolean {
    //? if = 1.8.9 {
    /*if (this !is LivingEntity) return false
    val team = scoreboardTeam ?: return false
    *///? }
    return (team as? PlayerTeamHook)?.`teamColors$hasColor`() ?: false
}

fun Entity.getColorEntry(): ColorEntry? {
    //? if = 1.8.9 {
    /*if (this !is LivingEntity) return null
    val team = scoreboardTeam ?: return null
    *///? }
    return (team as? PlayerTeamHook)?.`teamColors$getColorEntry`()
}

fun String.capitalize() = replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

fun String.toTitleCase() = split("_").joinToString(" ") { word ->
    word.lowercase().capitalize()
}