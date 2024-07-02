package net.mcreator.wildernessoddesyapi;

import org.apache.commons.lang3.tuple.Pair;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModLoadingContext;

@Mod(MobStages.MODID)
public class MobStages {
	public static final String MODID = "wilderness_oddesy_api";
	public static int daysElapsed = 0;
	public static final int BASE_MOB_SPAWN_RATE = 5; // Base rate of mob spawning

	public MobStages() {
		// Registering the common configuration
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfigHolder.COMMON_SPEC);
	}

	// Holder class for configuration settings
	public static class ModConfigHolder {
		public static final Common COMMON;
		public static final ModConfigSpec COMMON_SPEC;
		static {
			// Pair to hold the common config and its spec
			Pair<Common, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Common::new);
			COMMON = commonSpecPair.getLeft();
			COMMON_SPEC = commonSpecPair.getRight();
		}

		// Configuration settings for the mod
		public static class Common {
			public final ModConfigSpec.IntValue maxMobs;

			public Common(ModConfigSpec.Builder builder) {
				builder.push("general");
				maxMobs = builder.comment("Maximum number of mobs that can spawn").defineInRange("maxMobs", 100, 1, 1000);
				builder.pop();
			}
		}
	}
}
