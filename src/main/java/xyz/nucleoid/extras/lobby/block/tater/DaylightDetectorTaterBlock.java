package xyz.nucleoid.extras.lobby.block.tater;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.extras.lobby.NEBlocks;
import xyz.nucleoid.extras.lobby.particle.SimpleTaterParticleSpawner;
import xyz.nucleoid.extras.lobby.particle.TaterParticleSpawner;
import xyz.nucleoid.extras.lobby.particle.TaterParticleSpawnerTypes;
import xyz.nucleoid.extras.mixin.BlockWithEntityAccessor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class DaylightDetectorTaterBlock extends CubicPotatoBlock implements BlockEntityProvider {
	public static final MapCodec<DaylightDetectorTaterBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
		instance.group(
				createSettingsCodec(),
				TaterParticleSpawnerTypes.CODEC.fieldOf("particle_spawner").forGetter(DaylightDetectorTaterBlock::getParticleSpawner),
				Codec.STRING.fieldOf("texture").forGetter(DaylightDetectorTaterBlock::getItemTexture),
				Codec.BOOL.fieldOf("inverted").forGetter(tater -> tater.inverted)
		).apply(instance, DaylightDetectorTaterBlock::new)
	);

	public static final IntProperty POWER = Properties.POWER;

	public final boolean inverted;

	public DaylightDetectorTaterBlock(Settings settings, TaterParticleSpawner particleSpawner, String texture, boolean inverted) {
		super(settings, particleSpawner, texture);
		this.inverted = inverted;
		this.setDefaultState(this.stateManager.getDefaultState().with(POWER, 0));
	}

	public DaylightDetectorTaterBlock(Settings settings, String texture, boolean inverted) {
		this(settings, new SimpleTaterParticleSpawner(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DAYLIGHT_DETECTOR.getDefaultState().with(Properties.INVERTED, inverted))), texture, inverted);
	}

	@Override
	public boolean emitsRedstonePower(BlockState state) {
		return true;
	}

	@Override
	public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return state.get(POWER);
	}

	private static void updateState(BlockState state, World world, BlockPos pos) {
		int power = world.getLightLevel(LightType.SKY, pos) - world.getAmbientDarkness();
		float skyAngle = world.getSkyAngleRadians(1.0f);
		boolean inverted = ((DaylightDetectorTaterBlock) state.getBlock()).inverted;
		if (inverted) {
			power = 15 - power;
		} else if (power > 0) {
			float g = skyAngle < (float)Math.PI ? 0.0f : (float)Math.PI * 2;
			skyAngle += (g - skyAngle) * 0.2f;
			power = Math.round((float)power * MathHelper.cos(skyAngle));
		}
		power = MathHelper.clamp(power, 0, 15);
		if (state.get(POWER) != power) {
			world.setBlockState(pos, state.with(POWER, power), Block.NOTIFY_ALL);
		}
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new DaylightDetectorTaterBlockEntity(pos, state);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		if (!world.isClient && world.getDimension().hasSkyLight()) {
			return BlockWithEntityAccessor.validateTicker(type, NEBlocks.DAYLIGHT_DETECTOR_TATER_ENTITY, DaylightDetectorTaterBlock::tick);
		}
		return null;
	}

	private static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, T blockEntity) {
		if (world.getTime() % 20L == 0L) {
			DaylightDetectorTaterBlock.updateState(state, world, pos);
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(POWER);
	}

	@Override
	public MapCodec<? extends DaylightDetectorTaterBlock> getCodec() {
		return CODEC;
	}
}
