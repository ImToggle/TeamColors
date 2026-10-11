package me.imtoggle.teamcolors.mixin.vanilla;

//? if >= 1.21.10 && < 26.2 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.imtoggle.teamcolors.data.TagComponent;
import me.imtoggle.teamcolors.util.RenderUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import net.minecraft.network.chat.Component;
//~ if < 26.1 'Matrix4fc' -> 'Matrix4f'
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = NameTagFeatureRenderer.class, priority = 1200)
public class NametagFeatureRendererMixin {

    //~ if < 26.1 'renderTranslucent' -> 'render'
    @WrapOperation(method = "render", at = @At(
            //~ if < 26.1 'Matrix4fc' -> 'Matrix4f'
            value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)V"
    ))
    //~ if < 26.1 'Matrix4fc' -> 'Matrix4f'
    private void setNametagColor(Font instance, Component component, float x, float y, int color, boolean b, Matrix4f pose, MultiBufferSource multiBufferSource, Font.DisplayMode displayMode, int backgroundColor, int lightCoords, Operation<Void> original) {
        RenderUtil.tagColor = null;
        if (component instanceof TagComponent tagComponent) {
            RenderUtil.tagColor = tagComponent.getNametagColor();
            original.call(instance, component, x, y, color, b, pose, multiBufferSource, displayMode, RenderUtil.tagColor | (backgroundColor & 0xFF000000), lightCoords);
            return;
        }
        original.call(instance, component, x, y, color, b, pose, multiBufferSource, displayMode, backgroundColor, lightCoords);
    }

}
*///? } else {
public class NametagFeatureRendererMixin {}
//? }