package me.imtoggle.teamcolors.mixin.entityculling;

//? if > 1.8.9 {
import com.bawnorton.mixinsquared.TargetHandler;
import me.imtoggle.teamcolors.config.ModConfig;
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
import net.minecraft.network.chat.Component;
*///? }

@Pseudo
//? if >= 26.2 {
@Mixin(value = net.minecraft.client.renderer.extract.LevelExtractor.class, priority = 1500)
//? } else {
/*@Mixin(value = net.minecraft.client.renderer.LevelRenderer.class, priority = 1500)
 *///? }
public class WorldRendererMixin {

    @Dynamic("EntityCulling")
            //? if >= 1.21.10 {
    @TargetHandler(mixin = "dev.tr7zw.entityculling.mixin.WorldRendererMixin", name = "processNametag")
    @Inject(method = "@MixinSquared:Handler", at = @At("RETURN"))
    private static void applyTag(Entity entity, float partialTick, EntityRenderState state, CallbackInfoReturnable<EntityRenderState> cir) {
        if (!ModConfig.INSTANCE.getNametagCategory().isEnabled()) return;
        state.nameTag = Util.pack(state.nameTag, entity);
        //? if >= 26.1 {
        state.scoreText = Util.pack(state.scoreText, entity);
        //? }
    }
    //? } else {
    /*@TargetHandler(mixin = "dev.tr7zw.entityculling.mixin.WorldRendererMixin", name = "renderEntity")
    @WrapOperation(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getDisplayName()Lnet/minecraft/network/chat/Component;"))
    private Component applyTag(Component component, @Local(argsOnly = true) Entity entity) {
        if (!ModConfig.INSTANCE.getNametagCategory().isEnabled()) return component;
        return Util.pack(component, entity);
    }
    *///? }
}
//? } else {
/*public class WorldRendererMixin {}
*///? }