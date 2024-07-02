package net.mcreator.wildernessoddesyapi.goals;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.core.BlockPos;

import net.mcreator.wildernessoddesyapi.accessor.LevelAccessor;

import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;

public class SeekShelterDuringStormGoal extends Goal {
    protected final Animal animal;
    protected static final int SHELTER_RADIUS = 1600; // 100 chunks * 16 blocks per chunk
    protected static final int TREE_RADIUS = 800; // 50 chunks * 16 blocks per chunk
    protected static final int CAVE_MIN_HEIGHT = 2;
    private static final Logger LOGGER = LoggerFactory.getLogger(SeekShelterDuringStormGoal.class);

    public SeekShelterDuringStormGoal(Animal animal) {
        this.animal = animal;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        Level level = ((LevelAccessor) this.animal).getLevel();
        boolean canUse = level.isRaining() || level.isThundering();
        if (canUse) {
            LOGGER.warn("Shelter seeking goal can be used for: " + this.animal.getType().toString());
        }
        return canUse;
    }

    @Override
    public void start() {
        BlockPos shelter = this.findShelter();
        if (shelter != null) {
            LOGGER.warn("Shelter found for " + this.animal.getType().toString() + " at " + shelter);
            this.animal.getNavigation().moveTo(shelter.getX(), shelter.getY(), shelter.getZ(), 1.0D);
        } else {
            LOGGER.warn("No shelter found for " + this.animal.getType().toString());
        }
    }

    @Override
    public boolean canContinueToUse() {
        Level level = ((LevelAccessor) this.animal).getLevel();
        return level.isRaining() || level.isThundering();
    }

    protected BlockPos findShelter() {
        Level level = ((LevelAccessor) this.animal).getLevel();
        BlockPos currentPos = this.animal.blockPosition();
        BlockPos shelter = findCaveShelter(level, currentPos);
        if (shelter == null) {
            shelter = findTreeShelter(level, currentPos);
        }
        return shelter;
    }

    protected BlockPos findCaveShelter(Level level, BlockPos currentPos) {
        for (int dx = -SHELTER_RADIUS; dx <= SHELTER_RADIUS; dx++) {
            for (int dz = -SHELTER_RADIUS; dz <= SHELTER_RADIUS; dz++) {
                for (int dy = -SHELTER_RADIUS / 2; dy <= SHELTER_RADIUS / 2; dy++) {
                    BlockPos pos = currentPos.offset(dx, dy, dz);
                    if (level.getBlockState(pos).isAir() && isCave(level, pos)) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    protected boolean isCave(Level level, BlockPos pos) {
        int solidSides = 0;
        if (level.getBlockState(pos.north()).isSolidRender(level, pos.north()))
            solidSides++;
        if (level.getBlockState(pos.south()).isSolidRender(level, pos.south()))
            solidSides++;
        if (level.getBlockState(pos.west()).isSolidRender(level, pos.west()))
            solidSides++;
        if (level.getBlockState(pos.east()).isSolidRender(level, pos.east()))
            solidSides++;
        return solidSides >= 3 && pos.getY() > CAVE_MIN_HEIGHT;
    }

    protected BlockPos findTreeShelter(Level level, BlockPos currentPos) {
        Set<BlockState> leafBlocks = new HashSet<>();
        leafBlocks.add(Blocks.OAK_LEAVES.defaultBlockState());
        leafBlocks.add(Blocks.SPRUCE_LEAVES.defaultBlockState());
        leafBlocks.add(Blocks.BIRCH_LEAVES.defaultBlockState());
        leafBlocks.add(Blocks.JUNGLE_LEAVES.defaultBlockState());
        leafBlocks.add(Blocks.ACACIA_LEAVES.defaultBlockState());
        leafBlocks.add(Blocks.DARK_OAK_LEAVES.defaultBlockState());
        leafBlocks.add(Blocks.MANGROVE_LEAVES.defaultBlockState());
        for (int dx = -TREE_RADIUS; dx <= TREE_RADIUS; dx++) {
            for (int dz = -TREE_RADIUS; dz <= TREE_RADIUS; dz++) {
                BlockPos pos = currentPos.offset(dx, 0, dz);
                if (leafBlocks.contains(level.getBlockState(pos.above())) && canFitUnderTree(level, pos)) {
                    return pos;
                }
            }
        }
        return null;
    }

    protected boolean canFitUnderTree(Level level, BlockPos pos) {
        for (int dy = 1; dy <= this.animal.getBbHeight(); dy++) {
            if (!level.getBlockState(pos.above(dy)).isAir()) {
                return false;
            }
        }
        return true;
    }
}
