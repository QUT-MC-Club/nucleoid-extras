package xyz.nucleoid.extras.lobby.block;

import com.mojang.serialization.MapCodec;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import xyz.nucleoid.packettweaker.PacketContext;

public class SnakeBlock extends FacingBlock implements PolymerBlock {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");

    private final BlockState virtualBlockState;
    private final int delay;
    private final int length;
    private final boolean powered;

    public SnakeBlock(Settings settings, BlockState virtualBlockState, int delay, int length, boolean powered) {
        super(settings);

        this.virtualBlockState = virtualBlockState;
        this.delay = delay;
        this.length = length;
        this.powered = powered;

        this.setDefaultState(this.stateManager.getDefaultState()
            .with(FACING, Direction.NORTH)
            .with(ACTIVE, false)
            .with(POWERED, powered));
    }

    private boolean isActive(BlockState state) {
        return state.get(ACTIVE);
    }

    private boolean providesPower(BlockState state) {
        return state.get(POWERED);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return this.isActive(state) ? this.virtualBlockState : Blocks.BROWN_MUSHROOM.getDefaultState();
    }
    
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return this.isActive(state) ? 15 : 0;
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return this.providesPower(state);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return (this.providesPower(state) && this.isActive(state)) ? 15 : 0;
    }
    
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.isActive(state) ? VoxelShapes.fullCube() : VoxelShapes.empty();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, WireOrientation wireOrientation, boolean notify) {
        boolean powered = world.isReceivingRedstonePower(pos);
        boolean active = this.isActive(state);
        if (powered && !active) {
            if (sourceBlock instanceof SnakeBlock sourceSnakeBlock) {
                if (sourceSnakeBlock.isActive(sourceSnakeBlock.getDefaultState())) {
                    return;
                }
            }
            this.scheduleTick(world, pos, 1);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.isActive(state)) {
            world.setBlockState(pos, state.with(ACTIVE, false));
        } else {
            world.setBlockState(pos, state.with(ACTIVE, true));

            this.scheduleTick(world, pos.offset(state.get(FACING)), 1);
            this.scheduleTick(world, pos, this.length);
        }
    }

    private void scheduleTick(World world, BlockPos pos, int multiplier) {
        world.scheduleBlockTick(pos, this, this.delay * multiplier);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(FACING, mirror.apply(state.get(FACING)));
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(ACTIVE);
        builder.add(POWERED);
    }

    @Override
    protected MapCodec<? extends FacingBlock> getCodec() {
        return null;
    }
}
