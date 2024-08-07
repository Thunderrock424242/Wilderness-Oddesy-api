package net.mcreator.wildernessoddesyapi.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.wildernessoddesyapi.network.WildernessOddesyApiModVariables;

public class SetupCrawlProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!entity.getData(WildernessOddesyApiModVariables.PLAYER_VARIABLES).Is_player_crawling) {
			{
				WildernessOddesyApiModVariables.PlayerVariables _vars = entity.getData(WildernessOddesyApiModVariables.PLAYER_VARIABLES);
				_vars.Is_player_crawling = true;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}
