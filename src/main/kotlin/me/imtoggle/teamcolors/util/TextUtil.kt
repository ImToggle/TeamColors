package me.imtoggle.teamcolors.util

fun String.capitalize() = replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

fun String.toTitleCase() = split("_").joinToString(" ") { word ->
    word.lowercase().capitalize()
}