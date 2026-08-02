package me.imtoggle.teamcolors.hook;

import me.imtoggle.teamcolors.util.ColorEntry;

public interface PlayerTeamHook {
    ColorEntry teamColors$getColorEntry();
    void teamColors$setColorEntry(ColorEntry colorEntry);
}