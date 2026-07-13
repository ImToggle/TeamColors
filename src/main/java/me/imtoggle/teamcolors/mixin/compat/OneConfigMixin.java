package me.imtoggle.teamcolors.mixin.compat;

import dev.isxander.yacl3.impl.YetAnotherConfigLibImpl;
import me.imtoggle.teamcolors.util.OneConfigHandler;
import me.imtoggle.teamcolors.util.Util;
import org.polyfrost.oneconfig.api.config.v1.Tree;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "org.polyfrost.oneconfig.internal.compat.YACLCompat")
public class OneConfigMixin {

    @Dynamic("OneConfig")
    @Inject(method = "parseYACLInstance", at = @At("RETURN"), cancellable = true)
    private void modifyTree(Object instance, @Coerce Object mod, CallbackInfoReturnable<Tree> cir) {
        if (instance instanceof YetAnotherConfigLibImpl yacl) {
            if (yacl != Util.getCurrentInstance()) return;
            cir.setReturnValue(OneConfigHandler.handleTree(cir.getReturnValue()));
        }
    }

}