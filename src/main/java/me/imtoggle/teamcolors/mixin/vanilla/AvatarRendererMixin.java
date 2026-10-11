package me.imtoggle.teamcolors.mixin.vanilla;

//? if >= 1.21.10 && <= 1.21.11 {
/*import me.imtoggle.teamcolors.config.ModConfig;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
    private void packNametags(Avatar avatar, AvatarRenderState state, float f, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.getNametagCategory().getEnabled()) return;
        state.scoreText = Util.pack(state.scoreText, avatar);
    }

}
*///? } else {
public class AvatarRendererMixin {}
//? }