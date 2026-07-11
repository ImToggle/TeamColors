package me.imtoggle.teamcolors.mixin.compat;

import me.imtoggle.teamcolors.util.Util;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "io.github.pingisfun.hitboxplus.runtime.RuntimeHitboxLookup")
public class HitboxPlusMixin {

    @Dynamic("HitBoxPlus")
    @Inject(method = "teamColor", at = @At("RETURN"), cancellable = true)
    private static void modifySB(CallbackInfoReturnable<Integer> cir) {
        if (!Util.isHitboxEnabled() || cir.getReturnValue() == null) return;
        cir.setReturnValue(Util.getHitboxColor(cir.getReturnValue()));
    }

}