package me.imtoggle.teamcolors.hook

import me.imtoggle.teamcolors.data.ColorEntry

interface PlayerTeamHook {

    fun `teamColors$getColorEntry`(): ColorEntry

    fun `teamColors$setColorEntry`(entry: ColorEntry)

}