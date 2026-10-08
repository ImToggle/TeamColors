package me.imtoggle.teamcolors.config

import me.imtoggle.teamcolors.util.toTitleCase
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.oneconfig.api.config.v1.collect.impl.OneConfigCollector
import org.polyfrost.oneconfig.api.config.v1.dsl.category

object ModConfig : Config("teamcolors.json", "/assets/teamcolors/icon.png", "TeamColors", Category.VISUALS) {

    var categories = listOf(
        CategoryConfig("Hitbox"),
        CategoryConfig("Nametag")
    )

    override fun makeTree(): Tree? {
        val tree = super.makeTree()
        val collector = OneConfigCollector()

        categories.forEach { category ->
            val categoryID = category.name.lowercase()
            val t = Tree.tree(categoryID)
            collector.handle(t, category, 0)
            val preview = t.getProp("preview").getAs<PreviewVisualizer.PreviewState>()
            category.global.forEach { (name, entry) ->
                Tree.tree(name.lowercase()).run {
                    addMetadata(mapOf(
                        "title" to name,
                        "collapsed" to false
                    ))
                    collector.handle(this, entry, 0)
                    onAllProps { _, property ->
                        property.addDisplayCondition {
                            if (preview.currentColor == null) {
                                Property.Display.SHOWN
                            } else {
                                Property.Display.HIDDEN
                            }
                        }
                    }
                    t.put(this)
                }
            }
            category.individual.forEach { (id, entry) ->
                Tree.tree(id.lowercase()).run {
                    addMetadata(mapOf(
                        "title" to id.toTitleCase(),
                        "collapsed" to false
                    ))
                    collector.handle(this, entry, 0)
                    onAllProps { _, property ->
                        property.addDisplayCondition {
                            if (preview.currentColor == entry.vanillaColor) {
                                Property.Display.SHOWN
                            } else {
                                Property.Display.HIDDEN
                            }
                        }
                    }
                    t.put(this)
                }
            }
            t.onAll { id, node ->
                node.id = null
                node.id = "${categoryID}_${id}"
                node.category = category.name
                tree.put(node)
            }
        }
        return tree
    }
}