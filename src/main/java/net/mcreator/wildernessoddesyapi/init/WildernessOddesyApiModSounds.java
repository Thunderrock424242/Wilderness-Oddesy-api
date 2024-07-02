
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.wildernessoddesyapi.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.mcreator.wildernessoddesyapi.WildernessOddesyApiMod;

public class WildernessOddesyApiModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, WildernessOddesyApiMod.MODID);
	public static final DeferredHolder<SoundEvent, SoundEvent> I_RAN_SO_FAR_AWAY = REGISTRY.register("i_ran_so_far_away", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("wilderness_oddesy_api", "i_ran_so_far_away")));
}
