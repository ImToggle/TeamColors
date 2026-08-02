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
import java.util.UUID

fun handleTree(tree: Tree): Tree {
    val newTree = Tree.tree()
    newTree.id = tree.id
    newTree.title = tree.title
    newTree.noCache = true
    if (tree.saveFunction != null) {
        newTree.saveFunction = tree.saveFunction
    }
    newTree.addMetadata("icon_path", tree.getMetadata("icon_path"))
    var optionGroups: ImmutableList<OptionGroup>? = null
    var enabled: Property<*>? = null
    var group: Tree? = null
    tree.onAll { _, node ->
        var property = node as Property<*>
        var shouldAdd = true
        when (node.title) {
            "Enabled" -> {
                enabled = property
                optionGroups = currentInstance!!.categories()[if (property.category == "Hitbox") 0 else 1].groups()
            }
            "Color Preview" -> {
                property.addMetadata("visualizer", PreviewVisualizer::class.java)
            }
            "Mode" -> {
                shouldAdd = false
                @Suppress("UNCHECKED_CAST")
                val option = optionGroups?.get(if (property.subcategory == "Saturation") 1 else 2)?.options()[0] as? Option<Boolean>
                option?.let {
                    property = property.toRadioButton(it, arrayOf("Multiplier", "Absolute"))
                }
                property.addCallback {
                    updateColors()
                    return@addCallback false
                }
                group = Tree.tree(UUID.randomUUID().toString())
                group.title = property.subcategory
                group.category = property.category
                group.put(property)
            }
            "Value" -> {
                shouldAdd = false
                property.addCallback {
                    updateColors()
                    return@addCallback false
                }
                group!!.put(property)
                newTree.put(group)
            }
        }
        property.subcategory = ConfigVisualizer.DEFAULT_SUBCATEGORY
        if (property != enabled) {
            @Suppress("UNCHECKED_CAST")
            enabled?.let { property.addDisplayCondition(it as Property<Boolean>, false) }
        }
        if (shouldAdd) newTree.put(property)
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

fun <K, V> createMap() = try {
    val clazz = Class.forName("androidx.compose.runtime.SnapshotStateKt")
    val method = clazz.getMethod("mutableStateMapOf")
    @Suppress("UNCHECKED_CAST")
    method.invoke(null) as androidx.compose.runtime.snapshots.SnapshotStateMap<K, V>
} catch (e: Exception) {
    e.printStackTrace()
    mutableMapOf()
}