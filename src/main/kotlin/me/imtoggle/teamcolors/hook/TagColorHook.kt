package me.imtoggle.teamcolors.hook

interface TagColorHook {

    fun `teamcolors$getColor`(): Int?

    fun `teamcolors$setColor`(color: Int?)

}