@file:JvmName("Util")

package me.imtoggle.teamcolors

import net.minecraft.world.entity.Entity
import java.awt.Color

val vanillaColors = getColors()

private val hitboxMap = HashMap<Int, Int>()
private val nametagMap = HashMap<Int, Int>()

var settings = Config()

fun getColors(): List<Int> {
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

fun getRed(color: Int): Int {
    return color and 0x00FF0000 shr 16
}

fun getGreen(color: Int): Int {
    return color and 0x0000FF00 shr 8
}

fun getBlue(color: Int): Int {
    return color and 0x000000FF
}

fun calculateColor(color: Int, cfgEntry: ConfigEntry): Int {
    val hsb = FloatArray(3)
    Color.RGBtoHSB(getRed(color), getGreen(color), getBlue(color), hsb)
    fun modify(i: Int, entry: Entry) {
        val value = entry.value / 100f
        hsb[i] = if (entry.mode) value else hsb[i] * value
    }
    modify(1, cfgEntry.saturation)
    modify(2, cfgEntry.brightness)
    return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2])
}

fun getHitboxColor(rgb: Int): Int {
    return hitboxMap[rgb] ?: rgb
}

fun getNametagColor(rgb: Int): Int {
    return nametagMap[rgb] ?: rgb
}

data class Entry(var mode: Boolean = false, var value: Int = 100) : Cloneable {
    public override fun clone() = Entry(this.mode, this.value)
}

data class ConfigEntry(var saturation: Entry = Entry(), var brightness: Entry = Entry()) : Cloneable {
    public override fun clone() = ConfigEntry(saturation.copy(), brightness.copy())
}

data class Config(var hitbox: ConfigEntry = ConfigEntry(), var nametag: ConfigEntry = ConfigEntry()) : Cloneable {
    public override fun clone() = Config(hitbox.clone(), nametag.clone())

}