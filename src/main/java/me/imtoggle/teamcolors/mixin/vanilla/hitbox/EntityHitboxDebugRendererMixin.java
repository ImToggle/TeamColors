package me.imtoggle.teamcolors.mixin.vanilla.hitbox;

//? if >= 1.21.11 {
import com.llamalad7.mixinextras.sugar.Local;
import me.imtoggle.teamcolors.data.ColorEntry;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.client.renderer.debug.EntityHitboxDebugRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityHitboxDebugRenderer.class)
public class EntityHitboxDebugRendererMixin {

    //~ if < 26.1 'name = "mainColor"' -> 'name = "i"'
    @ModifyVariable(method = "showHitboxes", at = @At("STORE"), name = "mainColor")
    private int setHitboxColor(int mainColor, @Local(argsOnly = true, name = "entity") Entity entity) {
        if (Util.isEnabled("hitbox") && Util.hasTeamColor(entity)) {
            ColorEntry entry = Util.getColorEntry(entity);
            if (entry != null) {
                return entry.getColor("hitbox").getArgb();
            }
        }
        return mainColor;
    }

}
//? }