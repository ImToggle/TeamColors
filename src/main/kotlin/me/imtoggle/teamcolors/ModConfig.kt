package me.imtoggle.teamcolors

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.resources.Identifier

class ModConfig {

    companion object {
        val CONFIG = ConfigClassHandler.createBuilder(ModConfig::class.java)
            .id(Identifier.fromNamespaceAndPath("teamcolors", "config"))
            .serializer { config -> GsonConfigSerializerBuilder.create(config)
                .setPath(FabricLoader.getInstance().configDir.resolve("teamcolors.json"))
                .build()
            }
            .build()
    }

    @SerialEntry var config = Config()

}