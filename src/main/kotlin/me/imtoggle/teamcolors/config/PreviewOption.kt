package me.imtoggle.teamcolors.config

import org.polyfrost.oneconfig.api.config.v1.annotations.Option

@Option(display = PreviewVisualizer::class)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class PreviewOption(
    val title: String = "Color Preview"
)