package net.mcreator.wildernessoddesyapi;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BiomeFileWriter {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String BIOME_FILE_PATH = "biomes.txt";

    public static void writeBiomesToFile() {
        Path path = Paths.get(BIOME_FILE_PATH);
        StringBuilder biomesContent = new StringBuilder();

        biomesContent.append("Biomes List:\n");

        BuiltInRegistries.BIOME.entrySet().forEach(entry -> {
            ResourceLocation key = entry.getKey();
            biomesContent.append(key.toString()).append("\n");
        });

        try {
            String existingContent = "";
            if (Files.exists(path)) {
                existingContent = new String(Files.readAllBytes(path));
                if (existingContent.contains("Custom Section:")) {
                    existingContent = existingContent.substring(existingContent.indexOf("Custom Section:"));
                } else {
                    existingContent = "\nCustom Section:\n";
                }
            }

            Files.write(path, (biomesContent.toString() + "\n" + existingContent).getBytes());
            LOGGER.info("Biome list written to file successfully.");
        } catch (IOException e) {
            LOGGER.error("Failed to write biome list to file.", e);
        }
    }
}
