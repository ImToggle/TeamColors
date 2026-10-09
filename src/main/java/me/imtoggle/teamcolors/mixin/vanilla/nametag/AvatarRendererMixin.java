package me.imtoggle.teamcolors.mixin.vanilla.nametag;

//? if <= 1.21.11 {
/*import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.util.NametagPackager;
import me.imtoggle.teamcolors.util.Util;import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
    private void packNametags(CallbackInfo ci, @Local(argsOnly = true) Avatar avatar, @Local(argsOnly = true) AvatarRenderState state) {
        if (Util.isDisabled("nametag")) return;
        state.scoreText = NametagPackager.pack(state.scoreText, avatar);
    }

}
*///? }