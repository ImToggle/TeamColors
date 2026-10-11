package me.imtoggle.teamcolors.mixin.vanilla;

//? if >= 26.2 {
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.imtoggle.teamcolors.data.TagComponent;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if = 26.2 {
/*import me.imtoggle.teamcolors.hook.TagColorHook;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import org.joml.Matrix4fc;
*///? } else {
import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.util.RenderUtil;
import net.minecraft.client.renderer.feature.TextFeatureRenderer;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;
//? }

@Mixin(SubmitNodeCollection.class)
public class SubmitNodeCollectionMixin {

    //? if >= 26.3 {
    @WrapOperation(method = "submitNameTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollection;nameTag(Lorg/joml/Matrix4f;FFLnet/minecraft/util/FormattedCharSequence;IIILnet/minecraft/client/gui/Font$DisplayMode;)Lnet/minecraft/client/renderer/feature/TextFeatureRenderer$Submit;"))
    private TextFeatureRenderer.Submit setNametagColor(Matrix4f matrix4f, float x, float y, FormattedCharSequence formattedCharSequence, int lightCoords, int color, int backgroundColor, Font.DisplayMode displayMode, Operation<TextFeatureRenderer.Submit> original, @Local(argsOnly = true, name = "name") Component name) {
        RenderUtil.tagColor = null;
        if (name instanceof TagComponent tagComponent) {
            RenderUtil.tagColor = tagComponent.getNametagColor();
            int bgColor = RenderUtil.tagColor | (backgroundColor & 0xFF000000);
            return original.call(matrix4f, x, y, formattedCharSequence, lightCoords, color, bgColor, displayMode);
        }
        return original.call(matrix4f, x, y, formattedCharSequence, lightCoords, color, backgroundColor, displayMode);
    }
    //? } else {
    /*@WrapOperation(method = "submitNameTag", at = @At(value = "NEW", target = "(Lorg/joml/Matrix4fc;FFLnet/minecraft/network/chat/Component;IIILnet/minecraft/client/gui/Font$DisplayMode;)Lnet/minecraft/client/renderer/feature/NameTagFeatureRenderer$Submit;"))
    private NameTagFeatureRenderer.Submit setNametagColor(Matrix4fc matrix4fc, float x, float y, Component text, int lightCoords, int color, int backgroundColor, Font.DisplayMode displayMode, Operation<NameTagFeatureRenderer.Submit> original) {
        if (text instanceof TagComponent tagComponent) {
            int bgColor = tagComponent.getNametagColor() | (backgroundColor & 0xFF000000);
            NameTagFeatureRenderer.Submit submit = original.call(matrix4fc, x, y, text, lightCoords, color, bgColor, displayMode);
            if (Util.hasPolyNametag) ((TagColorHook) (Object) submit).teamcolors$setColor(bgColor);
            return submit;
        }
        return original.call(matrix4fc, x, y, text, lightCoords, color, backgroundColor, displayMode);
    }
    *///? }

}
//? } else {
/*public class SubmitNodeCollectionMixin {}
*///? }