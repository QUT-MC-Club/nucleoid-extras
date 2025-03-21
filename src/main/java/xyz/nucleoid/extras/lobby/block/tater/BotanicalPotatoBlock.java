package xyz.nucleoid.extras.lobby.block.tater;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.polymer.core.api.utils.PolymerUtils;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.nucleoid.extras.lobby.particle.TaterParticleSpawner;
import xyz.nucleoid.extras.lobby.particle.TaterParticleSpawnerTypes;
import xyz.nucleoid.extras.util.SkinEncoder;
import xyz.nucleoid.packettweaker.PacketContext;

public class BotanicalPotatoBlock extends TinyPotatoBlock implements BlockWithElementHolder {
    public static final MapCodec<BotanicalPotatoBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
                createSettingsCodec(),
                TaterParticleSpawnerTypes.CODEC.fieldOf("particle_spawner").forGetter(BotanicalPotatoBlock::getParticleSpawner),
                Codec.STRING.fieldOf("upper_texture").forGetter(BotanicalPotatoBlock::getItemTexture),
                Codec.STRING.fieldOf("lower_texture").forGetter(b -> b.lowerTexture)
        ).apply(instance, BotanicalPotatoBlock::new)
    );

    private final String lowerTexture;

    private final ItemStack upStack;
    private final ItemStack downStack;

    public BotanicalPotatoBlock(Settings settings, TaterParticleSpawner particleSpawner, String upperTexture, String lowerTexture) {
        super(settings.nonOpaque(), particleSpawner, upperTexture);

        this.lowerTexture = lowerTexture;

        this.upStack = PolymerUtils.createPlayerHead(this.getItemTexture());
        this.downStack = PolymerUtils.createPlayerHead(SkinEncoder.encode(lowerTexture));
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
        return Blocks.BARRIER.getDefaultState();
    }

    @Override
    public ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new Model(initialBlockState);
    }

    @Override
    public boolean tickElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return true;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        var model = (Model) BlockBoundAttachment.get(world, pos).holder();

        if (model.jumpTime < 0) {
            model.jumpTime = 20;
        }

        return super.onUse(state, world, pos, player, hit);
    }

    @Override
    public MapCodec<? extends BotanicalPotatoBlock> getCodec() {
        return CODEC;
    }

    private class Model extends ElementHolder {
        private final ItemDisplayElement upPart;
        private final ItemDisplayElement downPart;

        private int jumpTime = 0;
        private BlockState state;
        private final Matrix4f mat = new Matrix4f();

        Model(BlockState state) {
            this.upPart = new ItemDisplayElement(BotanicalPotatoBlock.this.upStack);
            this.downPart = new ItemDisplayElement(BotanicalPotatoBlock.this.downStack);

            this.upPart.setModelTransformation(ModelTransformationMode.FIXED);
            this.downPart.setModelTransformation(ModelTransformationMode.FIXED);

            this.upPart.setInterpolationDuration(1);
            this.downPart.setInterpolationDuration(1);

            this.upPart.setInvisible(true);
            this.downPart.setInvisible(true);

            this.state = state;
            this.updateAnimation();

            this.addElement(this.upPart);
            this.addElement(this.downPart);
        }

        @Override
        protected void onTick() {
            if (this.jumpTime >= 0) {
                this.updateAnimation();
            }

            this.jumpTime--;
        }

        private void updateAnimation() {
            mat.identity();
            mat.rotateY(-RotationPropertyHelper.toDegrees(state.get(Properties.ROTATION)) * MathHelper.RADIANS_PER_DEGREE);

            if (this.jumpTime > 0) {
                // Math stolen from botania™
                // https://github.com/VazkiiMods/Botania/blob/bd5c644356fa0456efc3773c8829517f1f2c5808/Xplat/src/main/java/vazkii/botania/client/render/block_entity/TinyPotatoBlockEntityRenderer.java#L139
                float up = (float) Math.abs(Math.sin(this.jumpTime / 10f * Math.PI)) * 0.2F;
                float rotZ = (float) Math.sin(this.jumpTime / 10f * Math.PI) * 2;
                float wiggle = (float) Math.sin(this.jumpTime / 10f * Math.PI) * 0.05F;

                mat.translate(wiggle, up, 0F);
                mat.rotate(RotationAxis.POSITIVE_Z.rotationDegrees(rotZ));
            }
            mat.rotateY(MathHelper.PI);

            this.upPart.setTransformation(mat);
            mat.translate(new Vector3f(0, -0.25f, 0));
            this.downPart.setTransformation(mat);

            this.upPart.startInterpolation();
            this.downPart.startInterpolation();
        }

        @Override
        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
            if (updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE) {
                this.state = BlockBoundAttachment.get(this).getBlockState();
                updateAnimation();
            }
        }
    }
}
