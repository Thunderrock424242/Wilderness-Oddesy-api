package net.mcreator.wildernessoddesyapi;

import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.mcreator.wildernessoddesyapi.command.ClearItemsCommand;
import net.mcreator.wildernessoddesyapi.command.AdminCommand;
import net.mcreator.wildernessoddesyapi.BiomeFileWriter;

@Mod(WildernessOddessyApi.MOD_ID)
public class WildernessOddessyApi {
    public static final String MOD_ID = "wilderness_oddesy_api";
    private static final Logger LOGGER = LogManager.getLogger();
    public static boolean ENABLE_OUTLINE = false; // Default is false meant to be used in dev environment.

    public WildernessOddessyApi() {
        NeoForge.EVENT_BUS.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.addListener(this::clientSetup);
        NeoForge.EVENT_BUS.addListener(this::onLoadComplete);
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Common setup complete");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Client setup complete");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        ClearItemsCommand.register(event.getServer().getCommands().getDispatcher());
        AdminCommand.register(event.getServer().getCommands().getDispatcher());
        LOGGER.info("Server starting setup complete");
    }

    @SubscribeEvent
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        LOGGER.info("Load Complete Event");
        BiomeFileWriter.writeBiomesToFile();
    }
}
