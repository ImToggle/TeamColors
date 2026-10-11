package me.imtoggle.teamcolors.config

import org.polyfrost.oneconfig.api.config.v1.ValueFormatter
import org.polyfrost.oneconfig.api.config.v1.annotations.RadioButton
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider

class ConfigEntry {

    @RadioButton(
        title = "Mode",
        options = ["Multiplier", "Absolute"]
    )
    var mode = 0

    @Slider(
        title = " ",
        min = 0f, max = 100f,
        formatter = PercentageFormatter::class
    )
    var value = 100

    private class PercentageFormatter : ValueFormatter {
        override fun format(value: Number) = "$value%"
    }

}