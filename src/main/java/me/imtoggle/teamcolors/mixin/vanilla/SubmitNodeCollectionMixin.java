package me.imtoggle.teamcolors.mixin.vanilla;

//? if >= 26.2 {
import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.data.TagComponent;
import me.imtoggle.teamcolors.util.RenderUtil;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SubmitNodeCollection.class)
public class SubmitNodeCollectionMixin {

    @ModifyVariable(method = "submitNameTag", at = @At("STORE"), name = "backgroundColor")
    private int setNametagColor(int backgroundColor, @Local(argsOnly = true, name = "name") Component name) {
        RenderUtil.tagColor = null;
        if (name instanceof TagComponent tagComponent) {
            RenderUtil.tagColor = tagComponent.getNametagColor();
            return RenderUtil.tagColor | (backgroundColor & 0xFF000000);
        }
        return backgroundColor;
    }

}
//? }