/*package net.mcreator.wildernessoddesyapi;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelLastEvent;

@EventBusSubscriber(modid = WildernessOddessyApi.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityOutlineRenderer {

    private static final double CHUNK_RADIUS = 30 * 16; // 30 chunks radius

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderWorldLast(RenderLevelLastEvent event) {
        if (WildernessOddessyApi.ENABLE_OUTLINE) { // Check the flag
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player != null) {
                Vec3 playerPos = player.position();
                AABB boundingBox = new AABB(playerPos.x - CHUNK_RADIUS, playerPos.y - CHUNK_RADIUS, playerPos.z - CHUNK_RADIUS,
                                            playerPos.x + CHUNK_RADIUS, playerPos.y + CHUNK_RADIUS, playerPos.z + CHUNK_RADIUS);
                for (Entity entity : mc.level.getEntities(null, boundingBox)) {
                    outlineEntity(entity, event.getPartialTick());
                }
            }
        }
    }

    private static void outlineEntity(Entity entity, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        EntityRenderer<? super Entity> renderer = mc.getEntityRenderDispatcher().getRenderer(entity);
        if (renderer != null) {
            PoseStack poseStack = new PoseStack();
            MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
            renderer.render(entity, entity.getYRot(), partialTicks, poseStack, bufferSource, mc.getEntityRenderDispatcher().getPackedLightCoords(entity, partialTicks));
            bufferSource.endBatch(RenderType.outline(renderer.getTextureLocation(entity)));
        }
    }
}
*/
// put it like this since code is broken waiting for help.