package me.imtoggle.teamcolors.mixin.vanilla;

import me.imtoggle.teamcolors.data.ColorEntry;
import me.imtoggle.teamcolors.hook.PlayerTeamHook;
import me.imtoggle.teamcolors.util.ColorUtil;
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
    private ColorEntry teamColors$colorEntry;

    @Override
    public ColorEntry teamColors$getColorEntry() {
        return teamColors$colorEntry;
    }

    @Override
    public boolean teamColors$hasColor() {
        return teamColors$colorEntry != null;
    }

    //? if >= 26.2 {
    @Inject(method = "setColor", at = @At("HEAD"))
    private void setEntry(java.util.Optional<net.minecraft.world.scores.TeamColor> color, CallbackInfo ci) {
        teamColors$colorEntry = null;
        color.ifPresent((teamColor) -> teamColors$colorEntry = ColorUtil.getColorMap().get(teamColor.rgb()));
    }
    //? } elif > 1.8.9 {
    /*@Inject(method = "setColor", at = @At("HEAD"))
    private void setEntry(net.minecraft.ChatFormatting color, CallbackInfo ci) {
        teamColors$colorEntry = null;
        if (color.isColor()) {
            teamColors$colorEntry = ColorUtil.getColorMap().get(color.getColor());
        }
    }
    *///? } else {
    /*@Inject(method = "setPrefix", at = @At("HEAD"))
    private void setEntry(String prefix, CallbackInfo ci) {
        teamColors$colorEntry = null;
        int id = Util.getFormattingID(prefix);
        if (id >= 0) {
            teamColors$colorEntry = ColorUtil.getColorMap().get(ColorUtil.getLegacyColors()[id]);
        }
    }
    *///? }

}