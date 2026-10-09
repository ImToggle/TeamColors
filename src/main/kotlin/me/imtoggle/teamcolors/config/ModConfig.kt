package me.imtoggle.teamcolors.config

import me.imtoggle.teamcolors.util.capitalize
import me.imtoggle.teamcolors.util.toTitleCase
import me.imtoggle.teamcolors.util.updateCategory
import me.imtoggle.teamcolors.util.updateColorMap
import me.imtoggle.teamcolors.util.updateIndividual
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.oneconfig.api.config.v1.collect.impl.OneConfigCollector
import org.polyfrost.oneconfig.api.config.v1.dsl.category

object ModConfig : Config("teamcolors.json", "/assets/teamcolors/icon.png", "TeamColors", Category.VISUALS) {

    val CATEGORIES = listOf("hitbox", "nametag")

    var categoriesConfig = CATEGORIES.map { CategoryConfig(it) }

    private fun handleGroup(
        tree: Tree, collector: OneConfigCollector, enabled: Property<Boolean>,
        id: String, title: String = id, src: Any,
        callBack: (parent: Tree) -> Unit
    ) {
        Tree.tree(id.lowercase()).run {
            addMetadata(mapOf(
                "title" to title,
                "collapsed" to false
            ))
            collector.handle(this, src, 0)
            val previewState = tree.getProp("preview").getAs<PreviewVisualizer.PreviewState>()
            val vanillaColor = if (src is ColorConfig) {
                src.vanillaColor
            } else {
                null
            }
            onAllProps { _, property ->
                enabled.addCallback {
                    property.revaluateDisplay()
                    return@addCallback false
                }
                property.addDisplayCondition {
                    if (previewState.currentColor == vanillaColor) {
                        if (enabled.get() == true) {
                            Property.Display.SHOWN
                        } else {
                            Property.Display.DISABLED
                        }
                    } else {
                        Property.Display.HIDDEN
                    }
                }
                property.addCallback {
                    callBack(this)
                    return@addCallback false
                }
            }
            tree.put(this)
        }
    }

    override fun makeTree(): Tree? {
        val tree = super.makeTree()
        val collector = OneConfigCollector()
        categoriesConfig.forEach { category ->
            val t = Tree.tree(category.name)
            collector.handle(t, category, 0)
            val enabled = t.getProp("enabled") as Property<Boolean>
            category.global.forEach { (id, entry) ->
                handleGroup(t, collector, enabled, id, id.capitalize(), entry) {
                    updateCategory(category.id)
                }
            }
            category.individual.forEach { (id, entry) ->
                handleGroup(t, collector, enabled, id, id.toTitleCase(), entry) { parent ->
                    updateIndividual(category.id, entry.vanillaColor, parent)
                }
            }
            t.onAll { id, node ->
                if (node is Property<*> && node != enabled) {
                    node.addDisplayCondition(enabled, false)
                    enabled.addCallback {
                        node.revaluateDisplay()
                        return@addCallback false
                    }
                }
                node.id = null
                node.id = "${category.id}_${id}"
                node.category = category.name
                tree.put(node)
            }
        }
        return tree
    }

    override fun initialize(byConfigManager: Boolean) {
        super.initialize(byConfigManager)
        updateColorMap()
        CATEGORIES.forEach { category ->
            tree.getProp("${category}_preview").getAs<PreviewVisualizer.PreviewState>().let {
                it.currentColor = null
            }
        }
        tree.onAll { _, node ->
            if (node is Tree) {
                node.onAllProps { _, property ->
                    property.revaluateDisplay()
                }
            }
        }
    }

}