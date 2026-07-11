package me.imtoggle.teamcolors.mixin;

import me.imtoggle.teamcolors.util.TagComponent;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if >= 1.21.4 {
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? } else {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.chat.Component;
*///? }

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    //? if >= 1.21.4 {
    //? if >= 26.2 {
    @Inject(method = "extractNameTags(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;FDD)V", at = @At("TAIL"))
    //? } else {
    /*@Inject(method = "extractRenderState", at = @At("TAIL"))
    *///? }
    private void addTag(CallbackInfo ci, @Local(argsOnly = true) Entity entity, @Local(argsOnly = true) EntityRenderState state) {
        if (!Util.isNametagEnabled() || !Util.hasTeamColor(entity)) return;
        int teamColor = Util.getTeamColor(entity);
        //? if >= 26.1 {
        if (state.scoreText != null) {
            state.scoreText = new TagComponent(state.scoreText, teamColor);
        }
        //? }
        if (state.nameTag != null) {
            state.nameTag = new TagComponent(state.nameTag, teamColor);
        }
    }
    //? } else {
    /*@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getDisplayName()Lnet/minecraft/network/chat/Component;"))
    private Component getDisplayName(Entity entity, Operation<Component> original) {
        Component component = original.call(entity);
        if (!Util.isNametagEnabled() || !Util.hasTeamColor(entity)) return component;
        return new TagComponent(component, Util.getTeamColor(entity));
    }
    *///? }
}