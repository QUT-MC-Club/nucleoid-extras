package xyz.nucleoid.extras.lobby.block.collectable;

import net.minecraft.particle.ParticleTypes;

public class ElderGuardianParticleCollectable extends CubicCollectableBlock {
	public ElderGuardianParticleCollectable(Settings settings, String texture) {
		super(settings, ParticleTypes.ELDER_GUARDIAN, texture, 10000);
	}

	@Override
	public int getBlockParticleChance() {
		return 50;
	}
}
