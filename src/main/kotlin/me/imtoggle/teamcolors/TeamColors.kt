package me.imtoggle.teamcolors

import me.imtoggle.teamcolors.config.ModConfig
import net.fabricmc.api.ClientModInitializer

class TeamColors : ClientModInitializer {

    override fun onInitializeClient() {
        ModConfig.preload()
    }
}