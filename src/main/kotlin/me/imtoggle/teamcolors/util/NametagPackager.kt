@file:JvmName("NametagPackager")

package me.imtoggle.teamcolors.util

import me.imtoggle.teamcolors.data.TagComponent
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity

fun pack(component: Component?, entity: Entity): Component? {
    val nametag = component ?: return null
    if (!entity.hasTeamColor()) return component
    val entry = entity.getColorEntry() ?: return component
    return TagComponent(nametag, entry.getColor("nametag").argb and 0x00FFFFFF)
}