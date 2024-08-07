/*package net.mcreator.wildernessoddesyapi.util;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.minecraft.world.level.gameevent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import java.util.EnumSet;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = "wildernessoddesyapi")
public class AIUtil {

    private static final int AI_UPDATE_INTERVAL = 20; // Update AI every second
    private static int tickCounter = 0;

    public static class OptimizedGoal extends Goal {
        private final Mob mob;
        private final int updateInterval;
        private int lastUpdateTick;

        public OptimizedGoal(Mob mob, int updateInterval) {
            this.mob = mob;
            this.updateInterval = updateInterval;
            this.lastUpdateTick = 0;
            this.setFlags(EnumSet.noneOf(Flag.class));
        }

        @Override
        public boolean canUse() {
            return (mob.tickCount - lastUpdateTick) >= updateInterval;
        }

        @Override
        public void start() {
            this.lastUpdateTick = mob.tickCount;
        }

        @Override
        public void stop() {
            // Cleanup or reset any variables if necessary
        }

        @Override
        public void tick() {
            // Implement the AI task logic here
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        Level world = event.world;
        if (!world.isClientSide && event.phase == TickEvent.Phase.END) {
            tickCounter++;
            if (tickCounter >= AI_UPDATE_INTERVAL) {
                tickCounter = 0;
                // Perform AI updates or other tasks here
                updateAIForEntities(world);
            }
        }
    }

    private static void updateAIForEntities(Level world) {
        if (world instanceof ServerLevel) {
            ServerLevel serverWorld = (ServerLevel) world;
            Predicate<Entity> filter = entity -> entity instanceof Mob;
            serverWorld.getEntities((EntityType<Entity>) EntityType.MOB, new AABB(0, 0, 0, 1000, 1000, 1000), filter)
                .forEach(entity -> {
                    if (entity instanceof Mob) {
                        Mob mob = (Mob) entity;
                        // Check if the mob's AI should be updated
                        if (shouldUpdateAI(mob)) {
                            // Update the AI for this mob
                            mob.getNavigation().tick();
                        }
                    }
                });
        }
    }

    private static boolean shouldUpdateAI(Mob mob) {
        // Implement logic to determine if the mob's AI should be updated
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        // Implement any client-side AI optimizations if necessary
    }
}
*/