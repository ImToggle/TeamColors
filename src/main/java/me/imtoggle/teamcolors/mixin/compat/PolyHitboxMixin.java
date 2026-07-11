package me.imtoggle.teamcolors.mixin.compat;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.world.entity.Entity;
import org.polyfrost.compose.render.PolyColor;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;

@Pseudo
@Mixin(targets = "org.polyfrost.polyhitbox.render.HitboxRenderer")
public class PolyHitboxMixin {

    @Dynamic("PolyHitbox")
    @WrapOperation(
            //? if >= 1.21.11 {
            method = "emit",
            //? } else {
            /*method = "draw",
             *///? }
            at = @At(value = "INVOKE", target = "Lorg/polyfrost/polyhitbox/config/HitboxConfig;getOutlineColor()Lorg/polyfrost/compose/render/PolyColor;"))
    private PolyColor modifyColor(@Coerce Object instance, Operation<PolyColor> original, @Local Entity entity) {
        PolyColor color = original.call(instance);
        if (!Util.isHitboxEnabled() || !Util.hasTeamColor(entity)) return color;
        return new PolyColor((Util.getHitboxColor(entity) & 0x00FFFFFF) | (color.getRawArgb() & 0xFF000000));
    }
}