package me.imtoggle.teamcolors.compat

import me.imtoggle.teamcolors.util.alphaMask
import me.imtoggle.teamcolors.util.getHitboxColor
import me.imtoggle.teamcolors.util.hasTeamColor
import me.imtoggle.teamcolors.util.isHitboxEnabled
import org.polyfrost.polyhitbox.api.HitboxColors
import org.polyfrost.polyhitbox.api.HitboxCondition
import org.polyfrost.polyhitbox.api.HitboxElement

object PolyHitboxCompat {

    fun initialize() {
        HitboxColors.register { context, argb ->
            val entity = context.entity
            if (context.element != HitboxElement.OUTLINE && context.element != HitboxElement.SIDE) return@register argb
            if (context.has(HitboxCondition.HOVERED)) return@register argb
            if (context.has(HitboxCondition.IFRAME)) return@register argb
            if (!isHitboxEnabled || !entity.hasTeamColor()) return@register argb
            return@register getHitboxColor(entity) or argb.alphaMask()
        }
    }

}