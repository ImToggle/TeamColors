package me.imtoggle.teamcolors.mixin.vanilla.nametag;

import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.util.NametagPackager;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if < 1.21.11 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.imtoggle.teamcolors.data.ColorEntry;
import net.minecraft.client.renderer.entity.state.HitboxRenderState;
import org.polyfrost.compose.render.PolyColor;
*///? }

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    private final String EXTRACT =
            //? if >= 26.2 {
            "extractNameTags(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;FDD)V";
            //? } else {
            /*"extractRenderState";
            *///? }

    @Inject(method = EXTRACT, at = @At("TAIL"))
    private void packNametags(CallbackInfo ci, @Local(argsOnly = true, name = "entity") Entity entity, @Local(argsOnly = true) EntityRenderState state) {
        if (Util.isDisabled("nametag")) return;
        state.nameTag = NametagPackager.pack(state.nameTag, entity);
        //? if >= 26.1 {
        state.scoreText = NametagPackager.pack(state.scoreText, entity);
        //? }
    }

    //? if < 1.21.11 {
    /*@WrapOperation(
            method = "extractHitboxes(Lnet/minecraft/world/entity/Entity;FZ)Lnet/minecraft/client/renderer/entity/state/HitboxesRenderState;",
            at = @At(value = "NEW", target = "(DDDDDDFFF)Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;")
    )
    private HitboxRenderState setHitboxColor(double d, double e, double f, double g, double h, double i, float j, float k, float l, Operation<HitboxRenderState> original, @Local(ordinal = 0, argsOnly = true) Entity entity) {
        if (Util.isEnabled("hitbox") && Util.hasTeamColor(entity)) {
            ColorEntry entry = Util.getColorEntry(entity);
            if (entry != null) {
                PolyColor color = entry.getColor("hitbox");
                return original.call(d, e, f, g, h, i, color.getRedF(), color.getGreenF(), color.getBlueF());
            }
        }
        return original.call(d, e, f, g, h, i, j, k, l);
    }
    *///? }
}
