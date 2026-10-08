package me.imtoggle.teamcolors.mixin.vanilla.team;

import me.imtoggle.teamcolors.data.ColorEntry;
import me.imtoggle.teamcolors.hook.ColorHook;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.TeamColor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

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

    @Inject(method = "setColor", at = @At("HEAD"))
    private void setEntry(Optional<TeamColor> color, CallbackInfo ci) {
        if (color.isPresent()) {

        }
    }

}