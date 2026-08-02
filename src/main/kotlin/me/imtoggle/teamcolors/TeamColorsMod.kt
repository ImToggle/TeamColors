package me.imtoggle.teamcolors

import me.imtoggle.teamcolors.config.ModConfig
import me.imtoggle.teamcolors.util.colorMap
import me.imtoggle.teamcolors.util.createMap
import me.imtoggle.teamcolors.util.updateColors
import me.imtoggle.teamcolors.util.vanillaColors
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.loader.api.FabricLoader

class TeamColorsMod : ClientModInitializer {

    override fun onInitializeClient() {
        if (FabricLoader.getInstance().isModLoaded("oneconfig")) {
            colorMap = createMap()
        }
        vanillaColors
        ModConfig.CONFIG.load()
        updateColors()
    }
}