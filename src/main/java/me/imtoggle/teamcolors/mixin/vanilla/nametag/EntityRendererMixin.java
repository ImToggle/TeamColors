package me.imtoggle.teamcolors.mixin.vanilla.nametag;

import me.imtoggle.teamcolors.util.NametagPackager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Inject(method = "extractNameTags(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;FDD)V", at = @At("TAIL"))
    private void extractNameTags(net.minecraft.world.entity.Entity entity, EntityRenderState state, float partialTicks, double nameTagDistance, double belowNameDistance, CallbackInfo ci) {
        state.nameTag = NametagPackager.INSTANCE.pack(state.nameTag, entity);
        state.scoreText = NametagPackager.INSTANCE.pack(state.scoreText, entity);
    }
}
