package xyz.nucleoid.extras.lobby.block.collectable;

import eu.pb4.polymer.core.api.block.PolymerHeadBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationPropertyHelper;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.ArrayList;
import java.util.List;

public class CubicCollectableBlock extends TinyPotatoBlock implements PolymerHeadBlock {
    protected static final List<CubicCollectableBlock> CUBIC_TATERS = new ArrayList<>();

    public CubicCollectableBlock(Settings settings, ParticleEffect particleEffect, String texture, int particleRate) {
        super(settings, texture, particleEffect, particleRate);
        CUBIC_TATERS.add(this);
    }

    public CubicCollectableBlock(Settings settings, ParticleEffect particleEffect, String texture) {
        this(settings, particleEffect, texture, 2);
    }

    public CubicCollectableBlock(Settings settings, BlockState particleState, String texture) {
        this(settings, new BlockStateParticleEffect(ParticleTypes.BLOCK, particleState), texture);
    }

    public CubicCollectableBlock(Settings settings, Block particleBlock, String texture) {
        this(settings, particleBlock.getDefaultState(), texture);
    }

    public CubicCollectableBlock(Settings settings, ItemStack particleStack, String texture) {
        this(settings, new ItemStackParticleEffect(ParticleTypes.ITEM, particleStack), texture);
    }

    public CubicCollectableBlock(Settings settings, Item particleItem, String texture) {
        this(settings, new ItemStack(particleItem), texture);
    }

    @Override
    public String getPolymerSkinValue(BlockState state, BlockPos pos, PacketContext context) {
        return this.getItemTexture();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.ROTATION);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(Properties.ROTATION, MathHelper.floor(RotationPropertyHelper.fromYaw(ctx.getPlayerYaw())));
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return Blocks.PLAYER_HEAD.getDefaultState().with(Properties.ROTATION, state.get(Properties.ROTATION));
    }
}
