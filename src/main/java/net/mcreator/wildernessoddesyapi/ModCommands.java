/*package net.mcreator.wildernessoddesyapi.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.mcreator.wildernessoddesyapi.WildernessOddessyApi;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.server.FMLServerStartingEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WildernessOddessyApi.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModCommands {

    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event) {
        registerCommands(event.getServer().getCommands().getDispatcher());
    }

    private static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("setOutlineEnabled")
                .then(Commands.argument("enabled", BoolArgumentType.bool())
                        .executes(context -> {
                            boolean enabled = BoolArgumentType.getBool(context, "enabled");
                            WildernessOddessyApi.ENABLE_OUTLINE = enabled;
                            context.getSource().sendSuccess(
                                    () -> net.minecraft.network.chat.Component.literal("Entity outline feature set to: " + enabled), true);
                            return 1;
                        })
                )
        );
    }
}
*/
///  put it like this since code is broken waiting for help.