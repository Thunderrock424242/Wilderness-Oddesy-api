package net.mcreator.wildernessoddesyapi.mixins;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.EntityType;

import net.mcreator.wildernessoddesyapi.goals.WolfShelterGoal;
import net.mcreator.wildernessoddesyapi.goals.SeekShelterDuringStormGoal;

@Mixin(Animal.class)
public abstract class DynamicWeatherEffectsMixin extends PathfinderMob {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicWeatherEffectsMixin.class);

    protected DynamicWeatherEffectsMixin(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void addShelterSeekingGoal(CallbackInfo info) {
        LOGGER.warn("Registering shelter seeking goal for: " + this.getType().toString());
        if ((Object) this instanceof Wolf) {
            this.goalSelector.addGoal(1, new WolfShelterGoal((Animal) (Object) this));
        } else {
            this.goalSelector.addGoal(1, new SeekShelterDuringStormGoal((Animal) (Object) this));
        }
    }
}
