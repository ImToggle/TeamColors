package me.imtoggle.teamcolors.config

import dev.isxander.yacl3.api.Controller
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.utils.Dimension
import dev.isxander.yacl3.gui.AbstractWidget
import dev.isxander.yacl3.gui.YACLScreen
import dev.isxander.yacl3.gui.controllers.ControllerWidget
import me.imtoggle.teamcolors.util.ConfigEntry
import me.imtoggle.teamcolors.util.getHitboxColor
import me.imtoggle.teamcolors.util.getNametagColor
import me.imtoggle.teamcolors.util.settings
import me.imtoggle.teamcolors.util.vanillaColors
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.core.SectionPos.y

class PreviewController(private val option: Option<Boolean>) : Controller<Boolean> {

    override fun option() = option

    override fun formatValue() = null

    override fun provideWidget(screen: YACLScreen, widgetDimension: Dimension<Int>): AbstractWidget {
        return ColorPreviewElement(this, screen, widgetDimension)
    }

    class ColorPreviewElement(controller: PreviewController, screen: YACLScreen, dim: Dimension<Int>) : ControllerWidget<PreviewController>(controller, screen, dim) {

        val isHitbox = control.option.pendingValue()

        val entry = if (isHitbox) {
            settings.hitbox
        } else {
            settings.nametag
        }

        init {
            dimension = dim.expanded(0, dimension.height())
        }

        override fun canReset(): Boolean {
            return false
        }

        override fun getHoveredControlWidth(): Int {
            return unhoveredControlWidth
        }

        override fun
                //? if >=26.1 {
                extractRenderState
                //? } else {
                /*render
                *///? }
                    (graphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, delta: Float) {
            val enabled = entry.enabled
            drawButtonRect(graphics, dimension.x(), dimension.y(), dimension.xLimit(), dimension.yLimit(), enabled, enabled)
            val width = (dimension.width() - 4) / 16
            var extra = (dimension.width() - 4) % 16
            var x = dimension.x() + 2
            val y1 = dimension.y() + dimension.height() / 2
            for (color in vanillaColors) {
                val w = if (extra > 0) width + 1 else width
                graphics.fill(x, dimension.y() + 2, x + w, y1, (color and 0x00FFFFFF) or 0xFF000000.toInt())
                graphics.fill(x, y1, x + w, dimension.yLimit() - 2, if (isHitbox) getHitboxColor(color) else getNametagColor(color))
                x += w
                extra--
            }
            if (!enabled) {
               graphics.fill(dimension.x() + 2, dimension.y() + 2, dimension.xLimit() - 2, dimension.yLimit() - 2, 0x80000000.toInt())
            }
        }

        //? if >=26.1 {
        override fun extractValueText(graphics: GuiGraphicsExtractor?, mouseX: Int, mouseY: Int, a: Float) {
        }
        //? }

    }
}