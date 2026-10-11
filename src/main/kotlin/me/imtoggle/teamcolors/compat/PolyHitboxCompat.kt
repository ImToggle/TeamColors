package me.imtoggle.teamcolors.compat

import me.imtoggle.teamcolors.config.ModConfig
import me.imtoggle.teamcolors.util.getColorEntry
import me.imtoggle.teamcolors.util.hasTeamColor
import org.polyfrost.polyhitbox.api.HitboxColors
import org.polyfrost.polyhitbox.api.HitboxElement

object PolyHitboxCompat {

    fun initialize() {
        HitboxColors.register { context, original ->
            if (!ModConfig.hitboxCategory.enabled) return@register original
            if (context.element != HitboxElement.OUTLINE && context.element != HitboxElement.SIDE) return@register original
            if (!context.entity.hasTeamColor()) return@register original
            val colorEntry = context.entity.getColorEntry() ?: return@register original
            return@register colorEntry.hitboxColor.argb or (original and 0xFF000000.toInt())
        }
    }

}