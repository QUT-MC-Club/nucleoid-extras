package xyz.nucleoid.extras.lobby.block.collectable;

import net.minecraft.util.math.BlockPos;

public sealed interface LuckyCollectableDropPos {
    public record Allowed(BlockPos pos) implements LuckyCollectableDropPos {}

    public record Blocked(BlockPos pos) implements LuckyCollectableDropPos {}

    public final class None implements LuckyCollectableDropPos {
        public static final None INSTANCE = new None();

        private None() {
            return;
        }
    }
}
