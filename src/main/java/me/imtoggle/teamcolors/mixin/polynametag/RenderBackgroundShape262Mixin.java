package me.imtoggle.teamcolors.mixin.polynametag;

//? if = 26.2 {
/*import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = NameTagFeatureRenderer.class, priority = 1500)
public class RenderBackgroundShape262Mixin {

    @Dynamic
    @TargetHandler(mixin = "org.polyfrost.polynametag.mixin.client.Mixin_RenderBackgroundShape262", name = "polynametag$drawShapedBackground")
    @ModifyVariable(method = "@MixinSquared:Handler", at = @At(value = "LOAD"), name = "argb")
    private int setNametagColor(int original, @Local NameTagFeatureRenderer.Submit submit) {
        return (submit.backgroundColor() & 0x00FFFFFF) | (original & 0xFF000000);
    }
}
*///? } else {
public class RenderBackgroundShape262Mixin {}
//? }