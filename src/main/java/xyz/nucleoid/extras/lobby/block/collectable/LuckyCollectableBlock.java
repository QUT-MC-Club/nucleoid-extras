package xyz.nucleoid.extras.lobby.block.collectable;

import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import xyz.nucleoid.extras.tag.NEBlockTags;
import xyz.nucleoid.extras.util.SkinEncoder;
import xyz.nucleoid.packettweaker.PacketContext;

public class LuckyCollectableBlock extends CubicCollectableBlock {
    private static final EnumProperty<LuckyCollectablePhase> PHASE = EnumProperty.of("phase", LuckyCollectablePhase.class);

    private static final int COURAGE_TICKS = 5;
    private static final int COOLDOWN_TICKS = SharedConstants.TICKS_PER_MINUTE * 30;

    private final String cooldownTexture;

    public LuckyCollectableBlock(Settings settings, String texture, String cooldownTexture) {
        super(settings, (ParticleEffect) null, texture);
        this.cooldownTexture = SkinEncoder.encode(cooldownTexture);

        this.setDefaultState(this.stateManager.getDefaultState().with(PHASE, LuckyCollectablePhase.READY));
    }

    @Override
    public ParticleEffect getPlayerParticleEffect(ServerPlayerEntity player) {
        int fromColor = LuckyCollectableBlock.getRandomColor(player.getRandom());
        int toColor = LuckyCollectableBlock.getRandomColor(player.getRandom());

        int scale = player.getRandom().nextInt(3);
        return new DustColorTransitionParticleEffect(fromColor, toColor, scale);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        LuckyCollectablePhase phase = state.get(PHASE);

        if (phase != LuckyCollectablePhase.READY) {
            return ActionResult.FAIL;
        }

        if (world instanceof ServerWorld serverWorld) {
            LuckyCollectableDropPos dropPos = this.getDropPos(serverWorld, state, pos);

            if (dropPos instanceof LuckyCollectableDropPos.Blocked) {
                world.setBlockState(pos, state.with(PHASE, LuckyCollectablePhase.BUILDING_COURAGE));
                world.scheduleBlockTick(pos, this, COURAGE_TICKS);
            } else {
                Block drop = this.getDrop(serverWorld);

                if (drop instanceof CubicCollectableBlock taterDrop && dropPos instanceof LuckyCollectableDropPos.Allowed allowed) {
                    BlockState dropState = drop.getDefaultState();
                    if (dropState.contains(Properties.ROTATION)) {
                        dropState = dropState.with(Properties.ROTATION, state.get(Properties.ROTATION));
                    }

                    world.setBlockState(allowed.pos(), dropState);

                    // Spawn particles
                    ParticleEffect particleEffect = taterDrop.getBlockParticleEffect(taterDrop.getDefaultState(), serverWorld, pos, player, hit);
                    this.spawnBlockParticles(serverWorld, pos, particleEffect);

                    // Play sound
                    float pitch = 0.5f + world.getRandom().nextFloat() * 0.4f;
                    world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1, pitch);

                    // Start cooldown
                    world.setBlockState(pos, state.with(PHASE, LuckyCollectablePhase.COOLDOWN));
                    world.scheduleBlockTick(pos, this, COOLDOWN_TICKS);
                }
            }
        }

        return ActionResult.SUCCESS_SERVER;
    }

    private Block getDrop(ServerWorld world) {
        var drops = world.getRegistryManager()
                .getOrThrow(RegistryKeys.BLOCK)
                .getOptional(NEBlockTags.LUCKY_TATER_DROPS);

        if (drops.isEmpty()) {
            return null;
        }

        var builder = DataPool.<Block>builder();

        for (RegistryEntry<Block> entry : drops.get()) {
            Block block = entry.value();
            int weight = block instanceof LuckyCollectableDrop drop ? drop.getWeight() : 1;

            builder.add(block, weight);
        }

        return builder
            .build()
            .getDataOrEmpty(world.getRandom())
            .orElse(null);
    }

    private LuckyCollectableDropPos getDropPos(ServerWorld world, BlockState state, BlockPos pos) {
        BlockPos.Mutable dropPos = pos.mutableCopy();
        dropPos.move(Direction.DOWN);

        int rotation = state.get(Properties.ROTATION);
        dropPos.move(Direction.fromHorizontalDegrees(rotation * 22.5).getOpposite());

        for (int i = 0; i < 3; i++) {
            BlockState dropState = world.getBlockState(dropPos);
            if (dropState.getBlock() instanceof CubicCollectableBlock) {
                return new LuckyCollectableDropPos.Blocked(dropPos);
            } else if (dropState.isAir()) {
                return new LuckyCollectableDropPos.Allowed(dropPos);
            }

            dropPos.move(Direction.UP);
        }

        return LuckyCollectableDropPos.None.INSTANCE;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        LuckyCollectablePhase phase = state.get(PHASE);

        if (phase == LuckyCollectablePhase.BUILDING_COURAGE || phase == LuckyCollectablePhase.COOLDOWN) {
            if (phase == LuckyCollectablePhase.BUILDING_COURAGE) {
                LuckyCollectableDropPos dropPos = this.getDropPos(world, state, pos);

                if (dropPos instanceof LuckyCollectableDropPos.Blocked blocked) {
                    world.breakBlock(blocked.pos(), false);
                }
            }

            world.setBlockState(pos, state.with(PHASE, LuckyCollectablePhase.READY));
        }
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(PHASE).getComparatorOutput();
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(PHASE);
    }

    @Override
    public String getPolymerSkinValue(BlockState state, BlockPos pos, PacketContext context) {
        return state.get(PHASE) == LuckyCollectablePhase.COOLDOWN ? this.cooldownTexture : super.getPolymerSkinValue(state, pos, context);
    }

    private static int getRandomColor(Random random) {
        return random.nextInt() * 0xFFFFFF;
    }
}
