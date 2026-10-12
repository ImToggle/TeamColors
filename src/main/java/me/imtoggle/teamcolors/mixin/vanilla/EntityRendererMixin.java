package me.imtoggle.teamcolors.mixin.vanilla;

import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.config.ModConfig;
import me.imtoggle.teamcolors.data.ColorEntry;
import me.imtoggle.teamcolors.util.RenderUtil;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if > 1.21.1 {
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? }

//? if < 1.21.11 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.polyfrost.compose.render.PolyColor;
//? if >= 1.21.5 {
import net.minecraft.client.renderer.entity.state.HitboxRenderState;
//? }
//? if > 1.8.9 {
import me.imtoggle.teamcolors.data.TagComponent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;
//? }
*///? }

@Mixin(value = EntityRenderer.class, priority = 1200)
public class EntityRendererMixin {

    //? if > 1.21.1 {
    private final String EXTRACT =
            //? if >= 26.2 {
            "extractNameTags(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;FDD)V";
            //? } else {
            /*"extractRenderState";
            *///? }

    @Inject(method = EXTRACT, at = @At("TAIL"))
    private void packNametags(CallbackInfo ci, @Local(argsOnly = true, name = "entity") Entity entity, @Local(argsOnly = true) EntityRenderState state) {
        if (!ModConfig.INSTANCE.getNametagCategory().isEnabled()) return;
        state.nameTag = Util.pack(state.nameTag, entity);
        //? if >= 26.1 {
        state.scoreText = Util.pack(state.scoreText, entity);
        //? }
    }
    //? }

    //? if >= 1.21.5 && < 1.21.11 {
    /*@WrapOperation(
            method = "extractHitboxes(Lnet/minecraft/world/entity/Entity;FZ)Lnet/minecraft/client/renderer/entity/state/HitboxesRenderState;",
            at = @At(value = "NEW", target = "(DDDDDDFFF)Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;")
    )
    private HitboxRenderState setHitboxColor(double d, double e, double f, double g, double h, double i, float j, float k, float l, Operation<HitboxRenderState> original, @Local(ordinal = 0, argsOnly = true) Entity entity) {
        if (ModConfig.INSTANCE.getHitboxCategory().isEnabled()) {
            ColorEntry entry = Util.getColorEntry(entity);
            if (entry != null) {
                PolyColor color = entry.getHitboxColor();
                return original.call(d, e, f, g, h, i, color.getRedF(), color.getGreenF(), color.getBlueF());
            }
        }
        return original.call(d, e, f, g, h, i, j, k, l);
    }
    *///? }

    //? if > 1.8.9 && <= 1.21.8 {
    /*//~ if < 1.21.8 ')V' -> ')I'
    @WrapOperation(method = "renderNameTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)V"))
    //~ if < 1.21.8 'void' -> 'int'
    private void setNametagColor(
            //~ if < 1.21.8 'Void' -> 'Integer'
            Font instance, Component component, float f, float g, int i, boolean bl, Matrix4f matrix4f, MultiBufferSource multiBufferSource, Font.DisplayMode displayMode, int j, int k, Operation<Void> original
            //? if = 1.21.1 {
            /^, @Local(argsOnly = true) Entity entity
            ^///? }
    ) {
        //? if = 1.21.1 {
        /^if (ModConfig.INSTANCE.getNametagCategory().isEnabled()) {
            component = Util.pack(component, entity);
        }
        ^///? }
        RenderUtil.tagColor = null;
        if (component instanceof TagComponent tagComponent) {
            RenderUtil.tagColor = tagComponent.getNametagColor();
            //~ if < 1.21.8 'original.call(' -> 'return original.call('
            original.call(instance, component, f, g, i, bl, matrix4f, multiBufferSource, displayMode, RenderUtil.tagColor | (j & 0xFF000000), k);
        } else {
            //~ if < 1.21.8 'original.call(' -> 'return original.call('
            original.call(instance, component, f, g, i, bl, matrix4f, multiBufferSource, displayMode, j, k);
        }
    }
    *///? } elif = 1.8.9 {
    /*@WrapOperation(method = "renderNameTag(Lnet/minecraft/world/entity/Entity;Ljava/lang/String;DDDI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/vertex/BufferBuilder;color(FFFF)Lnet/minecraft/client/render/vertex/BufferBuilder;"))
    private net.minecraft.client.render.vertex.BufferBuilder setNametag(net.minecraft.client.render.vertex.BufferBuilder instance, float r, float g, float b, float a, Operation<net.minecraft.client.render.vertex.BufferBuilder> original, @Local(argsOnly = true) Entity entity) {
        RenderUtil.tagColor = null;
        if (ModConfig.INSTANCE.getNametagCategory().isEnabled() && Util.hasTeamColor(entity)) {
            ColorEntry entry = Util.getColorEntry(entity);
            if (entry != null) {
                PolyColor color = entry.getNametagColor();
                RenderUtil.tagColor = color.getArgb();
                return original.call(instance, color.getRedF(), color.getGreenF(), color.getBlueF(), a);
            }
        }
        return original.call(instance, r, g, b, a);
    }
    *///? }
}
