
package net.mcreator.wildernessoddesyapi;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class GenericCacheManager<K, V> {
    private final Map<K, WeakReference<V>> cache = new ConcurrentHashMap<>();
    private final ExecutorService cacheCleaner = Executors.newSingleThreadExecutor();

    public GenericCacheManager() {
        // Periodically clean up the cache
        cacheCleaner.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(60000); // Run every minute
                    cache.entrySet().removeIf(entry -> entry.getValue().get() == null);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }

    public void put(K key, V value) {
        cache.put(key, new WeakReference<>(value));
    }

    public V get(K key) {
        WeakReference<V> reference = cache.get(key);
        return reference != null ? reference.get() : null;
    }
}

class CustomItemData {
    private final String itemName;
    private final int itemValue;

    public CustomItemData(String itemName, int itemValue) {
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemValue() {
        return itemValue;
    }
}

class CustomPlayerData {
    private final String playerName;
    private final int playerLevel;

    public CustomPlayerData(String playerName, int playerLevel) {
        this.playerName = playerName;
        this.playerLevel = playerLevel;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }
}

class CustomBlockData {
    private final String blockType;
    private final int blockHardness;

    public CustomBlockData(String blockType, int blockHardness) {
        this.blockType = blockType;
        this.blockHardness = blockHardness;
    }

    public String getBlockType() {
        return blockType;
    }

    public int getBlockHardness() {
        return blockHardness;
    }
}

class CustomBiomeData {
    private final String biomeName;
    private final float temperature;
    private final float humidity;

    public CustomBiomeData(String biomeName, float temperature, float humidity) {
        this.biomeName = biomeName;
        this.temperature = temperature;
        this.humidity = humidity;
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
}

class CustomWorldGenData {
    private final String structureName;
    private final int complexity;

    public CustomWorldGenData(String structureName, int complexity) {
        this.structureName = structureName;
        this.complexity = complexity;
    }

    public String getStructureName() {
        return structureName;
    }

    public int getComplexity() {
        return complexity;
    }
}

class CustomConfigData {
    private final String configName;
    private final String configValue;

    public CustomConfigData(String configName, String configValue) {
        this.configName = configName;
        this.configValue = configValue;
    }

    public String getConfigName() {
        return configName;
    }

    public String getConfigValue() {
        return configValue;
    }
}

class CustomPathfindingData {
    private final String entityId;
    private final BlockPos[] path;

    public CustomPathfindingData(String entityId, BlockPos[] path) {
        this.entityId = entityId;
        this.path = path;
    }

    public String getEntityId() {
        return entityId;
    }

    public BlockPos[] getPath() {
        return path;
    }
}

class CustomWeatherData {
    private final String weatherType;
    private final long duration;
    private final boolean isSevere;

    public CustomWeatherData(String weatherType, long duration, boolean isSevere) {
        this.weatherType = weatherType;
        this.duration = duration;
        this.isSevere = isSevere;
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
}
