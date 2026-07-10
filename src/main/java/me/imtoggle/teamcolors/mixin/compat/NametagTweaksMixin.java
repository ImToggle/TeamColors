package me.imtoggle.teamcolors.mixin.compat;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.imtoggle.teamcolors.util.Util;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >= 26.2 {
@Mixin(value = net.minecraft.client.renderer.SubmitNodeCollection.class, priority = 1500)
//? } elif >= 1.21.10 {
/*@Mixin(value = net.minecraft.client.renderer.feature.NameTagFeatureRenderer.Storage.class, priority = 1500)
*///? } else {
/*@Mixin(value = net.minecraft.client.renderer.entity.EntityRenderer.class, priority = 1500)
 *///? }
public class NametagTweaksMixin {

    @Dynamic("NametagTweaks")
    //? if >= 26.2 {
    @TargetHandler(mixin = "dev.microcontrollers.nametagtweaks.mixin.SubmitNodeCollectionMixin", name = "changeNametagBackgroundSeeThrough")
    //? } elif >=1.21.10 {
    /*@TargetHandler(mixin = "dev.microcontrollers.nametagtweaks.mixin.NametagFeatureRendererStorageMixin", name = "changeNametagBackgroundSeeThrough")
    *///? } else {
    /*@TargetHandler(mixin = "dev.microcontrollers.nametagtweaks.mixin.EntityRendererMixin", name = "changeNametagBackground")
    *///? }
    @ModifyReturnValue(method = "@MixinSquared:Handler", at = @At("RETURN"))
    private int nametagtweaks(int value) {
        if (!Util.hasTeam) return value;
        Util.hasTeam = false;
        return (Util.teamColor & 0x00FFFFFF) | (value & 0xFF000000);
    }
}