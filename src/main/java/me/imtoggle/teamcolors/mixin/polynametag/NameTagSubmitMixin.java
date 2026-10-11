package me.imtoggle.teamcolors.mixin.polynametag;

//? if = 26.2 {
/*import me.imtoggle.teamcolors.hook.TagColorHook;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(NameTagFeatureRenderer.Submit.class)
public class NameTagSubmitMixin implements TagColorHook {

    @Unique
    private Integer teamcolors$nametagColor = null;


    @Override
    public Integer teamcolors$getColor() {
        return teamcolors$nametagColor;
    }

    @Override
    public void teamcolors$setColor(Integer color) {
        teamcolors$nametagColor = color;
    }
}
*///? } else {
public class NameTagSubmitMixin {}
//? }