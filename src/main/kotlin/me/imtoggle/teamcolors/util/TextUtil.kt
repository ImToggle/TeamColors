package me.imtoggle.teamcolors.util

fun String.toTitleCase() = split("_").joinToString(" ") { word ->
    word.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}