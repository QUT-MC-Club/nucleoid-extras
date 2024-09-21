package xyz.nucleoid.extras.data.provider;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import xyz.nucleoid.extras.lobby.NEBlocks;
import xyz.nucleoid.extras.tag.NEBlockTags;

public class NEBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public NEBlockTagProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(dataOutput, registries);
    }

    @Override
    protected void configure(WrapperLookup lookup) {
        this.getOrCreateTagBuilder(BlockTags.DOORS)
                .add(NEBlocks.TRANSIENT_IRON_DOOR);

        this.getOrCreateTagBuilder(NEBlockTags.NON_VIBRATING_TATERS)
                .addOptionalTag(BlockTags.DAMPENS_VIBRATIONS);

        this.getOrCreateTagBuilder(BlockTags.WOODEN_DOORS)
                .add(NEBlocks.TRANSIENT_OAK_DOOR)
                .add(NEBlocks.TRANSIENT_SPRUCE_DOOR)
                .add(NEBlocks.TRANSIENT_BIRCH_DOOR)
                .add(NEBlocks.TRANSIENT_JUNGLE_DOOR)
                .add(NEBlocks.TRANSIENT_ACACIA_DOOR)
                .add(NEBlocks.TRANSIENT_CHERRY_DOOR)
                .add(NEBlocks.TRANSIENT_DARK_OAK_DOOR)
                .add(NEBlocks.TRANSIENT_MANGROVE_DOOR)
                .add(NEBlocks.TRANSIENT_BAMBOO_DOOR)
                .add(NEBlocks.TRANSIENT_CRIMSON_DOOR)
                .add(NEBlocks.TRANSIENT_WARPED_DOOR);
    }
}
