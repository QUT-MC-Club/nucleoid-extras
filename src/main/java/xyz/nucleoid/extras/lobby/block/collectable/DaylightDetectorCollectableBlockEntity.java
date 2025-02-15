package xyz.nucleoid.extras.lobby.block.collectable;

import xyz.nucleoid.extras.lobby.NEBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class DaylightDetectorCollectableBlockEntity extends BlockEntity {
	public DaylightDetectorCollectableBlockEntity(BlockPos pos, BlockState state) {
		super(NEBlocks.DAYLIGHT_DETECTOR_TATER_ENTITY, pos, state);
	}
}
