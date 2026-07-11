package me.imtoggle.teamcolors.mixin.compat;

import me.imtoggle.teamcolors.util.Util;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Pseudo
@Mixin(targets = "org.polyfrost.polynametag.client.NametagRenderer")
public class PolyNametagRendererMixin {

    @Dynamic("PolyNametag")
    @ModifyVariable(method = "backgroundColor", at = @At("STORE"), ordinal = 1)
    private static int modifyColor(int color) {
        if (!Util.hasTeam) return color;
        return Util.tagColor | (color & 0xFF000000);
    }
}