package net.mcreator.wildernessoddesyapi.mixins;

import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.Level;

@Mixin(Level.class)
public abstract class DayNightCycleMixin {
	/**
	 * Redirects the call to getDayTime in the Level class to modify the day-night cycle duration.
	 *
	 * @param level The level instance.
	 * @return The modified day time based on the custom day duration.
	 */
	@Redirect(method = "Lnet/minecraft/world/level/Level;tickTime()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getDayTime()J"))
	private long modifyDayNightCycle(Level level) {
		long dayTime = level.getDayTime();
		long vanillaDayDuration = 24000L; // Vanilla Minecraft day duration in ticks
		long customDayDuration = 1000L; // Custom day duration in ticks (5 minutes * 60 seconds * 20 ticks)
		// Calculate the time of day within the current day cycle
		long timeOfDay = dayTime % vanillaDayDuration;
		// Scale the time of day to fit the custom day duration
		long customTimeOfDay = (timeOfDay * customDayDuration) / vanillaDayDuration;
		// Calculate and return the new day time
		return (dayTime / vanillaDayDuration) * customDayDuration + customTimeOfDay;
	}
}
