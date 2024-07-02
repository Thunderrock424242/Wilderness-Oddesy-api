package net.mcreator.wildernessoddesyapi;

import org.checkerframework.checker.units.qual.K;

import net.minecraft.core.BlockPos;

import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.WeakHashMap;
import java.util.Objects;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

class GenericCacheManager<K, V> {
	private static final Logger LOGGER = Logger.getLogger(GenericCacheManager.class.getName());
	private final Map<K, V> cache = new WeakHashMap<>();
	private final ScheduledExecutorService cacheCleaner = Executors.newSingleThreadScheduledExecutor();

	public GenericCacheManager() {
		cacheCleaner.scheduleAtFixedRate(() -> {
			synchronized (cache) {
				cache.entrySet().removeIf(entry -> entry.getValue() == null);
				LOGGER.info("Cache cleaned up.");
			}
		}, 1, 1, TimeUnit.MINUTES);
	}

	public void put(K key, V value) {
		synchronized (cache) {
			cache.put(key, value);
		}
		LOGGER.fine("Added to cache: " + key);
	}

	public V get(K key) {
		synchronized (cache) {
			V value = cache.get(key);
			LOGGER.fine("Retrieved from cache: " + key + " -> " + value);
			return value;
		}
	}

	public void shutdown() {
		try {
			cacheCleaner.shutdown();
			if (!cacheCleaner.awaitTermination(60, TimeUnit.SECONDS)) {
				cacheCleaner.shutdownNow();
			}
		} catch (InterruptedException e) {
			cacheCleaner.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
}

// Custom player data class
class CustomPlayerData {
	private final String playerName;
	private final int playerLevel;
	private final double playerHealth;
	private final Map<String, Integer> skills;

	public CustomPlayerData(String playerName, int playerLevel, double playerHealth, Map<String, Integer> skills) {
		this.playerName = playerName;
		this.playerLevel = playerLevel;
		this.playerHealth = playerHealth;
		this.skills = new ConcurrentHashMap<>(skills);
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getPlayerLevel() {
		return playerLevel;
	}

	public double getPlayerHealth() {
		return playerHealth;
	}

	public Map<String, Integer> getSkills() {
		return new ConcurrentHashMap<>(skills);
	}

	@Override
	public String toString() {
		return String.format("CustomPlayerData{playerName='%s', playerLevel=%d, playerHealth=%.2f, skills=%s}", playerName, playerLevel, playerHealth, skills);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomPlayerData))
			return false;
		CustomPlayerData that = (CustomPlayerData) o;
		return playerLevel == that.playerLevel && Double.compare(that.playerHealth, playerHealth) == 0 && Objects.equals(playerName, that.playerName) && Objects.equals(skills, that.skills);
	}

	@Override
	public int hashCode() {
		return Objects.hash(playerName, playerLevel, playerHealth, skills);
	}
}

// Enhanced Custom Data Classes with improved equals and hashCode methods
class CustomItemData {
	private final String itemName;
	private final int itemValue;
	private final String rarity;
	private final String description;

	public CustomItemData(String itemName, int itemValue, String rarity, String description) {
		this.itemName = itemName;
		this.itemValue = itemValue;
		this.rarity = rarity;
		this.description = description;
	}

	public String getItemName() {
		return itemName;
	}

	public int getItemValue() {
		return itemValue;
	}

	public String getRarity() {
		return rarity;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return String.format("CustomItemData{itemName='%s', itemValue=%d, rarity='%s', description='%s'}", itemName, itemValue, rarity, description);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomItemData))
			return false;
		CustomItemData that = (CustomItemData) o;
		return itemValue == that.itemValue && Objects.equals(itemName, that.itemName) && Objects.equals(rarity, that.rarity) && Objects.equals(description, that.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemName, itemValue, rarity, description);
	}
}

class CustomBlockData {
	private final String blockType;
	private final int blockHardness;
	private final int resistance;
	private final String toolRequired;

	public CustomBlockData(String blockType, int blockHardness, int resistance, String toolRequired) {
		this.blockType = blockType;
		this.blockHardness = blockHardness;
		this.resistance = resistance;
		this.toolRequired = toolRequired;
	}

	public String getBlockType() {
		return blockType;
	}

	public int getBlockHardness() {
		return blockHardness;
	}

	public int getResistance() {
		return resistance;
	}

	public String getToolRequired() {
		return toolRequired;
	}

	@Override
	public String toString() {
		return String.format("CustomBlockData{blockType='%s', blockHardness=%d, resistance=%d, toolRequired='%s'}", blockType, blockHardness, resistance, toolRequired);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomBlockData))
			return false;
		CustomBlockData that = (CustomBlockData) o;
		return blockHardness == that.blockHardness && resistance == that.resistance && Objects.equals(blockType, that.blockType) && Objects.equals(toolRequired, that.toolRequired);
	}

	@Override
	public int hashCode() {
		return Objects.hash(blockType, blockHardness, resistance, toolRequired);
	}
}

class CustomBiomeData {
	private final String biomeName;
	private final float temperature;
	private final float humidity;
	private final float rainfall;
	private final int altitude;

	public CustomBiomeData(String biomeName, float temperature, float humidity, float rainfall, int altitude) {
		this.biomeName = biomeName;
		this.temperature = temperature;
		this.humidity = humidity;
		this.rainfall = rainfall;
		this.altitude = altitude;
	}

	public String getBiomeName() {
		return biomeName;
	}

	public float getTemperature() {
		return temperature;
	}

	public float getHumidity() {
		return humidity;
	}

	public float getRainfall() {
		return rainfall;
	}

	public int getAltitude() {
		return altitude;
	}

	@Override
	public String toString() {
		return String.format("CustomBiomeData{biomeName='%s', temperature=%.2f, humidity=%.2f, rainfall=%.2f, altitude=%d}", biomeName, temperature, humidity, rainfall, altitude);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomBiomeData))
			return false;
		CustomBiomeData that = (CustomBiomeData) o;
		return Float.compare(that.temperature, temperature) == 0 && Float.compare(that.humidity, humidity) == 0 && Float.compare(that.rainfall, rainfall) == 0 && altitude == that.altitude && Objects.equals(biomeName, that.biomeName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(biomeName, temperature, humidity, rainfall, altitude);
	}
}

class CustomWorldGenData {
	private final String structureName;
	private final int complexity;
	private final double spawnRate;
	private final List<String> biomeTypes;

	public CustomWorldGenData(String structureName, int complexity, double spawnRate, List<String> biomeTypes) {
		this.structureName = structureName;
		this.complexity = complexity;
		this.spawnRate = spawnRate;
		this.biomeTypes = new ArrayList<>(biomeTypes);
	}

	public String getStructureName() {
		return structureName;
	}

	public int getComplexity() {
		return complexity;
	}

	public double getSpawnRate() {
		return spawnRate;
	}

	public List<String> getBiomeTypes() {
		return new ArrayList<>(biomeTypes);
	}

	@Override
	public String toString() {
		return String.format("CustomWorldGenData{structureName='%s', complexity=%d, spawnRate=%.2f, biomeTypes=%s}", structureName, complexity, spawnRate, biomeTypes);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomWorldGenData))
			return false;
		CustomWorldGenData that = (CustomWorldGenData) o;
		return complexity == that.complexity && Double.compare(that.spawnRate, spawnRate) == 0 && Objects.equals(structureName, that.structureName) && Objects.equals(biomeTypes, that.biomeTypes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(structureName, complexity, spawnRate, biomeTypes);
	}
}

class CustomConfigData {
	private final String configName;
	private final String configValue;
	private final String defaultValue;
	private final boolean isEnabled;

	public CustomConfigData(String configName, String configValue, String defaultValue, boolean isEnabled) {
		this.configName = configName;
		this.configValue = configValue;
		this.defaultValue = defaultValue;
		this.isEnabled = isEnabled;
	}

	public String getConfigName() {
		return configName;
	}

	public String getConfigValue() {
		return configValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public String toString() {
		return String.format("CustomConfigData{configName='%s', configValue='%s', defaultValue='%s', isEnabled=%b}", configName, configValue, defaultValue, isEnabled);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomConfigData))
			return false;
		CustomConfigData that = (CustomConfigData) o;
		return isEnabled == that.isEnabled && Objects.equals(configName, that.configName) && Objects.equals(configValue, that.configValue) && Objects.equals(defaultValue, that.defaultValue);
	}

	@Override
	public int hashCode() {
		return Objects.hash(configName, configValue, defaultValue, isEnabled);
	}
}

class CustomPathfindingData {
	private final String entityId;
	private final BlockPos[] path;
	private final boolean isComplete;
	private final int currentStep;

	public CustomPathfindingData(String entityId, BlockPos[] path, boolean isComplete, int currentStep) {
		this.entityId = entityId;
		this.path = path.clone();
		this.isComplete = isComplete;
		this.currentStep = currentStep;
	}

	public String getEntityId() {
		return entityId;
	}

	public BlockPos[] getPath() {
		return path.clone();
	}

	public boolean isComplete() {
		return isComplete;
	}

	public int getCurrentStep() {
		return currentStep;
	}

	@Override
	public String toString() {
		return String.format("CustomPathfindingData{entityId='%s', path=%s, isComplete=%b, currentStep=%d}", entityId, Arrays.toString(path), isComplete, currentStep);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomPathfindingData))
			return false;
		CustomPathfindingData that = (CustomPathfindingData) o;
		return isComplete == that.isComplete && currentStep == that.currentStep && Objects.equals(entityId, that.entityId) && Arrays.equals(path, that.path);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(entityId, isComplete, currentStep);
		result = 31 * result + Arrays.hashCode(path);
		return result;
	}
}

class CustomWeatherData {
	private final String weatherType;
	private final long duration;
	private final boolean isSevere;
	private final int intensity;
	private final double windSpeed;

	public CustomWeatherData(String weatherType, long duration, boolean isSevere, int intensity, double windSpeed) {
		this.weatherType = weatherType;
		this.duration = duration;
		this.isSevere = isSevere;
		this.intensity = intensity;
		this.windSpeed = windSpeed;
	}

	public String getWeatherType() {
		return weatherType;
	}

	public long getDuration() {
		return duration;
	}

	public boolean isSevere() {
		return isSevere;
	}

	public int getIntensity() {
		return intensity;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	@Override
	public String toString() {
		return String.format("CustomWeatherData{weatherType='%s', duration=%d, isSevere=%b, intensity=%d, windSpeed=%.2f}", weatherType, duration, isSevere, intensity, windSpeed);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomWeatherData))
			return false;
		CustomWeatherData that = (CustomWeatherData) o;
		return duration == that.duration && isSevere == that.isSevere && intensity == that.intensity && Double.compare(that.windSpeed, windSpeed) == 0 && Objects.equals(weatherType, that.weatherType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(weatherType, duration, isSevere, intensity, windSpeed);
	}
}

class CustomEntityData {
	private final String entityType;
	private final double health;
	private final BlockPos position;

	public CustomEntityData(String entityType, double health, BlockPos position) {
		this.entityType = entityType;
		this.health = health;
		this.position = position;
	}

	public String getEntityType() {
		return entityType;
	}

	public double getHealth() {
		return health;
	}

	public BlockPos getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return String.format("CustomEntityData{entityType='%s', health=%.2f, position=%s}", entityType, health, position);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomEntityData))
			return false;
		CustomEntityData that = (CustomEntityData) o;
		return Double.compare(that.health, health) == 0 && Objects.equals(entityType, that.entityType) && Objects.equals(position, that.position);
	}

	@Override
	public int hashCode() {
		return Objects.hash(entityType, health, position);
	}
}

class CustomEventData {
	private final String eventType;
	private final String eventDescription;
	private final long timestamp;

	public CustomEventData(String eventType, String eventDescription, long timestamp) {
		this.eventType = eventType;
		this.eventDescription = eventDescription;
		this.timestamp = timestamp;
	}

	public String getEventType() {
		return eventType;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return String.format("CustomEventData{eventType='%s', eventDescription='%s', timestamp=%d}", eventType, eventDescription, timestamp);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomEventData))
			return false;
		CustomEventData that = (CustomEventData) o;
		return timestamp == that.timestamp && Objects.equals(eventType, that.eventType) && Objects.equals(eventDescription, that.eventDescription);
	}

	@Override
	public int hashCode() {
		return Objects.hash(eventType, eventDescription, timestamp);
	}
}

class CustomQuestData {
	private final String questName;
	private final String questDescription;
	private final List<String> objectives;
	private final List<String> rewards;

	public CustomQuestData(String questName, String questDescription, List<String> objectives, List<String> rewards) {
		this.questName = questName;
		this.questDescription = questDescription;
		this.objectives = new ArrayList<>(objectives);
		this.rewards = new ArrayList<>(rewards);
	}

	public String getQuestName() {
		return questName;
	}

	public String getQuestDescription() {
		return questDescription;
	}

	public List<String> getObjectives() {
		return new ArrayList<>(objectives);
	}

	public List<String> getRewards() {
		return new ArrayList<>(rewards);
	}

	@Override
	public String toString() {
		return String.format("CustomQuestData{questName='%s', questDescription='%s', objectives=%s, rewards=%s}", questName, questDescription, objectives, rewards);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomQuestData))
			return false;
		CustomQuestData that = (CustomQuestData) o;
		return Objects.equals(questName, that.questName) && Objects.equals(questDescription, that.questDescription) && Objects.equals(objectives, that.objectives) && Objects.equals(rewards, that.rewards);
	}

	@Override
	public int hashCode() {
		return Objects.hash(questName, questDescription, objectives, rewards);
	}
}

class CustomSkillData {
	private final String skillName;
	private final int skillLevel;
	private final String skillEffect;

	public CustomSkillData(String skillName, int skillLevel, String skillEffect) {
		this.skillName = skillName;
		this.skillLevel = skillLevel;
		this.skillEffect = skillEffect;
	}

	public String getSkillName() {
		return skillName;
	}

	public int getSkillLevel() {
		return skillLevel;
	}

	public String getSkillEffect() {
		return skillEffect;
	}

	@Override
	public String toString() {
		return String.format("CustomSkillData{skillName='%s', skillLevel=%d, skillEffect='%s'}", skillName, skillLevel, skillEffect);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomSkillData))
			return false;
		CustomSkillData that = (CustomSkillData) o;
		return skillLevel == that.skillLevel && Objects.equals(skillName, that.skillName) && Objects.equals(skillEffect, that.skillEffect);
	}

	@Override
	public int hashCode() {
		return Objects.hash(skillName, skillLevel, skillEffect);
	}
}

class CustomStructureData {
	private final String structureName;
	private final String dimension;
	private final int size;
	private final boolean isGenerated;

	public CustomStructureData(String structureName, String dimension, int size, boolean isGenerated) {
		this.structureName = structureName;
		this.dimension = dimension;
		this.size = size;
		this.isGenerated = isGenerated;
	}

	public String getStructureName() {
		return structureName;
	}

	public String getDimension() {
		return dimension;
	}

	public int getSize() {
		return size;
	}

	public boolean isGenerated() {
		return isGenerated;
	}

	@Override
	public String toString() {
		return String.format("CustomStructureData{structureName='%s', dimension='%s', size=%d, isGenerated=%b}", structureName, dimension, size, isGenerated);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomStructureData))
			return false;
		CustomStructureData that = (CustomStructureData) o;
		return size == that.size && isGenerated == that.isGenerated && Objects.equals(structureName, that.structureName) && Objects.equals(dimension, that.dimension);
	}

	@Override
	public int hashCode() {
		return Objects.hash(structureName, dimension, size, isGenerated);
	}
}

class CustomAchievementData {
	private final String achievementName;
	private final String description;
	private final int points;
	private final boolean isUnlocked;

	public CustomAchievementData(String achievementName, String description, int points, boolean isUnlocked) {
		this.achievementName = achievementName;
		this.description = description;
		this.points = points;
		this.isUnlocked = isUnlocked;
	}

	public String getAchievementName() {
		return achievementName;
	}

	public String getDescription() {
		return description;
	}

	public int getPoints() {
		return points;
	}

	public boolean isUnlocked() {
		return isUnlocked;
	}

	@Override
	public String toString() {
		return String.format("CustomAchievementData{achievementName='%s', description='%s', points=%d, isUnlocked=%b}", achievementName, description, points, isUnlocked);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomAchievementData))
			return false;
		CustomAchievementData that = (CustomAchievementData) o;
		return points == that.points && isUnlocked == that.isUnlocked && Objects.equals(achievementName, that.achievementName) && Objects.equals(description, that.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(achievementName, description, points, isUnlocked);
	}
}

class CustomRecipeData {
	private final String recipeName;
	private final List<String> ingredients;
	private final String resultItem;
	private final int resultQuantity;

	public CustomRecipeData(String recipeName, List<String> ingredients, String resultItem, int resultQuantity) {
		this.recipeName = recipeName;
		this.ingredients = new ArrayList<>(ingredients);
		this.resultItem = resultItem;
		this.resultQuantity = resultQuantity;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public List<String> getIngredients() {
		return new ArrayList<>(ingredients);
	}

	public String getResultItem() {
		return resultItem;
	}

	public int getResultQuantity() {
		return resultQuantity;
	}

	@Override
	public String toString() {
		return String.format("CustomRecipeData{recipeName='%s', ingredients=%s, resultItem='%s', resultQuantity=%d}", recipeName, ingredients, resultItem, resultQuantity);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomRecipeData))
			return false;
		CustomRecipeData that = (CustomRecipeData) o;
		return resultQuantity == that.resultQuantity && Objects.equals(recipeName, that.recipeName) && Objects.equals(ingredients, that.ingredients) && Objects.equals(resultItem, that.resultItem);
	}

	@Override
	public int hashCode() {
		return Objects.hash(recipeName, ingredients, resultItem, resultQuantity);
	}
}

class CustomDimensionData {
	private final String dimensionName;
	private final String environmentType;
	private final int difficultyLevel;

	public CustomDimensionData(String dimensionName, String environmentType, int difficultyLevel) {
		this.dimensionName = dimensionName;
		this.environmentType = environmentType;
		this.difficultyLevel = difficultyLevel;
	}

	public String getDimensionName() {
		return dimensionName;
	}

	public String getEnvironmentType() {
		return environmentType;
	}

	public int getDifficultyLevel() {
		return difficultyLevel;
	}

	@Override
	public String toString() {
		return String.format("CustomDimensionData{dimensionName='%s', environmentType='%s', difficultyLevel=%d}", dimensionName, environmentType, difficultyLevel);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomDimensionData))
			return false;
		CustomDimensionData that = (CustomDimensionData) o;
		return difficultyLevel == that.difficultyLevel && Objects.equals(dimensionName, that.dimensionName) && Objects.equals(environmentType, that.environmentType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dimensionName, environmentType, difficultyLevel);
	}
}

class CustomNPCData {
	private final String npcName;
	private final String role;
	private final String dialogue;
	private final double health;

	public CustomNPCData(String npcName, String role, String dialogue, double health) {
		this.npcName = npcName;
		this.role = role;
		this.dialogue = dialogue;
		this.health = health;
	}

	public String getNpcName() {
		return npcName;
	}

	public String getRole() {
		return role;
	}

	public String getDialogue() {
		return dialogue;
	}

	public double getHealth() {
		return health;
	}

	@Override
	public String toString() {
		return String.format("CustomNPCData{npcName='%s', role='%s', dialogue='%s', health=%.2f}", npcName, role, dialogue, health);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CustomNPCData))
			return false;
		CustomNPCData that = (CustomNPCData) o;
		return Double.compare(that.health, health) == 0 && Objects.equals(npcName, that.npcName) && Objects.equals(role, that.role) && Objects.equals(dialogue, that.dialogue);
	}

	@Override
	public int hashCode() {
		return Objects.hash(npcName, role, dialogue, health);
	}
}
