package me.imtoggle.teamcolors

import me.imtoggle.teamcolors.config.ModConfig
import me.imtoggle.teamcolors.util.settings
import me.imtoggle.teamcolors.util.updateColors
import me.imtoggle.teamcolors.util.vanillaColors
import net.fabricmc.api.ClientModInitializer

class TeamColorsMod : ClientModInitializer {

    override fun onInitializeClient() {
        vanillaColors
        ModConfig.CONFIG.load()
        settings = ModConfig.CONFIG.instance().config.clone()
        updateColors()
    }
}