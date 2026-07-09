package me.imtoggle.teamcolors

import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory

class TemplateMod : ClientModInitializer {

    val LOGGER = LoggerFactory.getLogger("template")

    override fun onInitializeClient() {
        vanillaColors
        ModConfig.CONFIG.load()
        settings = ModConfig.CONFIG.instance().config.clone()
        updateColors()
    }
}