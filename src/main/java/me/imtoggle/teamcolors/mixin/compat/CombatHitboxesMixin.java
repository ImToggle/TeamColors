package me.imtoggle.teamcolors.mixin.compat;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.awt.*;

//? if >= 1.21.11 {
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.objectweb.asm.Opcodes;

@Mixin(value = net.minecraft.client.renderer.debug.EntityHitboxDebugRenderer.class, priority = 1500)
//? } elif >= 1.21.10 {
/*@Mixin(value = net.minecraft.client.renderer.feature.HitboxFeatureRenderer.class, priority = 1500)
*///? } else {
/*@Mixin(value = net.minecraft.client.renderer.entity.EntityRenderDispatcher.class)
*///? }
public class CombatHitboxesMixin {

    @Dynamic("CombatHitboxes")
    //? if >= 1.21.11 {
    @TargetHandler(mixin = "me.sootysplash.box.mixin.HitBoxRenderMixin", name = "onDrawHitbox")
    @WrapOperation(method = "@MixinSquared:Handler", at = @At(value = "FIELD", target = "Lme/sootysplash/box/Config;hitBoxColor:I", opcode = Opcodes.GETFIELD))
    private int applyColor(@Coerce Object instance, Operation<Integer> original, @Local(ordinal = 0, argsOnly = true) Entity entity) {
        int color = original.call(instance);
        if (!Util.isHitboxEnabled() || !Util.hasTeamColor(entity)) return color;
        return (Util.getHitboxColor(entity) & 0x00FFFFFF) | (color & 0xFF000000);
    }
    //? } else {
    /*@TargetHandler(mixin = "me.sootysplash.box.mixin.HitBoxRenderMixin", name = "renderBox")
    @org.spongepowered.asm.mixin.injection.ModifyVariable(method = "@MixinSquared:Handler", at = @At(value = "HEAD"), ordinal = 2, argsOnly = true)
    private static Color apply(Color color, @Local Entity entity) {
        if (!Util.isHitboxEnabled() || !Util.hasTeamColor(entity)) return color;
        return new Color((Util.getHitboxColor(entity) & 0x00FFFFFF) | (color.getRGB() & 0xFF000000));
    }
    *///? }
}
