package me.imtoggle.teamcolors.util

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.FormattedText
import net.minecraft.network.chat.Style

class TagComponent(val inputComponent: Component, val nametagColor: Int) : Component by inputComponent {
    override fun getString() = inputComponent.string

    override fun getString(limit: Int) = inputComponent.getString(limit)

    override fun tryCollapseToString() = inputComponent.tryCollapseToString()

    override fun plainCopy() = inputComponent.plainCopy()

    override fun copy() = inputComponent.copy()

    override fun <T : Any> visit(output: FormattedText.StyledContentConsumer<T>, parentStyle: Style) = inputComponent.visit(output, parentStyle)

    override fun <T : Any> visit(output: FormattedText.ContentConsumer<T>) = inputComponent.visit(output)

    override fun toFlatList() = inputComponent.toFlatList()

    override fun toFlatList(rootStyle: Style) = inputComponent.toFlatList(rootStyle)

    override fun contains(other: Component) = inputComponent.contains(other)
}