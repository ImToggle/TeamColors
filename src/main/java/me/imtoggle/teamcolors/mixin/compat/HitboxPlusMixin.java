package me.imtoggle.teamcolors.mixin.compat;

import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.world.scores.Team;
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
    private static void modifySB(CallbackInfoReturnable<Integer> cir, @Local Team team) {
        if (!Util.isHitboxEnabled() || cir.getReturnValue() == null) return;
        cir.setReturnValue(Util.getColorEntry(Util.getTeamColor(team)).getHitboxColor());
    }

}