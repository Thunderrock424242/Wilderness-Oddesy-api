package net.mcreator.wildernessoddesyapi.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;

import net.mcreator.wildernessoddesyapi.accessor.LevelAccessor;

@Mixin(Entity.class)
public abstract class EntityMixinMixin implements LevelAccessor {
    @Shadow
    private Level level;

    @Override
    public Level getLevel() {
        return this.level;
    }
}
