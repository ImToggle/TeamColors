package me.imtoggle.teamcolors.mixin.polynametag;

//? if >= 26.3 {
import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.feature.TextFeatureRenderer;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = TextFeatureRenderer.class, priority = 1500)
public class RenderBackgroundShape263Mixin {

    @Dynamic
    @TargetHandler(mixin = "org.polyfrost.polynametag.mixin.client.Mixin_RenderBackgroundShape263", name = "polynametag$drawShapedBackground")
    @WrapOperation(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Lorg/polyfrost/polynametag/client/NametagRenderer;backgroundArgb()I"))
    private int setNametagColor(Operation<Integer> original, @Local TextFeatureRenderer.Submit submit) {
        int bgColor = original.call();
        if (submit.content() instanceof TextFeatureRenderer.Content.Text text) {
            return (text.backgroundColor() & 0x00FFFFFF) | (bgColor & 0xFF000000);
        }
        return bgColor;
    }

}
//? } else {
/*public class RenderBackgroundShape263Mixin {}
*///? }