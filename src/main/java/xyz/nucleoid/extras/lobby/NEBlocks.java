package xyz.nucleoid.extras.lobby;

import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import eu.pb4.polymer.core.api.block.SimplePolymerBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import xyz.nucleoid.extras.NucleoidExtras;
import xyz.nucleoid.extras.lobby.block.*;
import xyz.nucleoid.extras.lobby.block.tater.*;
import xyz.nucleoid.extras.lobby.particle.*;

import java.util.function.Function;
import com.mojang.serialization.MapCodec;

public class NEBlocks {
    public static final Block QUTMC_LOGO = registerColorPatternTaterBlock("qutmc_logo", new int[]{
        0xFF2F26, // red
        0xFF8E1F, // orange
        0xFFFF24, // yellow
        0x2BFF12, // green
        0x0000FF, // blue
        0xB224FF, // purple
    }, "ewogICJ0aW1lc3RhbXAiIDogMTcyNjkwMTk1NzExOCwKICAicHJvZmlsZUlkIiA6ICJkMWRkMDJlOWQwN2E0YWU1YWRjYWQyYzI5YTZhYmIyMCIsCiAgInByb2ZpbGVOYW1lIiA6ICJSYW5pdGFfRmFjaGExOCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8zYWJmOWQ3NWQzODhiNjFjZTk2YWE2NzhjOTY4YjViM2FlMDE3MzRlYzJiMjA5NzU1OWE2ZDcwYTljODU3MDZjIgogICAgfQogIH0KfQ==");
    public static final Block NUCLEOID_LOGO = registerTaterBlock("nucleoid_logo", ParticleTypes.GLOW_SQUID_INK, "bac7400dfcb9a387361a3ad7c296943eb841a9bda13ad89558e2d6efebf167bc");

    public static final Block END_PORTAL = registerSimple("end_portal", Blocks.END_PORTAL);
    public static final Block END_GATEWAY = register("end_gateway", AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.BLOCK).strength(100).noCollision(), settings -> new VirtualEndGatewayBlock(settings));
    public static final Block SAFE_TNT = registerSimple("safe_tnt", Blocks.TNT);

    public static final Block BLACK_CONCRETE_POWDER = registerSimple("black_concrete_powder", Blocks.BLACK_CONCRETE_POWDER);
    public static final Block BLUE_CONCRETE_POWDER = registerSimple("blue_concrete_powder", Blocks.BLUE_CONCRETE_POWDER);
    public static final Block BROWN_CONCRETE_POWDER = registerSimple("brown_concrete_powder", Blocks.BROWN_CONCRETE_POWDER);
    public static final Block CYAN_CONCRETE_POWDER = registerSimple("cyan_concrete_powder", Blocks.CYAN_CONCRETE_POWDER);
    public static final Block GREEN_CONCRETE_POWDER = registerSimple("green_concrete_powder", Blocks.GREEN_CONCRETE_POWDER);
    public static final Block GRAY_CONCRETE_POWDER = registerSimple("gray_concrete_powder", Blocks.GRAY_CONCRETE_POWDER);
    public static final Block LIGHT_BLUE_CONCRETE_POWDER = registerSimple("light_blue_concrete_powder", Blocks.LIGHT_BLUE_CONCRETE_POWDER);
    public static final Block LIGHT_GRAY_CONCRETE_POWDER = registerSimple("light_gray_concrete_powder", Blocks.LIGHT_GRAY_CONCRETE_POWDER);
    public static final Block LIME_CONCRETE_POWDER = registerSimple("lime_concrete_powder", Blocks.LIME_CONCRETE_POWDER);
    public static final Block MAGENTA_CONCRETE_POWDER = registerSimple("magenta_concrete_powder", Blocks.MAGENTA_CONCRETE_POWDER);
    public static final Block ORANGE_CONCRETE_POWDER = registerSimple("orange_concrete_powder", Blocks.ORANGE_CONCRETE_POWDER);
    public static final Block PINK_CONCRETE_POWDER = registerSimple("pink_concrete_powder", Blocks.PINK_CONCRETE_POWDER);
    public static final Block PURPLE_CONCRETE_POWDER = registerSimple("purple_concrete_powder", Blocks.PURPLE_CONCRETE_POWDER);
    public static final Block RED_CONCRETE_POWDER = registerSimple("red_concrete_powder", Blocks.RED_CONCRETE_POWDER);
    public static final Block WHITE_CONCRETE_POWDER = registerSimple("white_concrete_powder", Blocks.WHITE_CONCRETE_POWDER);
    public static final Block YELLOW_CONCRETE_POWDER = registerSimple("yellow_concrete_powder", Blocks.YELLOW_CONCRETE_POWDER);

    public static final Block GOLD_LAUNCH_PAD = register("gold_launch_pad", AbstractBlock.Settings.copy(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE).strength(100).noCollision(), settings -> new LaunchPadBlock(settings, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE.getDefaultState()));
    public static final Block IRON_LAUNCH_PAD = register("iron_launch_pad", AbstractBlock.Settings.copy(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE).strength(100).noCollision(), settings -> new LaunchPadBlock(settings, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE.getDefaultState()));

    public static final Block CONTRIBUTOR_STATUE = register("contributor_statue", AbstractBlock.Settings.copy(Blocks.SMOOTH_STONE).strength(100), settings -> new ContributorStatueBlock(settings));

    public static final Block INFINITE_DISPENSER = register("infinite_dispenser", AbstractBlock.Settings.copy(Blocks.DISPENSER).strength(100), settings -> new InfiniteDispenserBlock(settings));
    public static final Block INFINITE_DROPPER = register("infinite_dropper", AbstractBlock.Settings.copy(Blocks.DROPPER).strength(100), settings -> new InfiniteDropperBlock(settings));

    public static final Block SNAKE_BLOCK = register("snake_block", AbstractBlock.Settings.copy(Blocks.LIME_CONCRETE).strength(100), settings -> new SnakeBlock(settings, Blocks.LIME_CONCRETE.getDefaultState(), 8, 7, false));
    public static final Block FAST_SNAKE_BLOCK = register("fast_snake_block", AbstractBlock.Settings.copy(Blocks.LIGHT_BLUE_CONCRETE).strength(100), settings -> new SnakeBlock(settings, Blocks.LIGHT_BLUE_CONCRETE.getDefaultState(), 4, 7, false));
    public static final Block POWERED_SNAKE_BLOCK = register("powered_snake_block", AbstractBlock.Settings.copy(Blocks.REDSTONE_BLOCK).strength(100), settings -> new SnakeBlock(settings, Blocks.REDSTONE_BLOCK.getDefaultState(), 8, 7, true));
    public static final Block POWERED_FAST_SNAKE_BLOCK = register("powered_fast_snake_block", AbstractBlock.Settings.copy(Blocks.REDSTONE_BLOCK).strength(100), settings -> new SnakeBlock(settings, Blocks.REDSTONE_BLOCK.getDefaultState(), 4, 7, true));

    public static final Block TRANSIENT_IRON_DOOR = register("transient_iron_door", AbstractBlock.Settings.copy(Blocks.IRON_DOOR), settings -> new TransientDoorBlock(Blocks.IRON_DOOR, settings));
    public static final Block TRANSIENT_OAK_DOOR = register("transient_oak_door", AbstractBlock.Settings.copy(Blocks.OAK_DOOR), settings -> new TransientDoorBlock(Blocks.OAK_DOOR, settings));
    public static final Block TRANSIENT_SPRUCE_DOOR = register("transient_spruce_door", AbstractBlock.Settings.copy(Blocks.SPRUCE_DOOR), settings -> new TransientDoorBlock(Blocks.SPRUCE_DOOR, settings));
    public static final Block TRANSIENT_BIRCH_DOOR = register("transient_birch_door", AbstractBlock.Settings.copy(Blocks.BIRCH_DOOR), settings -> new TransientDoorBlock(Blocks.BIRCH_DOOR, settings));
    public static final Block TRANSIENT_JUNGLE_DOOR = register("transient_jungle_door", AbstractBlock.Settings.copy(Blocks.JUNGLE_DOOR), settings -> new TransientDoorBlock(Blocks.JUNGLE_DOOR, settings));
    public static final Block TRANSIENT_ACACIA_DOOR = register("transient_acacia_door", AbstractBlock.Settings.copy(Blocks.ACACIA_DOOR), settings -> new TransientDoorBlock(Blocks.ACACIA_DOOR, settings));
    public static final Block TRANSIENT_CHERRY_DOOR = register("transient_cherry_door", AbstractBlock.Settings.copy(Blocks.CHERRY_DOOR), settings -> new TransientDoorBlock(Blocks.CHERRY_DOOR, settings));
    public static final Block TRANSIENT_DARK_OAK_DOOR = register("transient_dark_oak_door", AbstractBlock.Settings.copy(Blocks.DARK_OAK_DOOR), settings -> new TransientDoorBlock(Blocks.DARK_OAK_DOOR, settings));
    public static final Block TRANSIENT_MANGROVE_DOOR = register("transient_mangrove_door", AbstractBlock.Settings.copy(Blocks.MANGROVE_DOOR), settings -> new TransientDoorBlock(Blocks.MANGROVE_DOOR, settings));
    public static final Block TRANSIENT_PALE_OAK_DOOR = register("transient_pale_oak_door", AbstractBlock.Settings.copy(Blocks.PALE_OAK_DOOR), settings -> new TransientDoorBlock(Blocks.PALE_OAK_DOOR, settings));
    public static final Block TRANSIENT_BAMBOO_DOOR = register("transient_bamboo_door", AbstractBlock.Settings.copy(Blocks.BAMBOO_DOOR), settings -> new TransientDoorBlock(Blocks.BAMBOO_DOOR, settings));
    public static final Block TRANSIENT_CRIMSON_DOOR = register("transient_crimson_door", AbstractBlock.Settings.copy(Blocks.CRIMSON_DOOR), settings -> new TransientDoorBlock(Blocks.CRIMSON_DOOR, settings));
    public static final Block TRANSIENT_WARPED_DOOR = register("transient_warped_door", AbstractBlock.Settings.copy(Blocks.WARPED_DOOR), settings -> new TransientDoorBlock(Blocks.WARPED_DOOR, settings));
    public static final Block TRANSIENT_COPPER_DOOR = register("transient_copper_door", AbstractBlock.Settings.copy(Blocks.COPPER_DOOR), settings -> new TransientOxidizableDoorBlock(Blocks.COPPER_DOOR, settings));
    public static final Block TRANSIENT_EXPOSED_COPPER_DOOR = register("transient_exposed_copper_door", AbstractBlock.Settings.copy(Blocks.EXPOSED_COPPER_DOOR), settings -> new TransientOxidizableDoorBlock(Blocks.EXPOSED_COPPER_DOOR, settings));
    public static final Block TRANSIENT_WEATHERED_COPPER_DOOR = register("transient_weathered_copper_door", AbstractBlock.Settings.copy(Blocks.WEATHERED_COPPER_DOOR), settings -> new TransientOxidizableDoorBlock(Blocks.WEATHERED_COPPER_DOOR, settings));
    public static final Block TRANSIENT_OXIDIZED_COPPER_DOOR = register("transient_oxidized_copper_door", AbstractBlock.Settings.copy(Blocks.OXIDIZED_COPPER_DOOR), settings -> new TransientOxidizableDoorBlock(Blocks.OXIDIZED_COPPER_DOOR, settings));
    public static final Block TRANSIENT_WAXED_COPPER_DOOR = register("transient_waxed_copper_door", AbstractBlock.Settings.copy(Blocks.WAXED_COPPER_DOOR), settings -> new TransientDoorBlock(Blocks.WAXED_COPPER_DOOR, settings));
    public static final Block TRANSIENT_WAXED_EXPOSED_COPPER_DOOR = register("transient_waxed_exposed_copper_door", AbstractBlock.Settings.copy(Blocks.WAXED_EXPOSED_COPPER_DOOR), settings -> new TransientDoorBlock(Blocks.WAXED_EXPOSED_COPPER_DOOR, settings));
    public static final Block TRANSIENT_WAXED_WEATHERED_COPPER_DOOR = register("transient_waxed_weathered_copper_door", AbstractBlock.Settings.copy(Blocks.WAXED_WEATHERED_COPPER_DOOR), settings -> new TransientDoorBlock(Blocks.WAXED_WEATHERED_COPPER_DOOR, settings));
    public static final Block TRANSIENT_WAXED_OXIDIZED_COPPER_DOOR = register("transient_waxed_oxidized_copper_door", AbstractBlock.Settings.copy(Blocks.WAXED_OXIDIZED_COPPER_DOOR), settings -> new TransientDoorBlock(Blocks.WAXED_OXIDIZED_COPPER_DOOR, settings));

    public static final Block TRANS_COLLECTABLE = registerColorPatternTaterBlock("trans_collectable", new int[]{
        0xEE90AD, // pink
        0x3CB0DA, // blue
        0xCFD5D6, // white
    }, "f77dbc809b254449023fac0dd4e0d9100b5c4407748be089f0e00c7ef7ab764");
    public static final Block ASEXUAL_COLLECTABLE = registerColorPatternTaterBlock("asexual_collectable", new int[]{
        0x16161B, // black
        0x3F4548, // gray
        0xCFD5D6, // white
        0x7B2BAD, // purple
    }, "3902887dc55d4f736d0b566ad812f256113aaa4a318ffb865623fb5a677aef32");
    public static final Block BI_COLLECTABLE = registerColorPatternTaterBlock("bi_collectable", new int[]{
        0xBE46B5, // pink
        0x7B2BAD, // purple
        0x353A9E, // blue
    }, "4526a72ca5be42920cd310280c03e2c9e9a70c55aa9cc1a0c48396d556f1c75d");
    public static final Block GAY_COLLECTABLE = registerColorPatternTaterBlock("gay_collectable", new int[]{
        0xA12823, // red
        0xF17716, // orange
        0xF9C629, // yellow
        0x556E1C, // green
        0x353A9E, // blue
        0x7B2BAD, // purple
    }, "f9f446f29396ff444d0ef4f53a70c28afb69e5d1da037c03c277d23917dacded");
    public static final Block LESBIAN_COLLECTABLE = registerColorPatternTaterBlock("lesbian_collectable", new int[]{
        0xA12823, // red
        0xF17716, // orange
        0xEAEDED, // white
        0xEE90AD, // pink
        0xBE46B5, // magenta
    }, "44492740f40c19c3e52871cdf6cbd585e980fc7b50cb0fc949bfbe44032a7db7");
    public static final Block NONBINARY_COLLECTABLE = registerColorPatternTaterBlock("nonbinary_collectable", new int[]{
        0xF9C629, // yellow
        0x16161B, // black
        0xCFD5D6, // white
        0x7B2BAD, // purple
    }, "10854e473bc7a0a6956cb12df8026de9fc00fae40c0502a3182908bbb50c9aa5");
    public static final Block PAN_COLLECTABLE = registerColorPatternTaterBlock("pan_collectable", new int[]{
        0xFA318C, // pink
        0xFDD73B, // yellow
        0x2394F9, // blue
    }, "3f761be18f070a016e4f61d37ec13b23032a552dcdb70a67f855c3ab2fae54e0");
    public static final Block GENDERFLUID_COLLECTABLE = registerColorPatternTaterBlock("genderfluid_collectable", new int[]{
        0xBE46B5, // pink
        0xCFD5D6, // white
        0x7B2BAD, // purple
        0x16161B, // black
        0x2394F9, // blue
    }, "ba066cdd8d48501eb51eea1e3e417c25ef51a04284714baad5ab5de5cd4221b8");
    public static final Block DEMISEXUAL_COLLECTABLE = registerColorPatternTaterBlock("demisexual_collectable", new int[]{
        0x16161B, // black
        0xCFD5D6, // white
        0x7B2BAD, // purple
        0x3F4548, // gray
    }, "32b7cd2c5d70cab476ce951e2c520c9b3579250ad900164d6c2321c7f43d6dc7");

    public static final Block BIOMEDICAL_SOCIETY_VIRUS = registerTaterBlock("biomedical_society_virus", ParticleTypes.SCRAPE, "b12f770c4542c9f26ba03aaee686e0946698d394a8e745d3eac6013383dcff29");
    public static final Block DND_CLUB_DICE = registerDiceTaterBlock("dnd_club_dice");
    public static final Block QAC_CLUB_DUCK = registerTaterBlock("qac_club_duck", ParticleTypes.RAIN, "c1eef1240cdf9bb23bb9b44943c0519dc89bd3e139099a27e4fc0d9e345a76ae");
    public static final Block QUT_FUR_PROTOGEN = registerTaterBlock("qut_fur_protogen", ParticleTypes.ELECTRIC_SPARK, "4a2518738a6a3db2bdf00e761653cee28a4ea8cbd4f698ede7c9052c145787d6");

    public static final Block CORRUPCOLLECTABLE = register("corrupcollectable", createTaterBlockSettings(), settings -> new CorruptaterBlock(settings));

    public static final BlockEntityType<LaunchPadBlockEntity> LAUNCH_PAD_ENTITY = FabricBlockEntityTypeBuilder.create(LaunchPadBlockEntity::new, GOLD_LAUNCH_PAD, IRON_LAUNCH_PAD).build();
    public static final BlockEntityType<ContributorStatueBlockEntity> CONTRIBUTOR_STATUE_ENTITY = FabricBlockEntityTypeBuilder.create(ContributorStatueBlockEntity::new, CONTRIBUTOR_STATUE).build();
    public static final BlockEntityType<InfiniteDispenserBlockEntity> INFINITE_DISPENSER_ENTITY = FabricBlockEntityTypeBuilder.create(InfiniteDispenserBlockEntity::new, INFINITE_DISPENSER).build();
    public static final BlockEntityType<InfiniteDropperBlockEntity> INFINITE_DROPPER_ENTITY = FabricBlockEntityTypeBuilder.create(InfiniteDropperBlockEntity::new, INFINITE_DROPPER).build();
    // these are currently unused:
    public static final BlockEntityType<TateroidBlockEntity> TATEROID_ENTITY = FabricBlockEntityTypeBuilder.create(TateroidBlockEntity::new).build();
    public static final BlockEntityType<DaylightDetectorTaterBlockEntity> DAYLIGHT_DETECTOR_TATER_ENTITY = FabricBlockEntityTypeBuilder.create(DaylightDetectorTaterBlockEntity::new).build();
    public static final BlockEntityType<BellTaterBlockEntity> BELL_TATER_ENTITY = FabricBlockEntityTypeBuilder.create(BellTaterBlockEntity::new).build();

    private static Block registerSimple(String id, Block virtual) {
        return register(id, AbstractBlock.Settings.copy(virtual).strength(100), settings -> new SimplePolymerBlock(settings, virtual));
    }

    private static AbstractBlock.Settings createTaterBlockSettings() {
        return AbstractBlock.Settings.create().mapColor(MapColor.PALE_GREEN).strength(100);
    }

    private static Block registerBotanicTaterBlock(String id, ParticleEffect effect, String textureUp, String textureDown) {
        return register(id, createTaterBlockSettings(), settings -> new BotanicalPotatoBlock(settings, new SimpleTaterParticleSpawner(effect), textureUp, textureDown));
    }

    private static Block registerTaterBlock(String id, TaterParticleSpawner particleSpawner, String texture) {
        return register(id, createTaterBlockSettings(), settings -> new CubicPotatoBlock(settings, particleSpawner, texture));
    }

    private static Block registerTaterBlock(String id, ParticleEffect effect, String texture) {
        return registerTaterBlock(id, new SimpleTaterParticleSpawner(effect), texture);
    }

    private static Block registerTaterBlock(String id, Block particleBlock, String texture) {
        var effect = new BlockStateParticleEffect(ParticleTypes.BLOCK, particleBlock.getDefaultState());
        return registerTaterBlock(id, effect, texture);
    }

    private static Block registerTaterBlock(String id, Item particleItem, String texture) {
        var effect = new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(particleItem));
        return registerTaterBlock(id, effect, texture);
    }

    private static Block registerTaterBlock(String id, ParticleEffect effect, String texture, int particleRate) {
        return registerTaterBlock(id, new SimpleTaterParticleSpawner(effect, particleRate), texture);
    }

    private static Block registerColorPatternTaterBlock(String id, int[] pattern, String texture) {
        return registerTaterBlock(id, new ColorPatternTaterParticleSpawner(pattern), texture);
    }

    private static Block registerEntityEffectTaterBlock(String id, String texture) {
        return registerTaterBlock(id, EntityEffectTaterParticleSpawner.DEFAULT, texture);
    }

    private static Block registerLuckyTaterBlock(String id, String texture, String cooldownTexture) {
        return register(id, createTaterBlockSettings(), settings -> new LuckyTaterBlock(settings, texture, cooldownTexture));
    }

    private static Block registerWardenTaterBlock(String id, String texture) {
        return registerTaterBlock(id, new WardenTaterParticleSpawner(), texture);
    }

    private static Block registerGlowingLayerTaterBlock(String id, ParticleEffect effect, String texture, GlowingLayerTaterBlock.Pixel[] glowingPixels) {
        return register(id, createTaterBlockSettings(), settings -> new GlowingLayerTaterBlock(settings, new SimpleTaterParticleSpawner(effect), texture, glowingPixels));
    }

    private static Block registerDiceTaterBlock(String id) {
        return register(id, createTaterBlockSettings(), settings -> new DiceTaterBlock(settings));
    }

    private static Block registerTateroidBlock(String id, RegistryEntry<SoundEvent> defaultSound, double defaultParticleColor, String texture) {
        return register(id, createTaterBlockSettings(), settings -> new TateroidBlock(settings, defaultSound, defaultParticleColor, texture));
    }

    private static Block registerColorTaterBlock(String id, int color, String texture) {
        return registerTaterBlock(id, SimpleTaterParticleSpawner.ofDust(color), texture);
    }

    private static Block registerColorTaterBlock(String id, DyeColor color, String texture) {
        return registerTaterBlock(id, SimpleTaterParticleSpawner.ofDust(color), texture);
    }

    private static Block registerRedstoneTaterBlock(String id, int color, String texture) {
        return register(id, createTaterBlockSettings(), settings -> new RedstoneTaterBlock(settings, SimpleTaterParticleSpawner.ofDust(color), texture));
    }

    private static Block registerDaylightDetectorTaterBlock(String id, String texture, boolean inverted) {
        return register(id, createTaterBlockSettings(), settings -> new DaylightDetectorTaterBlock(settings, texture, inverted));
    }

    private static Block registerTargetTaterBlock(String id, String texture) {
        return register(id, createTaterBlockSettings(), settings -> new TargetTaterBlock(settings, texture));
    }

    private static Block registerBellTaterBlock(String id, String texture) {
        return register(id, createTaterBlockSettings(), settings -> new BellTaterBlock(settings, texture));
    }

    private static Block registerElderGuardianParticleTaterBlock(String id, String texture) {
        return registerTaterBlock(id, new SimpleTaterParticleSpawner(ParticleTypes.ELDER_GUARDIAN, 10000, 50), texture);
    }

    private static Block registerCapsuleTaterBlock(String id, int color, int weight, String texture) {
        return register(id, createTaterBlockSettings(), settings -> new CapsuleTaterBlock(settings, RingTaterParticleSpawner.ofDust(color), weight, texture));
    }

    private static Block registerMarkerTaterBlock(String id, Block particleBlock, String texture) {
        return registerTaterBlock(id, new SimpleMarkerTaterParticleSpawner(particleBlock, MarkerTaterParticleSpawner.MARKER_PLAYER_PARTICLE_RATE, SimpleMarkerTaterParticleSpawner.DEFAULT_BLOCK_PARTICLE_CHANCE), texture);
    }

    private static Block registerLightTaterBlock(String id, String texture) {
        return registerTaterBlock(id, LightTaterParticleSpawner.DEFAULT, texture);
    }

    public static void register() {
        registerOxidizableBlockPair(NEBlocks.TRANSIENT_COPPER_DOOR, NEBlocks.TRANSIENT_EXPOSED_COPPER_DOOR);
        registerOxidizableBlockPair(NEBlocks.TRANSIENT_EXPOSED_COPPER_DOOR, NEBlocks.TRANSIENT_WEATHERED_COPPER_DOOR);
        registerOxidizableBlockPair(NEBlocks.TRANSIENT_WEATHERED_COPPER_DOOR, NEBlocks.TRANSIENT_OXIDIZED_COPPER_DOOR);

        OxidizableBlocksRegistry.registerWaxableBlockPair(NEBlocks.TRANSIENT_COPPER_DOOR, NEBlocks.TRANSIENT_WAXED_COPPER_DOOR);
        OxidizableBlocksRegistry.registerWaxableBlockPair(NEBlocks.TRANSIENT_EXPOSED_COPPER_DOOR, NEBlocks.TRANSIENT_WAXED_EXPOSED_COPPER_DOOR);
        OxidizableBlocksRegistry.registerWaxableBlockPair(NEBlocks.TRANSIENT_WEATHERED_COPPER_DOOR, NEBlocks.TRANSIENT_WAXED_WEATHERED_COPPER_DOOR);
        OxidizableBlocksRegistry.registerWaxableBlockPair(NEBlocks.TRANSIENT_OXIDIZED_COPPER_DOOR, NEBlocks.TRANSIENT_WAXED_OXIDIZED_COPPER_DOOR);

        TaterParticleSpawnerTypes.register();

        registerBlockType("bell_tater", BellTaterBlock.CODEC);
        registerBlockType("botantical_tater", BotanicalPotatoBlock.CODEC);
        registerBlockType("capsule_tater", CapsuleTaterBlock.CODEC);
        registerBlockType("corruptater", CorruptaterBlock.CODEC);
        registerBlockType("cubic_tater", CubicPotatoBlock.CODEC);
        registerBlockType("daylight_detector_tater", DaylightDetectorTaterBlock.CODEC);
        registerBlockType("dice_tater", DiceTaterBlock.CODEC);
        registerBlockType("glowing_layer_tater", GlowingLayerTaterBlock.CODEC);
        registerBlockType("lucky_tater", LuckyTaterBlock.CODEC);
        registerBlockType("redstone_tater", RedstoneTaterBlock.CODEC);
        registerBlockType("target_tater", TargetTaterBlock.CODEC);
        registerBlockType("tateroid", TateroidBlock.CODEC);

        registerBlockEntity("launch_pad", LAUNCH_PAD_ENTITY);
        registerBlockEntity("contributor_statue", CONTRIBUTOR_STATUE_ENTITY);
        registerBlockEntity("infinite_dispenser", INFINITE_DISPENSER_ENTITY);
        registerBlockEntity("infinite_dropper", INFINITE_DROPPER_ENTITY);
        registerBlockEntity("tateroid", TATEROID_ENTITY);
        registerBlockEntity("daylight_detector_tater", DAYLIGHT_DETECTOR_TATER_ENTITY);
        registerBlockEntity("bell_tater", BELL_TATER_ENTITY);
    }

    private static <T extends Block> T register(String id, Block.Settings settings, Function<Block.Settings, T> factory) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, NucleoidExtras.identifier(id));
        T block = factory.apply(settings.registryKey(key));

        return Registry.register(Registries.BLOCK, key, block);
    }

    private static void registerOxidizableBlockPair(Block less, Block more) {
        OxidizableBlocksRegistry.registerOxidizableBlockPair(less, more);

        // TransientOxidizableDoorBlock#hasRandomTicks is dependent on the above registration,
        // so the cached BlockState#ticksRandomly field must be recomputed with the new result
        for (BlockState state : less.getStateManager().getStates()) {
            state.initShapeCache();
        }
    }

    private static <T extends Block> MapCodec<T> registerBlockType(String id, MapCodec<T> codec) {
        return Registry.register(Registries.BLOCK_TYPE, NucleoidExtras.identifier(id), codec);
    }

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String id, BlockEntityType<T> type) {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, NucleoidExtras.identifier(id), type);
        PolymerBlockUtils.registerBlockEntity(type);
        return type;
    }
}
