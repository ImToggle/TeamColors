package me.imtoggle.teamcolors.mixin.compat;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.util.TagComponent;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = /*? if >= 1.21.10 {*/ "net.minecraft.client.renderer.feature.NameTagFeatureRenderer" /*?} else {*/ /*"net.minecraft.client.renderer.entity.EntityRenderer" *//*?}*/, priority = 1500)
public class PolyNametagMixin {

    @Dynamic("PolyNametag")
    @TargetHandler(mixin = "org.polyfrost.polynametag.mixin.client.Mixin_RenderBackgroundShape", name = "polynametag$drawShapedBackground")
    @WrapOperation(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Lorg/polyfrost/polynametag/client/NametagRenderer;backgroundArgb()I"))
    private int applyColor(Operation<Integer> original, @Local(argsOnly = true) Component text) {
        int color = original.call();
        if (text instanceof TagComponent tagComponent) {
            color = (Util.getNametagColor(tagComponent.getTeamColor()) & 0x00FFFFFF) | (color & 0xFF000000);
        }
        return color;
    }

}