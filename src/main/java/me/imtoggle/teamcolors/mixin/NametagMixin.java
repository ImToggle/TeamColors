package me.imtoggle.teamcolors.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.util.Util;
import org.spongepowered.asm.mixin.Mixin;

//? if >= 1.21.4 {
import me.imtoggle.teamcolors.util.TagComponent;
import net.minecraft.network.chat.Component;
//? }

//? if >= 26.2 {
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(net.minecraft.client.renderer.SubmitNodeCollection.class)
//? } else {
/*import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
//? if >= 1.21.10 {
@Mixin(net.minecraft.client.renderer.feature.NameTagFeatureRenderer.Storage.class)
//? } else {
/^@Mixin(net.minecraft.client.renderer.entity.EntityRenderer.class)
^///? }
*///? }
public class NametagMixin {
    //? if >= 26.2 {
    @ModifyConstant(method = "submitNameTag", constant = @Constant(intValue = -16777216, ordinal = 0))
    private int setColor(int value, @Local(ordinal = 0, argsOnly = true) final Component name) {
        Util.hasTeam = false;
        if (name instanceof TagComponent tagComponent) {
            Util.hasTeam = true;
            return Util.teamColor = Util.getNametagColor(tagComponent.getTeamColor());
        }
        return value;
    }
    //? } else {
    /*//? if >= 1.21.10 {
    @ModifyVariable(method = "add", at = @At(value = "STORE"), ordinal = 2)
    //? } else {
    /^@ModifyVariable(method = "renderNameTag", at = @At(value = "STORE"), ordinal = 2)
    ^///? }
    //? if >= 1.21.4 {
    private int setColor(int value, @Local(ordinal = 0, argsOnly = true) final Component name) {
        Util.hasTeam = false;
        if (name instanceof TagComponent tagComponent) {
            Util.hasTeam = true;
            return Util.teamColor = (Util.getNametagColor(tagComponent.getTeamColor()) & 0x00FFFFFF) | value;
        }
        return value;
    }
    //? } else {
    /^private int setColor(int value, @Local(ordinal = 0, argsOnly = true) net.minecraft.world.entity.Entity entity) {
        Util.hasTeam = Util.isNametagEnabled() && Util.hasTeamColor(entity);
        if (!Util.hasTeam) return value;
        return Util.teamColor = (Util.getNametagColor(entity.getTeamColor()) & 0x00FFFFFF) | value;
    }
    ^///? }
    *///? }
}