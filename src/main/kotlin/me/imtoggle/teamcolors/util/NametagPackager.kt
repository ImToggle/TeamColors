package me.imtoggle.teamcolors.util

import me.imtoggle.teamcolors.data.TagComponent
import me.imtoggle.teamcolors.util.TeamHelper.getColorEntry
import me.imtoggle.teamcolors.util.TeamHelper.hasTeamColor
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity

object NametagPackager {

    fun pack(component: Component?, entity: Entity): Component? {
        val nametag = component ?: return null
        if (!entity.hasTeamColor()) return component
        val entry = entity.getColorEntry() ?: return component
        return TagComponent(nametag, entry.getColor("nametag"))
    }

}