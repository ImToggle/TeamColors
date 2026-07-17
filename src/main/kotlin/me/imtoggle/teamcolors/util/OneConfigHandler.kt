@file:JvmName("OneConfigHandler")

package me.imtoggle.teamcolors.util

import com.google.common.collect.ImmutableList
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionGroup
import me.imtoggle.teamcolors.config.PreviewVisualizer
import org.polyfrost.oneconfig.api.config.v1.Properties
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.oneconfig.api.config.v1.Visualizer
import org.polyfrost.oneconfig.api.config.v1.dsl.category
import org.polyfrost.oneconfig.api.config.v1.dsl.noCache
import org.polyfrost.oneconfig.api.config.v1.dsl.saveFunction
import org.polyfrost.oneconfig.api.config.v1.dsl.subcategory
import org.polyfrost.oneconfig.api.config.v1.internal.ConfigVisualizer

fun handleTree(tree: Tree): Tree {
    val categories = arrayOf("Hitbox", "Nametag")
    val newTree = Tree.tree()
    newTree.id = tree.id
    newTree.title = tree.title
    newTree.noCache = true
    if (tree.saveFunction != null) {
        newTree.saveFunction = tree.saveFunction
    }
    newTree.addMetadata("icon_path", tree.getMetadata("icon_path"))
    var optionGroups:  ImmutableList<OptionGroup>? = null
    var enabled: Property<*>? = null
    tree.onAll { _, node ->
        val group = node as Tree
        if (node.title in categories) {
            optionGroups = currentInstance!!.categories()[if (node.title == "Hitbox") 0 else 1].groups()
            group.onAll { _, node ->
                when (node.title) {
                    "Enabled" -> {
                        enabled = node as Property<*>
                    }
                    "Color Preview" -> {
                        val property = node as Property<*>
                        property.addMetadata("visualizer", PreviewVisualizer::class.java)
                        @Suppress("UNCHECKED_CAST")
                        enabled?.let { property.addDisplayCondition(it as Property<Boolean>, false) }
                    }
                }
                node.subcategory = ConfigVisualizer.DEFAULT_SUBCATEGORY
                newTree.put(node)
            }
        } else {
            @Suppress("UNCHECKED_CAST")
            val option = optionGroups?.get(if (group.title == "Saturation") 1 else 2)?.options()[0] as? Option<Boolean>
            val groupTree = Tree.tree()
            groupTree.id = group.id
            groupTree.title = group.title
            groupTree.category = group.category
            groupTree.subcategory = group.subcategory
            group.onAll { _, node ->
                var property = node as Property<*>
                when (property.title) {
                    "Mode" -> option?.let {
                        property = property.toRadioButton(it, arrayOf("Multiplier", "Absolute"))
                    }
                }
                @Suppress("UNCHECKED_CAST")
                enabled?.let { property.addDisplayCondition(it as Property<Boolean>, false) }
                property.addCallback {
                    updateColors()
                    return@addCallback false
                }
                groupTree.put(property)
            }

            newTree.put(groupTree)
        }
    }
    return newTree
}

fun Property<*>.toRadioButton(option: Option<Boolean>, options: Array<String>): Property<Int> {
    val newProperty = Properties.functional(
        { if (option.binding().value) 1 else 0 },
        { option.requestSet(it == 1) },
        this.id,
        this.title,
        type = Int::class.java
    )
    newProperty.category = this.category
    newProperty.subcategory = this.subcategory
    newProperty.addMetadata("default", this.getMetadata("default"))
    newProperty.addMetadata("options", options)
    newProperty.addMetadata("visualizer", Visualizer.RadioVisualizer::class.java)
    return newProperty
}

fun createMap() = try {
    val clazz = Class.forName("androidx.compose.runtime.SnapshotStateKt")
    val method = clazz.getMethod("mutableStateMapOf")
    @Suppress("UNCHECKED_CAST")
    method.invoke(null) as androidx.compose.runtime.snapshots.SnapshotStateMap<Int, Int>
} catch (e: Exception) {
    e.printStackTrace()
    mutableMapOf()
}