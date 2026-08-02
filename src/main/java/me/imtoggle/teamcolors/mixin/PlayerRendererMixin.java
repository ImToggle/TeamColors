package me.imtoggle.teamcolors.mixin;

import me.imtoggle.teamcolors.util.TagComponent;
import me.imtoggle.teamcolors.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >= 1.21.4 {
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
//? if >= 1.21.10 {
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
//? } else {
/*import net.minecraft.client.renderer.entity.state.PlayerRenderState;
*///? }
//? } else {
/*import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.injection.ModifyArg;
*///? }

//? if >= 1.21.10 {
@Mixin(net.minecraft.client.renderer.entity.player.AvatarRenderer.class)
//? } else {
/*@Mixin(net.minecraft.client.renderer.entity.player.PlayerRenderer.class)
*///? }
public class PlayerRendererMixin {
    //? if <= 1.21.11 {
    /*//? if >= 1.21.4 {
    //? if >= 1.21.10 {
    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
    private void addTag(@Coerce Entity entity, AvatarRenderState state, float f, CallbackInfo ci) {
    //? } else {
    /^@Inject(method = "extractRenderState(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;F)V", at = @At("TAIL"))
    private void addTag(@Coerce Entity entity, PlayerRenderState state, float f, CallbackInfo ci) {
        ^///? }
        if (state.scoreText == null || !Util.isNametagEnabled() || !Util.hasTeamColor(entity)) return;
        state.scoreText = new TagComponent(state.scoreText, Util.getNametagColor(entity));
    }
    //? } else {
    /^@ModifyArg(method = "renderNameTag(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IF)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;renderNameTag(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IF)V", ordinal = 0), index = 1)
    private Component addTag(Component component, @Local(argsOnly = true) AbstractClientPlayer entity) {
        if (!Util.isNametagEnabled() || !Util.hasTeamColor(entity)) return component;
        return new TagComponent(component, Util.getNametagColor(entity));
    }
    ^///? }
    *///? }

}