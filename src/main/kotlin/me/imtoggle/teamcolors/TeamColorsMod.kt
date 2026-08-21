package me.imtoggle.teamcolors

import me.imtoggle.teamcolors.compat.PolyHitboxCompat
import me.imtoggle.teamcolors.config.ModConfig
import me.imtoggle.teamcolors.util.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.loader.api.FabricLoader

class TeamColorsMod : ClientModInitializer {

    override fun onInitializeClient() {
        if (FabricLoader.getInstance().isModLoaded("oneconfig")) {
            colorMap = createMap()
        }
        if (FabricLoader.getInstance().isModLoaded("polyhitbox")) {
            PolyHitboxCompat.initialize()
        }
        vanillaColors
        ModConfig.CONFIG.load()
        updateColors()
    }
}