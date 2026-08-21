@file:JvmName("Util")

package me.imtoggle.teamcolors.util

import dev.isxander.yacl3.api.YetAnotherConfigLib
import me.imtoggle.teamcolors.config.ModConfig
import me.imtoggle.teamcolors.hook.PlayerTeamHook
import net.minecraft.world.entity.Entity
import net.minecraft.world.scores.Team
import java.awt.Color
import kotlin.math.abs

@JvmField
var hasTeam = false

@JvmField
var tagColor = -1

var currentInstance: YetAnotherConfigLib? = null

val vanillaColors = getColors()

var colorMap = mutableMapOf<Int, ColorEntry>()

val settings
    get() = ModConfig.CONFIG.instance().config

val isHitboxEnabled
    get() = settings.hitbox.enabled

val isNametagEnabled
    get() = settings.nametag.enabled

val useHSL = false

private fun getColors(): List<Int> {
    //? if >=26.2 {
    return net.minecraft.world.scores.TeamColor.entries.map { it.rgb() }
    //? } else {
    /*return net.minecraft.ChatFormatting.entries.filter { it.isColor }.map { entry -> entry.color!! }
    *///? }
}

fun ColorEntry.update(color: Int) {
    hitboxColor = calculateColor(color, settings.hitbox)
    nametagColor = calculateColor(color, settings.nametag)
}

fun updateColors() {
    for (color in vanillaColors) {
        colorMap.getOrPut(color) { ColorEntry() }.apply {
            update(color)
        }
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

fun Team.hasTeamColor(): Boolean {
    //? if >= 26.2 {
    return this.color.isPresent
    //? } else {
    /*return this.color.color != null
    *///? }
}

fun getTeamColor(team: Team): Int {
    //? if >= 26.2 {
    return team.color.get().rgb()
    //? } else {
    /*return team.color.color!!
    *///? }
}

//? if >= 1.21.4 {
fun handleState(entity: Entity, state: net.minecraft.client.renderer.entity.state.EntityRenderState) {
    if (!isNametagEnabled || !entity.hasTeamColor()) return
    val nametagColor = getNametagColor(entity)
    //? if >= 26.1 {
    if (state.scoreText != null) {
        state.scoreText = TagComponent(state.scoreText!!, nametagColor)
    }
    //? }
    if (state.nameTag != null) {
        state.nameTag = TagComponent(state.nameTag!!, nametagColor)
    }
}
//? }

fun Int.alphaMask() = this and 0xFF000000.toInt()

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
    return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]) and 0x00FFFFFF
}

fun getHitboxColor(entity: Entity): Int {
    return (entity.team!! as PlayerTeamHook).`teamColors$getColorEntry`().hitboxColor
}

fun getNametagColor(entity: Entity): Int {
    return (entity.team!! as PlayerTeamHook).`teamColors$getColorEntry`().nametagColor
}

fun getColorEntry(color: Int): ColorEntry {
    return colorMap[color]!!
}

data class Entry(var mode: Boolean = false, var value: Int = 100)

data class ConfigEntry(var enabled: Boolean = true, var saturation: Entry = Entry(), var brightness: Entry = Entry())

data class Config(var hitbox: ConfigEntry = ConfigEntry(), var nametag: ConfigEntry = ConfigEntry())