package me.imtoggle.teamcolors.mixin.compat;

import com.bawnorton.mixinsquared.TargetHandler;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

//? if >= 1.21.10 {
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//? } else {
/*import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.util.TagComponent;
import net.minecraft.network.chat.Component;
*///? }

@Pseudo
//? if >= 26.2 {
@Mixin(value = net.minecraft.client.renderer.extract.LevelExtractor.class, priority = 1500)
//? } else {
/*@Mixin(value = net.minecraft.client.renderer.LevelRenderer.class, priority = 1500)
*///? }
public class EntityCullingMixin {

    @Dynamic("EntityCulling")
    //? if >= 1.21.10 {
    @TargetHandler(mixin = "dev.tr7zw.entityculling.mixin.WorldRendererMixin", name = "processNametag")
    @Inject(method = "@MixinSquared:Handler", at = @At("RETURN"))
    private static void applyTag(Entity entity, float partialTick, EntityRenderState state, CallbackInfoReturnable<EntityRenderState> cir) {
        Util.handleState(entity, state);
    }
    //? } else {
    /*@TargetHandler(mixin = "dev.tr7zw.entityculling.mixin.WorldRendererMixin", name = "renderEntity")
    @WrapOperation(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getDisplayName()Lnet/minecraft/network/chat/Component;"))
    private Component applyTag(Component component, @Local(argsOnly = true) Entity entity) {
        if (!Util.isNametagEnabled() || !Util.hasTeamColor(entity)) return component;
        return new TagComponent(component, Util.getTeamColor(entity));
    }
    *///? }
}