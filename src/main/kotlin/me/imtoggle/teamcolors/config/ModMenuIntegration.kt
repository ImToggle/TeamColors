package me.imtoggle.teamcolors.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionGroup
import dev.isxander.yacl3.api.StateManager
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder
import me.imtoggle.teamcolors.util.ConfigEntry
import me.imtoggle.teamcolors.util.Entry
import me.imtoggle.teamcolors.util.currentInstance
import me.imtoggle.teamcolors.util.updateColors
import net.minecraft.network.chat.Component

class ModMenuIntegration : ModMenuApi {

    fun ConfigCategory.Builder.buildGroup(name: String, default: Entry, current: Entry): ConfigCategory.Builder {
        return this.group(OptionGroup.createBuilder()
            .name(Component.literal(name))
            .option(Option.createBuilder<Boolean>()
                .name(Component.literal("Mode"))
                .stateManager(StateManager.createInstant(
                    default.mode,
                    { current.mode },
                    { value -> current.mode = value }
                ))
                .controller { option -> BooleanControllerBuilder.create(option)
                    .formatValue { value -> Component.literal(if (value) "Absolute" else "Multiplier") }
                }
                .addListener { _, _ -> updateColors() }
                .build()
            )
            .option(Option.createBuilder<Int>()
                .name(Component.literal("Value"))
                .stateManager(StateManager.createInstant(
                    default.value,
                    { current.value },
                    { value -> current.value = value }
                ))
                .controller { option -> IntegerSliderControllerBuilder.create(option)
                    .range(0, 100)
                    .step(1)
                    .formatValue { value -> Component.literal("%d%%".format(value)) }
                }
                .addListener { _, _ -> updateColors() }
                .build()
            )
            .build()
        )
    }

    fun YetAnotherConfigLib.Builder.buildCategory(category: String, default: ConfigEntry, current: ConfigEntry): YetAnotherConfigLib.Builder {
        val isHitbox = category == "Hitbox"
        return this.category(ConfigCategory.createBuilder()
            .name(Component.literal(category))
            .option(Option.createBuilder<Boolean>()
                .name(Component.literal("Enabled"))
                .stateManager(StateManager.createInstant(
                    default.enabled,
                    { current.enabled },
                    { value -> current.enabled = value }
                ))
                .controller { option -> BooleanControllerBuilder.create(option) }
                .build()
            )
            .option(Option.createBuilder<Boolean>()
                .name(Component.literal("Color Preview"))
                .customController { option -> PreviewController(option) }
                .stateManager(StateManager.createInstant(isHitbox, { isHitbox }, {}))
                .build()
            )
            .buildGroup("Saturation", default.saturation, current.saturation)
            .buildGroup("Brightness", default.brightness, current.brightness)
            .build()
        )
    }

    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parentScreen ->
            val yacl = YetAnotherConfigLib.create(ModConfig.CONFIG) { default, current, builder -> builder
                .title(Component.literal("TeamColors"))
                .buildCategory("Hitbox", default.config.hitbox, current.config.hitbox)
                .buildCategory("Nametag", default.config.nametag, current.config.nametag)
            }
            currentInstance = yacl
            return@ConfigScreenFactory yacl.generateScreen(parentScreen)
        }
    }
}