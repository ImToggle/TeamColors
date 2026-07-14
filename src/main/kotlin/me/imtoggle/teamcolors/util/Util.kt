@file:JvmName("Util")

package me.imtoggle.teamcolors.util

import dev.isxander.yacl3.api.YetAnotherConfigLib
import me.imtoggle.teamcolors.config.ModConfig
import net.minecraft.world.entity.Entity
import java.awt.Color
import kotlin.math.abs

@JvmField
var hasTeam = false

@JvmField
var tagColor = -1

var currentInstance: YetAnotherConfigLib? = null

val vanillaColors = getColors()

var hitboxMap = mutableMapOf<Int, Int>()
var nametagMap = mutableMapOf<Int, Int>()

val settings
    get() = ModConfig.CONFIG.instance().config

val isHitboxEnabled
    get() = settings.hitbox.enabled

val isNametagEnabled
    get() = settings.nametag.enabled

val useHSL = true

private fun getColors(): List<Int> {
    //? if >=26.2 {
    return net.minecraft.world.scores.TeamColor.entries.map { it.rgb() }
    //? } else {
    /*return net.minecraft.ChatFormatting.entries.filter { it.isColor }.map { entry -> entry.color!! }
    *///? }
}

fun updateColors() {
    hitboxMap.clear()
    nametagMap.clear()
    for (color in vanillaColors) {
        hitboxMap[color] = calculateColor(color, settings.hitbox)
        nametagMap[color] = calculateColor(color, settings.nametag)
    }
}

fun Entity.hasTeamColor(): Boolean {
    val team = this.team ?: return false
    //? if >= 26.2 {
    return team.color.isPresent
    //? } else {
    /*return team.color.color != null
    *///? }
}

fun getTeamColor(entity: Entity): Int {
    //? if >= 26.2 {
    return entity.team!!.color.get().rgb()
    //? } else {
    /*return entity.team!!.color.color ?: -1
    *///? }
}

//? if >= 1.21.4 {
fun handleState(entity: Entity, state: net.minecraft.client.renderer.entity.state.EntityRenderState) {
    if (!isNametagEnabled || !entity.hasTeamColor()) return
    val teamColor = getTeamColor(entity)
    //? if >= 26.1 {
    if (state.scoreText != null) {
        state.scoreText = TagComponent(state.scoreText!!, teamColor)
    }
    //? }
    if (state.nameTag != null) {
        state.nameTag = TagComponent(state.nameTag!!, teamColor)
    }
}
//? }

fun Int.red() = this and 0x00FF0000 shr 16

fun Int.green() = this and 0x0000FF00 shr 8

fun Int.blue() = this and 0x000000FF

private fun calculateColor(color: Int, cfgEntry: ConfigEntry): Int {
    return if (useHSL) {
        color.modifySL(cfgEntry)
    } else {
        color.modifySB(cfgEntry)
    }
}

fun modify(n: Float, entry: Entry): Float {
    val value = entry.value / 100f
    return if (entry.mode) value else n * value
}

fun Int.modifySL(cfgEntry: ConfigEntry): Int {
    val r = this.red() / 255f
    val g = this.green() / 255f
    val b = this.blue() / 255f

    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)
    val delta = max - min

    var l = (max + min) * 0.5f
    var s = if (delta == 0f) 0f else delta / (1f - abs(2f * l - 1f))
    var hPrime = 0f

    if (delta != 0f) {
        hPrime = when (max) {
            r -> (g - b) / delta
            g -> (b - r) / delta + 2f
            else -> (r - g) / delta + 4f
        }
        if (hPrime < 0f) hPrime += 6f
    }

    s = modify(s, cfgEntry.saturation)
    l = modify(l, cfgEntry.brightness)

    val c = (1f - abs(2f * l - 1f)) * s
    val x = c * (1f - abs((hPrime % 2f) - 1f))
    val m = l - c * 0.5f

    var r2 = 0f
    var g2 = 0f
    var b2 = 0f

    when (hPrime.toInt()) {
        0 -> { r2 = c; g2 = x }
        1 -> { r2 = x; g2 = c }
        2 -> { g2 = c; b2 = x }
        3 -> { g2 = x; b2 = c }
        4 -> { r2 = x; b2 = c }
        5 -> { r2 = c; b2 = x }
    }

    val finalR = ((r2 + m) * 255f).toInt().coerceIn(0, 255)
    val finalG = ((g2 + m) * 255f).toInt().coerceIn(0, 255)
    val finalB = ((b2 + m) * 255f).toInt().coerceIn(0, 255)

    return (finalR shl 16) or (finalG shl 8) or finalB
}

fun Int.modifySB(cfgEntry: ConfigEntry): Int {
    val hsb = FloatArray(3)
    Color.RGBtoHSB(this.red(), this.green(), this.blue(), hsb)
    hsb[1] = modify(hsb[1], cfgEntry.saturation)
    hsb[2] = modify(hsb[2], cfgEntry.brightness)
    return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2])
}

fun getHitboxColor(entity: Entity): Int {
    return hitboxMap[getTeamColor(entity)] ?: -1
}

fun getHitboxColor(rgb: Int): Int {
    return hitboxMap[rgb] ?: rgb
}

fun getNametagColor(entity: Entity): Int {
    return nametagMap[getTeamColor(entity)] ?: -1
}

fun getNametagColor(rgb: Int): Int {
    return nametagMap[rgb] ?: rgb
}

data class Entry(var mode: Boolean = false, var value: Int = 100)

data class ConfigEntry(var enabled: Boolean = true, var saturation: Entry = Entry(), var brightness: Entry = Entry())

data class Config(var hitbox: ConfigEntry = ConfigEntry(), var nametag: ConfigEntry = ConfigEntry())