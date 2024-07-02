package net.mcreator.wildernessoddesyapi;

import org.checkerframework.checker.units.qual.K;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import java.util.logging.Logger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Properties;
import java.util.Map;
import java.util.List;

public class CustomItem extends Item {
	private static final Logger LOGGER = Logger.getLogger(CustomItem.class.getName());
	private static final Map<String, GenericCacheManager<?, ?>> caches = new ConcurrentHashMap<>();
	static {
		caches.put("item", new GenericCacheManager<String, CustomItemData>());
		caches.put("block", new GenericCacheManager<BlockPos, CustomBlockData>());
		caches.put("biome", new GenericCacheManager<String, CustomBiomeData>());
		caches.put("worldGen", new GenericCacheManager<String, CustomWorldGenData>());
		caches.put("config", new GenericCacheManager<String, CustomConfigData>());
		caches.put("pathfinding", new GenericCacheManager<String, CustomPathfindingData>());
		caches.put("weather", new GenericCacheManager<String, CustomWeatherData>());
		caches.put("entity", new GenericCacheManager<String, CustomEntityData>());
		caches.put("event", new GenericCacheManager<String, CustomEventData>());
		caches.put("quest", new GenericCacheManager<String, CustomQuestData>());
		caches.put("skill", new GenericCacheManager<String, CustomSkillData>());
		caches.put("structure", new GenericCacheManager<String, CustomStructureData>());
		caches.put("achievement", new GenericCacheManager<String, CustomAchievementData>());
		caches.put("recipe", new GenericCacheManager<String, CustomRecipeData>());
		caches.put("dimension", new GenericCacheManager<String, CustomDimensionData>());
		caches.put("npc", new GenericCacheManager<String, CustomNPCData>());
	}

	public CustomItem(Properties properties) {
		super(properties);
		LOGGER.info("CustomItem initialized with properties: " + properties);
	}

	@Override
	public void onCraftedBy(ItemStack stack, net.minecraft.world.level.Level world, Player player) {
		super.onCraftedBy(stack, world, player);
		CustomItemData itemData = new CustomItemData(stack.getHoverName().getString(), calculateItemValue(stack), "Common", "A special crafted item.");
		((GenericCacheManager<String, CustomItemData>) getCache("item")).put(stack.getDescriptionId(), itemData);
		LOGGER.info("Item crafted: " + stack.getHoverName().getString());
	}

	@Override
	public void inventoryTick(ItemStack stack, net.minecraft.world.level.Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		// Retrieve custom item data from the cache
		CustomItemData itemData = ((GenericCacheManager<String, CustomItemData>) getCache("item")).get(stack.getDescriptionId());
		if (itemData != null) {
			applyCustomEffects(entity, itemData);
			LOGGER.fine("Applying custom effects to entity: " + entity.getName().getString());
		}
		// Example of using block data
		BlockPos pos = entity.blockPosition();
		CustomBlockData blockData = ((GenericCacheManager<BlockPos, CustomBlockData>) getCache("block")).get(pos);
		if (blockData != null) {
			LOGGER.fine("Block Type: " + blockData.getBlockType() + ", Hardness: " + blockData.getBlockHardness());
		}
	}

	private int calculateItemValue(ItemStack stack) {
		int value = stack.getCount() * 10;
		LOGGER.fine("Calculated item value: " + value + " for stack: " + stack.getHoverName().getString());
		return value;
	}

	private void applyCustomEffects(Entity entity, CustomItemData data) {
		// Custom logic to apply effects based on item data
	}

	private static <K, V> void addData(GenericCacheManager<K, V> cache, K id, V data) {
		cache.put(id, data);
		LOGGER.fine("Added data to cache: " + data + " with ID: " + id);
	}

	private static GenericCacheManager<?, ?> getCache(String cacheName) {
		return caches.get(cacheName);
	}

	public static void addBlockData(BlockPos pos, String blockType, int hardness) {
		CustomBlockData blockData = new CustomBlockData(blockType, hardness, 5, "Pickaxe");
		addData((GenericCacheManager<BlockPos, CustomBlockData>) getCache("block"), pos, blockData);
	}

	public static void addBiomeData(String biomeId, String biomeName, float temperature, float humidity) {
		CustomBiomeData biomeData = new CustomBiomeData(biomeName, temperature, humidity, 0.5f, 100);
		addData((GenericCacheManager<String, CustomBiomeData>) getCache("biome"), biomeId, biomeData);
	}

	public static void addWorldGenData(String structureId, String structureName, int complexity) {
		CustomWorldGenData worldGenData = new CustomWorldGenData(structureName, complexity, 0.1, List.of("Forest", "Plains"));
		addData((GenericCacheManager<String, CustomWorldGenData>) getCache("worldGen"), structureId, worldGenData);
	}

	public static void addConfigData(String configId, String configName, String configValue) {
		CustomConfigData configData = new CustomConfigData(configName, configValue, "default", true);
		addData((GenericCacheManager<String, CustomConfigData>) getCache("config"), configId, configData);
	}

	public static void addPathfindingData(String entityId, BlockPos[] path) {
		CustomPathfindingData pathfindingData = new CustomPathfindingData(entityId, path, false, 0);
		addData((GenericCacheManager<String, CustomPathfindingData>) getCache("pathfinding"), entityId, pathfindingData);
	}

	public static void addWeatherData(String weatherId, String weatherType, long duration, boolean isSevere) {
		CustomWeatherData weatherData = new CustomWeatherData(weatherType, duration, isSevere, 5, 20.0);
		addData((GenericCacheManager<String, CustomWeatherData>) getCache("weather"), weatherId, weatherData);
	}

	public static void addEntityData(String entityId, String entityType, double health, BlockPos position) {
		CustomEntityData entityData = new CustomEntityData(entityType, health, position);
		addData((GenericCacheManager<String, CustomEntityData>) getCache("entity"), entityId, entityData);
	}

	public static void addEventData(String eventId, String eventType, String eventDescription, long timestamp) {
		CustomEventData eventData = new CustomEventData(eventType, eventDescription, timestamp);
		addData((GenericCacheManager<String, CustomEventData>) getCache("event"), eventId, eventData);
	}

	public static void addQuestData(String questId, String questName, String questDescription, List<String> objectives, List<String> rewards) {
		CustomQuestData questData = new CustomQuestData(questName, questDescription, objectives, rewards);
		addData((GenericCacheManager<String, CustomQuestData>) getCache("quest"), questId, questData);
	}

	public static void addSkillData(String skillId, String skillName, int skillLevel, String skillEffect) {
		CustomSkillData skillData = new CustomSkillData(skillName, skillLevel, skillEffect);
		addData((GenericCacheManager<String, CustomSkillData>) getCache("skill"), skillId, skillData);
	}

	public static void addStructureData(String structureId, String structureName, String dimension, int size, boolean isGenerated) {
		CustomStructureData structureData = new CustomStructureData(structureName, dimension, size, isGenerated);
		addData((GenericCacheManager<String, CustomStructureData>) getCache("structure"), structureId, structureData);
	}

	public static void addAchievementData(String achievementId, String achievementName, String description, int points, boolean isUnlocked) {
		CustomAchievementData achievementData = new CustomAchievementData(achievementName, description, points, isUnlocked);
		addData((GenericCacheManager<String, CustomAchievementData>) getCache("achievement"), achievementId, achievementData);
	}

	public static void addRecipeData(String recipeId, String recipeName, List<String> ingredients, String resultItem, int resultQuantity) {
		CustomRecipeData recipeData = new CustomRecipeData(recipeName, ingredients, resultItem, resultQuantity);
		addData((GenericCacheManager<String, CustomRecipeData>) getCache("recipe"), recipeId, recipeData);
	}

	public static void addDimensionData(String dimensionId, String dimensionName, String environmentType, int difficultyLevel) {
		CustomDimensionData dimensionData = new CustomDimensionData(dimensionName, environmentType, difficultyLevel);
		addData((GenericCacheManager<String, CustomDimensionData>) getCache("dimension"), dimensionId, dimensionData);
	}

	public static void addNPCData(String npcId, String npcName, String role, String dialogue, double health) {
		CustomNPCData npcData = new CustomNPCData(npcName, role, dialogue, health);
		addData((GenericCacheManager<String, CustomNPCData>) getCache("npc"), npcId, npcData);
	}
}
