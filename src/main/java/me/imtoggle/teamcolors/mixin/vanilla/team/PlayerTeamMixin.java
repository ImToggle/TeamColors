package me.imtoggle.teamcolors.mixin.vanilla.team;

import me.imtoggle.teamcolors.data.ColorEntry;
import me.imtoggle.teamcolors.hook.ColorHook;
import me.imtoggle.teamcolors.util.ColorUtil;
import net.minecraft.ChatFormatting;import net.minecraft.world.scores.PlayerTeam;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTeam.class)
public class PlayerTeamMixin implements ColorHook {

    @Unique
    private ColorEntry teamColors$colorEntry;

    @Override
    public @NotNull ColorEntry teamColors$getColorEntry() {
        return teamColors$colorEntry;
    }

    @Override
    public void teamColors$setColorEntry(@NotNull ColorEntry entry) {
        this.teamColors$colorEntry = entry;
    }

    //? if >= 26.2 {
    @Inject(method = "setColor", at = @At("HEAD"))
    private void setEntry(java.util.Optional<net.minecraft.world.scores.TeamColor> color, CallbackInfo ci) {
        color.ifPresent((teamColor) -> teamColors$colorEntry = ColorUtil.getColorMap().get(teamColor.rgb()));
    }
    //? } else {
    /*@Inject(method = "setColor", at = @At("HEAD"))
    private void setEntry(ChatFormatting color, CallbackInfo ci) {
        if (color.isColor()) {
            teamColors$colorEntry = ColorUtil.getColorMap().get(color.getColor());
        }
    }
    *///? }

}