package net.mcreator.wildernessoddesyapi.mixins;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.Level;

import net.mcreator.wildernessoddesyapi.MobStages;

@Mixin(Level.class)
public class MobStagesMixinMixin {
	/**
	 * Injects into the world tick method to increment the days elapsed in the MobStages class.
	 *
	 * @param ci The callback information.
	 */
	@Inject(method = "tick", at = @At("HEAD"))
	private void onWorldTick(CallbackInfo ci) {
		Level world = (Level) (Object) this;
		// Only increment days elapsed in the Overworld on the server side
		if (world.dimension() == Level.OVERWORLD && !world.isClientSide) {
			// Check if it's the start of a new day (every 24000 ticks)
			if (world.getDayTime() % 24000 == 0) {
				MobStages.daysElapsed++;
			}
		}
	}
}
