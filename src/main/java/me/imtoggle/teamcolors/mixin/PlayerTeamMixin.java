package me.imtoggle.teamcolors.mixin;

import me.imtoggle.teamcolors.hook.PlayerTeamHook;
import me.imtoggle.teamcolors.util.ColorEntry;
import me.imtoggle.teamcolors.util.Util;
import net.minecraft.world.scores.PlayerTeam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTeam.class)
public class PlayerTeamMixin implements PlayerTeamHook {

    @Unique
    private ColorEntry colorEntry;

    @Override
    public ColorEntry teamColors$getColorEntry() {
        return colorEntry;
    }

    @Override
    public void teamColors$setColorEntry(ColorEntry colorEntry) {
        this.colorEntry = colorEntry;
    }

    @Inject(method = "setColor", at = @At("TAIL"))
    private void insertColorEntry1(CallbackInfo ci) {
        if (!Util.hasTeamColor((PlayerTeam) (Object) this)) return;
        colorEntry = Util.getColorEntry(Util.getTeamColor((PlayerTeam) (Object) this));
    }

}