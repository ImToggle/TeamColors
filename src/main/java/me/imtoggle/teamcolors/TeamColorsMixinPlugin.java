package me.imtoggle.teamcolors;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class TeamColorsMixinPlugin implements IMixinConfigPlugin {

    private static final Map<String, Supplier<Boolean>> CONDITIONS = ImmutableMap.of(
        "me.imtoggle.teamcolors.mixin.compat.CombatHitboxesMixin", () -> FabricLoader.getInstance().isModLoaded("combat-hitboxes"),
        "me.imtoggle.teamcolors.mixin.compat.HitboxPlusMixin", () -> FabricLoader.getInstance().isModLoaded("hitboxplus"),
        "me.imtoggle.teamcolors.mixin.compat.NametagTweaksMixin", () -> FabricLoader.getInstance().isModLoaded("nametagtweaks")
    );

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return CONDITIONS.getOrDefault(mixinClassName, () -> true).get();
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}