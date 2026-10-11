package me.imtoggle.teamcolors.mixin.polynametag;

//? if > 1.8.9 && < 26.2 {
/*import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.data.TagComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//~ if < 1.21.10 'feature.NameTagFeatureRenderer' -> 'entity.EntityRenderer'
@Mixin(value = net.minecraft.client.renderer.feature.NameTagFeatureRenderer.class, priority = 1500)
public class RenderBackgroundShapeMixin {

    @Dynamic
    @TargetHandler(mixin = "org.polyfrost.polynametag.mixin.client.Mixin_RenderBackgroundShape", name = "polynametag$drawShapedBackground")
    @WrapOperation(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Lorg/polyfrost/polynametag/client/NametagRenderer;backgroundArgb()I"))
    private int setNametagColor(Operation<Integer> original, @Local Component text) {
        int bgColor = original.call();
        if (text instanceof TagComponent tagComponent) {
            return tagComponent.getNametagColor() | (bgColor & 0xFF000000);
        }
        return bgColor;
    }
}
*///? } else {
public class RenderBackgroundShapeMixin {}
//? }