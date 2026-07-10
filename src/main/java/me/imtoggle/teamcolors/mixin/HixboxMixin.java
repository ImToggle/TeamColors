package me.imtoggle.teamcolors.mixin;

import me.imtoggle.teamcolors.util.Util;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

//? if >= 1.21.8 {
import com.llamalad7.mixinextras.sugar.Local;
//? }

//? if >= 1.21.11 {
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
@Mixin(net.minecraft.client.renderer.debug.EntityHitboxDebugRenderer.class)
//? } else {
/*import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if >= 1.21.8 {
@Mixin(net.minecraft.client.renderer.entity.EntityRenderer.class)
//? } else {
/^@Mixin(net.minecraft.client.renderer.entity.EntityRenderDispatcher.class)
^///? }
*///? }
public class HixboxMixin {

    //? if >= 1.21.11 {
    @ModifyConstant(method = "showHitboxes", constant = @Constant(intValue = -1, ordinal = 0))
    private int setColor(int value, @Local(ordinal = 0, argsOnly = true) Entity entity) {
        if (!Util.isHitboxEnabled() || !Util.hasTeamColor(entity)) return value;
        return Util.getHitboxColor(entity.getTeamColor());
    }
    //? } elif >= 1.21.8 {
    /*@WrapOperation(method = "extractHitboxes(Lnet/minecraft/world/entity/Entity;FZ)Lnet/minecraft/client/renderer/entity/state/HitboxesRenderState;", at = @At(value = "NEW", target = "(DDDDDDFFF)Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;", ordinal = 1))
    private net.minecraft.client.renderer.entity.state.HitboxRenderState setColor(double d, double e, double f, double g, double h, double i, float j, float k, float l, Operation<net.minecraft.client.renderer.entity.state.HitboxRenderState> original, @Local(ordinal = 0) Entity entity) {
        if (!Util.isHitboxEnabled() || !Util.hasTeamColor(entity)) return original.call(d, e, f, g, h, i, j, k, l);
        int color = Util.getHitboxColor(entity.getTeamColor());
        return original.call(d, e, f, g, h, i, Util.getRed(color) / 255f, Util.getGreen(color) / 255f, Util.getBlue(color) / 255f);
    }
    *///? } else {
    /*//? if >= 1.21.4 {
    @WrapOperation(method = "render(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;renderHitbox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;FFFF)V"))
    //? } else {
    /^@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;renderHitbox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;FFFF)V"))
    ^///? }
    private void setColor(com.mojang.blaze3d.vertex.PoseStack poseStack, com.mojang.blaze3d.vertex.VertexConsumer vertexConsumer, Entity entity, float f, float g, float h, float i, Operation<Void> original) {
        if (!Util.isHitboxEnabled() || !Util.hasTeamColor(entity)) {
            original.call(poseStack, vertexConsumer, entity, f, g, h, i);
            return;
        }
        int color = Util.getHitboxColor(entity.getTeamColor());
        original.call(poseStack, vertexConsumer, entity, f, Util.getRed(color) / 255f, Util.getGreen(color) / 255f, Util.getBlue(color) / 255f);
    }
    *///? }
}