package net.mcreator.wildernessoddesyapi.mixins;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;

import net.mcreator.wildernessoddesyapi.MobStages.ModConfigHolder;
import net.mcreator.wildernessoddesyapi.MobStages;

@Mixin(EntityType.class)
public class MobSpawnMixin {
	/**
	 * Injects into the canSpawn method to modify mob spawning based on custom rules defined in MobStages.
	 *
	 * @param event The spawn placement check event.
	 * @param cir   The callback info returnable.
	 */
	@Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
	private void onMobSpawn(MobSpawnEvent.SpawnPlacementCheck event, CallbackInfoReturnable<MobSpawnEvent.SpawnPlacementCheck.Result> cir) {
		// Check if the entity type is a monster and if the world is the Overworld
		if (event.getEntityType().getCategory() == MobCategory.MONSTER) {
			Level world = (Level) event.getLevel();
			if (world.dimension() == Level.OVERWORLD) {
				// Apply custom spawning logic for the first 20 days
				if (MobStages.daysElapsed <= 20) {
					int additionalMobs = MobStages.daysElapsed / MobStages.BASE_MOB_SPAWN_RATE;
					int maxMobs = ModConfigHolder.COMMON.maxMobs.get();
					// Allow additional mob spawns based on the custom logic
					if (world.random.nextInt(100) < additionalMobs && additionalMobs < maxMobs) {
						cir.setReturnValue(MobSpawnEvent.SpawnPlacementCheck.Result.ALLOW);
						return;
					}
				}
				// Revert to the default spawning system after 20 days
				cir.setReturnValue(MobSpawnEvent.SpawnPlacementCheck.Result.DEFAULT);
			}
		}
	}
}
