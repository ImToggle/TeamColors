package me.imtoggle.teamcolors.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.util.TagComponent;
import me.imtoggle.teamcolors.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >= 26.2 {
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(net.minecraft.client.renderer.SubmitNodeCollection.class)
//? } else {
/*import org.spongepowered.asm.mixin.injection.ModifyVariable;
//? if >= 1.21.10 {
@Mixin(net.minecraft.client.renderer.feature.NameTagFeatureRenderer.Storage.class)
//? } else {
/^@Mixin(net.minecraft.client.renderer.entity.EntityRenderer.class)
 ^///? }
*///? }
public class NametagMixin {

    /* Capture Color */

    //? if >= 26.2 {
    @Inject(method = "submitNameTag", at = @At("HEAD"))
    //? } elif >= 1.21.10 {
    /*@Inject(method = "add", at = @At("HEAD"))
    *///? } else {
    /*@Inject(method = "renderNameTag", at = @At("HEAD"))
    *///? }
    private void captureColor(CallbackInfo ci, @Local(argsOnly = true) Component name) {
        Util.hasTeam = name instanceof TagComponent;
        if (Util.hasTeam) {
            Util.tagColor = ((TagComponent) name).getNametagColor();
        }
    }

    //? if >= 26.2 {
    @Inject(method = "submitNameTag", at = @At("TAIL"))
    //? } elif >= 1.21.10 {
    /*@Inject(method = "add", at = @At("TAIL"))
     *///? } else {
    /*@Inject(method = "renderNameTag", at = @At("TAIL"))
     *///? }
    private void revertState(CallbackInfo ci) {
        Util.hasTeam = false;
    }

    //? if >= 26.2 {
    @ModifyConstant(method = "submitNameTag", constant = @Constant(intValue = -16777216, ordinal = 0))
    //? } elif >= 1.21.10 {
    /*@ModifyVariable(method = "add", at = @At(value = "STORE"), ordinal = 2)
    *///? } else {
    /*@ModifyVariable(method = "renderNameTag", at = @At(value = "STORE"), ordinal = 2)
    *///? }
    private int setColor(int value) {
        if (Util.hasTeam) return Util.tagColor | value;
        return value;
    }

}