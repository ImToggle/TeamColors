package me.imtoggle.teamcolors.mixin.compat;

import me.imtoggle.teamcolors.util.Util;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "org.polyfrost.polynametag.client.NametagRenderer")
public class PolyNametagMixin {

    @Dynamic("PolyNametag")
    @Inject(method = "backgroundArgb", at = @At("RETURN"), cancellable = true)
    private static void modifyColor(CallbackInfoReturnable<Integer> cir) {
        if (!Util.hasTeam) return;
        cir.setReturnValue((Util.teamColor & 0x00FFFFFF) | (cir.getReturnValue() & 0xFF000000));
    }

    @Dynamic("PolyNametag")
    @ModifyVariable(method = "backgroundColor", at = @At("STORE"), ordinal = 1)
    private static int modifyColor(int color) {
        if (!Util.hasTeam) return color;
        return (Util.teamColor & 0x00FFFFFF) | (color & 0xFF000000);
    }
}