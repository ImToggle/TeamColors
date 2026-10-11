package me.imtoggle.teamcolors.mixin.polynametag;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.imtoggle.teamcolors.util.RenderUtil;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NametagRenderer.class)
public class NametagRendererMixin {

    @ModifyReturnValue(method = "backgroundColor", at = @At("RETURN"))
    private static int setNametagColor(int original) {
        return RenderUtil.tagColor != null ? (RenderUtil.tagColor & 0x00FFFFFF) | (original & 0xFF000000) : original;
    }

}
