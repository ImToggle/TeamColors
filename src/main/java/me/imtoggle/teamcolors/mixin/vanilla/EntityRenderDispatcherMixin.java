package me.imtoggle.teamcolors.mixin.vanilla;

//? if <= 1.21.4 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.imtoggle.teamcolors.config.ModConfig;
import me.imtoggle.teamcolors.data.ColorEntry;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import org.polyfrost.compose.render.PolyColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if > 1.8.9 {
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
//? } else {
/^import net.minecraft.util.math.Box;
^///? }

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    //? if = 1.21.4 {
    /^@WrapOperation(
            method = "render(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;renderHitbox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;FFFF)V")
    )
    private void setHitboxColor(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, float f, float g, float h, float i, Operation<Void> original) {
        if (ModConfig.INSTANCE.getHitboxCategory().isEnabled() && Util.hasTeamColor(entity)) {
            ColorEntry entry = Util.getColorEntry(entity);
            if (entry != null) {
                PolyColor color = entry.getHitboxColor();
                original.call(poseStack, vertexConsumer, entity, f, color.getRedF(), color.getGreenF(), color.getBlueF());
                return;
            }
        }
        original.call(poseStack, vertexConsumer, entity, f, g, h, i);
    }
    ^///? } elif > 1.8.9 {
    @WrapOperation(method = "renderHitbox", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLineBox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/phys/AABB;FFFF)V",
            ordinal = 0
    ))
    private static void setHitboxColor(PoseStack poseStack, VertexConsumer vertexConsumer, net.minecraft.world.phys.AABB aabb, float r, float g, float b, float a, Operation<Void> original, @com.llamalad7.mixinextras.sugar.Local(argsOnly = true) Entity entity) {
        if (ModConfig.INSTANCE.getHitboxCategory().isEnabled() && Util.hasTeamColor(entity)) {
            ColorEntry entry = Util.getColorEntry(entity);
            if (entry != null) {
                PolyColor color = entry.getHitboxColor();
                original.call(poseStack, vertexConsumer, aabb, color.getRedF(), color.getGreenF(), color.getBlueF(), a);
                return;
            }
        }
        original.call(poseStack, vertexConsumer, aabb, r, g, b, a);
    }
    //? } else {
    /^@WrapOperation(method = "renderHitbox", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/world/WorldRenderer;renderOutlineShape(Lnet/minecraft/util/math/Box;IIII)V",
            ordinal = 0
    ))
    private static void setHitboxColor(Box shape, int r, int g, int b, int a, Operation<Void> original, @com.llamalad7.mixinextras.sugar.Local(argsOnly = true) Entity entity) {
        if (ModConfig.INSTANCE.getHitboxCategory().isEnabled() && Util.hasTeamColor(entity)) {
            ColorEntry entry = Util.getColorEntry(entity);
            if (entry != null) {
                PolyColor color = entry.getHitboxColor();
                original.call(shape, color.getRed(), color.getGreen(), color.getBlue(), a);
                return;
            }
        }
        original.call(shape, r, g, b, a);
    }
    ^///? }
}
*///? } else {
public class EntityRenderDispatcherMixin {}
//? }
