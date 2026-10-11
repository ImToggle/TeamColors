package me.imtoggle.teamcolors.mixin.polynametag;

//? if = 26.2 {
/*import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.hook.TagColorHook;
import me.imtoggle.teamcolors.util.RenderUtil;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NameTagFeatureRenderer.class, priority = 1500)
public class WrapNametagRender262Mixin {

    @Inject(method = "buildGroup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/feature/NameTagFeatureRenderer;prepareText(Lnet/minecraft/client/gui/Font;Lnet/minecraft/client/renderer/feature/NameTagFeatureRenderer$Submit;)Lnet/minecraft/client/gui/Font$PreparedText;"))
    private void setTagColor(CallbackInfo ci, @Local NameTagFeatureRenderer.Submit submit) {
        RenderUtil.tagColor = ((TagColorHook) (Object) submit).teamcolors$getColor();
    }

}
*///? } else {
public class WrapNametagRender262Mixin {}
//? }