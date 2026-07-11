package me.imtoggle.teamcolors.mixin;

import me.imtoggle.teamcolors.util.Util;
import net.minecraft.client.renderer.entity.EntityRenderer;
//? if >= 1.21.4 {
import me.imtoggle.teamcolors.util.TagComponent;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? }
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    //? if >= 1.21.4 {
    //? if >= 26.2 {
    @Inject(method = "extractNameTags(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;FDD)V", at = @At("TAIL"))
    private void addTag(Entity entity, EntityRenderState state, float partialTicks, double nameTagDistance, double belowNameDistance, CallbackInfo ci) {
    //? } else {
    /*@Inject(method = "extractRenderState", at = @At("TAIL"))
    private void addTag(Entity entity, EntityRenderState state, float partialTicks, CallbackInfo ci) {
    *///? }
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
    //? }
}