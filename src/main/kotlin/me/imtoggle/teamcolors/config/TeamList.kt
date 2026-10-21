package me.imtoggle.teamcolors.config

import org.polyfrost.oneconfig.api.config.v1.annotations.Option

@Option(display = TeamListVisualizer::class)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class TeamList(
    val title: String,
    val description: String,
    val category: String = "General",
    val subcategory: String = "General",
    val maxEntries: Int = 0,
    val reorderable: Boolean = false,
    val addText: String = "Choose teams",
)