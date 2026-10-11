package me.imtoggle.teamcolors

import me.imtoggle.teamcolors.compat.PolyHitboxCompat
import me.imtoggle.teamcolors.config.ModConfig
import me.imtoggle.teamcolors.util.hasPolyNametag
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.loader.api.FabricLoader

class TeamColors : ClientModInitializer {

    override fun onInitializeClient() {
        ModConfig.preload()
        hasPolyNametag = FabricLoader.getInstance().isModLoaded("polynametag")
        if (FabricLoader.getInstance().isModLoaded("polyhitbox")) {
            PolyHitboxCompat.initialize()
        }
    }

}