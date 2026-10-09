package me.imtoggle.teamcolors.mixin.vanilla;

//? if < 26.2 {
/*import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.data.TagComponent;
import me.imtoggle.teamcolors.util.RenderUtil;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(NameTagFeatureRenderer.Storage.class)
public class NametagFeatureRendererMixin {

    //~ if < 26.1 'backgroundColor' -> 'k'
    @ModifyVariable(method = "add", at = @At(value = "STORE"), name = "backgroundColor")
    private int setNametagColor(int backgroundColor, @Local(argsOnly = true) Component name) {
        RenderUtil.tagColor = null;
        if (name instanceof TagComponent tagComponent) {
            RenderUtil.tagColor = tagComponent.getNametagColor();
            return RenderUtil.tagColor | (backgroundColor & 0xFF000000);
        }
        return backgroundColor;
    }

}
*///? }