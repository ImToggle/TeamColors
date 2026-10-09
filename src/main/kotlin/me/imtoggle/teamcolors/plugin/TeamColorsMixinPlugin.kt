package me.imtoggle.teamcolors.plugin

import org.objectweb.asm.tree.ClassNode
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import org.spongepowered.asm.mixin.extensibility.IMixinInfo

class TeamColorsMixinPlugin : IMixinConfigPlugin {

    override fun onLoad(mixinPackage: String) {
    }

    override fun getRefMapperConfig() = null

    override fun shouldApplyMixin(targetClassName: String, mixinClassName: String) = true

    override fun acceptTargets(myTargets: Set<String>, otherTargets: Set<String>) {
    }

    override fun getMixins() = buildList {
        //? if >= 26.2 {
        add("vanilla.SubmitNodeCollectionMixin")
        //? } else {
        /*add("vanilla.NametagFeatureRendererMixin")
        *///? }

        //? if <= 1.21.11 {
        /*add("vanilla.AvatarRendererMixin")
        *///? }

        //? if >= 1.21.11 {
        add("vanilla.EntityHitboxDebugRendererMixin")
        //? }
    }

    override fun preApply(targetClassName: String, targetClass: ClassNode, mixinClassName: String, mixinInfo: IMixinInfo) {
    }

    override fun postApply(targetClassName: String, targetClass: ClassNode, mixinClassName: String, mixinInfo: IMixinInfo) {
    }
}