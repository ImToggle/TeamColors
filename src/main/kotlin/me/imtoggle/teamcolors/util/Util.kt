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

fun Entity.hasTeamColor(): Boolean {
    //? if >= 26.2 {
    return team?.color?.isPresent ?: false
    //? } elif > 1.8.9 {
    /*return team?.color?.isColor ?: false
    *///? } else {
    /*if (this is LivingEntity) {
        val team = scoreboardTeam ?: return false
        return team is net.minecraft.world.scores.PlayerTeam && team.color.isColor
    }
    return false
    *///? }
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