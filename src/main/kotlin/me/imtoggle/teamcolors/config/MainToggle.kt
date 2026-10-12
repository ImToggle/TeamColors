package me.imtoggle.teamcolors.config

import org.polyfrost.oneconfig.api.toggle.v1.ModToggle

class MainToggle : ModToggle {

    override fun isEnabled() = ModConfig.enabled

    override fun setEnabled(enabled: Boolean) {
        ModConfig.enabled = enabled
    }

}