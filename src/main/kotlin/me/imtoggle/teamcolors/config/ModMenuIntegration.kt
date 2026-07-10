package me.imtoggle.teamcolors.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionGroup
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder
import me.imtoggle.teamcolors.util.ConfigEntry
import me.imtoggle.teamcolors.util.Entry
import me.imtoggle.teamcolors.util.settings
import me.imtoggle.teamcolors.util.updateColors
import net.minecraft.network.chat.Component


class ModMenuIntegration : ModMenuApi {

    fun ConfigCategory.Builder.buildGroup(name: String, entries: List<Entry>): ConfigCategory.Builder {
        val (settings, default, current) = entries
        return this.group(OptionGroup.createBuilder()
            .name(Component.literal(name))
            .option(Option.createBuilder<Boolean>()
                .name(Component.literal("Mode"))
                .binding(settings.mode, { current.mode }, { value -> current.mode = value })
                .controller { option -> BooleanControllerBuilder.create(option)
                    .formatValue { value -> Component.literal(if (value) "Absolute" else "Multiplier") }
                }
                .addListener { option, _ ->
                    settings.mode = option.pendingValue()
                    updateColors()
                }
                .build()
            )
            .option(Option.createBuilder<Int>()
                .name(Component.literal("Value"))
                .binding(default.value,{ current.value }, { value -> current.value = value })
                .controller { option -> IntegerSliderControllerBuilder.create(option)
                    .range(0, 100)
                    .step(1)
                    .formatValue { value -> Component.literal("%d%%".format(value)) }
                }
                .addListener { option, _ ->
                    settings.value = option.pendingValue()
                    updateColors()
                }
                .build()
            )
            .build()
        )
    }

    fun YetAnotherConfigLib.Builder.buildCategory(category: String, entries: List<ConfigEntry>): YetAnotherConfigLib.Builder {
        val isHitbox = category == "Hitbox"
        return this.category(ConfigCategory.createBuilder()
            .name(Component.literal(category))
            .option(Option.createBuilder<Boolean>()
                .name(Component.literal("Color Preview"))
                .customController { option -> PreviewController(option) }
                .binding(isHitbox, { isHitbox }, {})
                .build()
            )
            .buildGroup("Saturation", entries.map { it.saturation })
            .buildGroup("Brightness", entries.map { it.brightness })
            .build()
        )
    }

    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parentScreen ->
            YetAnotherConfigLib.create(ModConfig.CONFIG) { default, current, builder ->
                val entries = arrayOf(settings, default.config, current.config)
                builder
                .title(Component.literal("TeamColors"))
                .buildCategory("Hitbox", entries.map { it.hitbox })
                .buildCategory("Nametag", entries.map { it.nametag })
            }.generateScreen(parentScreen)
        }
    }
}