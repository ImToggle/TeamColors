package me.imtoggle.teamcolors.mixin.vanilla;

//? if = 1.8.9 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.config.ModConfig;
import me.imtoggle.teamcolors.data.ColorEntry;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.client.render.vertex.BufferBuilder;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.polyfrost.compose.render.PolyColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @WrapOperation(method = "renderNameTag(Lnet/minecraft/world/entity/LivingEntity;DDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/vertex/BufferBuilder;color(FFFF)Lnet/minecraft/client/render/vertex/BufferBuilder;"))
    private net.minecraft.client.render.vertex.BufferBuilder setNametag(net.minecraft.client.render.vertex.BufferBuilder instance, float r, float g, float b, float a, Operation<BufferBuilder> original, @Local(argsOnly = true) LivingEntity entity) {
        if (ModConfig.INSTANCE.getNametagCategory().getEnabled() && Util.hasTeamColor(entity)) {
            ColorEntry entry = Util.getColorEntry(entity);
            if (entry != null) {
                PolyColor color = entry.getNametagColor();
                return original.call(instance, color.getRedF(), color.getGreenF(), color.getBlueF(), a);
            }
        }
        return original.call(instance, r, g, b, a);
    }
}
*///? } else {
public class LivingEntityRendererMixin {}
//? }