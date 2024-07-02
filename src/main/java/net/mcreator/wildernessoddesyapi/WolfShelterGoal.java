package net.mcreator.wildernessoddesyapi.goals;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.core.BlockPos;

import net.mcreator.wildernessoddesyapi.accessor.LevelAccessor;

public class WolfShelterGoal extends SeekShelterDuringStormGoal {
    private static final Logger LOGGER = LoggerFactory.getLogger(WolfShelterGoal.class);

    public WolfShelterGoal(Animal animal) {
        super(animal);
    }

    @Override
    protected BlockPos findShelter() {
        Level level = ((LevelAccessor) this.animal).getLevel();
        BlockPos currentPos = this.animal.blockPosition();
        BlockPos shelter = findCaveShelter(level, currentPos);
        if (shelter == null) {
            shelter = findStructureShelter(level, currentPos);
        }
        return shelter;
    }

    private BlockPos findStructureShelter(Level level, BlockPos currentPos) {
        for (int dx = -SHELTER_RADIUS; dx <= SHELTER_RADIUS; dx++) {
            for (int dz = -SHELTER_RADIUS; dz <= SHELTER_RADIUS; dz++) {
                for (int dy = -SHELTER_RADIUS / 2; dy <= SHELTER_RADIUS / 2; dy++) {
                    BlockPos pos = currentPos.offset(dx, dy, dz);
                    if (level.getBlockState(pos).isSolidRender(level, pos) && isStructure(level, pos)) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    private boolean isStructure(Level level, BlockPos pos) {
        return level.getBlockState(pos).is(Blocks.BRICKS);
    }
}
