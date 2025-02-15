package xyz.nucleoid.extras.lobby.block.collectable;

import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.DyeColor;

public class ColorCollectableBlock extends CubicCollectableBlock {
    public ColorCollectableBlock(Settings settings, int color, String texture) {
        super(settings, new DustParticleEffect(color, 1), texture);
    }

    public ColorCollectableBlock(Settings settings, DyeColor color, String texture) {
        this(settings, color.getSignColor(), texture);
    }
}
