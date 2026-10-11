package me.imtoggle.teamcolors.mixin.vanilla;

//? if > 1.21.1 && <= 1.21.8 {
/*import me.imtoggle.teamcolors.config.ModConfig;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(
            method = "extractRenderState(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;F)V",
            at = @At("TAIL")
    )
    private void packNametags(AbstractClientPlayer abstractClientPlayer, PlayerRenderState state, float f, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.getNametagCategory().getEnabled()) return;
        state.scoreText = Util.pack(state.scoreText, abstractClientPlayer);
    }
}
*///? } else {
public class PlayerRendererMixin {}
//? }