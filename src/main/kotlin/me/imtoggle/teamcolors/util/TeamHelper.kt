package me.imtoggle.teamcolors.util

import me.imtoggle.teamcolors.data.ColorEntry
import me.imtoggle.teamcolors.hook.ColorHook
import net.minecraft.world.entity.Entity

object TeamHelper {

    fun Entity.hasTeam(): Boolean {
        return team != null
    }

    fun Entity.hasTeamColor(): Boolean {
        return team?.color?.isPresent ?: false
    }

    fun Entity.getColorEntry(): ColorEntry? {
        return (team as? ColorHook)?.`teamColors$getColorEntry`()
    }

}