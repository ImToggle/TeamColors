package me.imtoggle.teamcolors.plugin

import com.bawnorton.mixinsquared.MixinSquaredBootstrap
import net.fabricmc.loader.api.FabricLoader
import org.objectweb.asm.tree.ClassNode
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import org.spongepowered.asm.mixin.extensibility.IMixinInfo

class TeamColorsMixinPlugin : IMixinConfigPlugin {

    override fun onLoad(mixinPackage: String) {
        MixinSquaredBootstrap.init()
    }

    override fun getRefMapperConfig() = null

    override fun shouldApplyMixin(targetClassName: String, mixinClassName: String) = true

    override fun acceptTargets(myTargets: Set<String>, otherTargets: Set<String>) {
    }

    override fun getMixins() = buildList {
        MixinSquaredBootstrap.reOrderExtensions()
        //? if >= 26.2 {
        add("vanilla.SubmitNodeCollectionMixin")
        //? } elif >= 1.21.10 {
        /*add("vanilla.NametagFeatureRendererMixin")
        *///? }

        //? if >= 1.21.10 && <= 1.21.11 {
        /*add("vanilla.AvatarRendererMixin")
        *///? }

        //? if > 1.21.1 && <= 1.21.8 {
        /*add("vanilla.PlayerRendererMixin")
        *///? }

        //? if = 1.8.9 {
        /*add("vanilla.LivingEntityRendererMixin")
        *///? }

        //? if >= 1.21.11 {
        add("vanilla.EntityHitboxDebugRendererMixin")
        //? } elif <= 1.21.4 {
        /*add("vanilla.EntityRenderDispatcherMixin")
        *///? }

        if (FabricLoader.getInstance().isModLoaded("polynametag")) {
            add("polynametag.NametagRendererMixin")

            //? if >= 26.3 {
            add("polynametag.RenderBackgroundShape263Mixin")
            //? } elif = 26.2 {
            /*add("polynametag.NameTagSubmitMixin")
            add("polynametag.WrapNametagRender262Mixin")
            add("polynametag.RenderBackgroundShape262Mixin")
            *///? } elif > 1.8.9 {
            /*add("polynametag.RenderBackgroundShapeMixin")
            *///? }
        }
    }

    override fun preApply(targetClassName: String, targetClass: ClassNode, mixinClassName: String, mixinInfo: IMixinInfo) {
    }

    override fun postApply(targetClassName: String, targetClass: ClassNode, mixinClassName: String, mixinInfo: IMixinInfo) {
    }
}