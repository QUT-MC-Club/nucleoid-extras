package xyz.nucleoid.extras.lobby;

import eu.pb4.polymer.core.api.block.PolymerHeadBlock;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.nucleoid.extras.NucleoidExtras;
import xyz.nucleoid.extras.NucleoidExtrasConfig;
import xyz.nucleoid.extras.component.GamePortalComponent;
import xyz.nucleoid.extras.component.LauncherComponent;
import xyz.nucleoid.extras.component.NEDataComponentTypes;
import xyz.nucleoid.extras.component.TaterPositionsComponent;
import xyz.nucleoid.extras.component.TaterSelectionComponent;
import xyz.nucleoid.extras.lobby.block.tater.TinyPotatoBlock;
import xyz.nucleoid.extras.lobby.item.GamePortalOpenerItem;
import xyz.nucleoid.extras.lobby.item.LaunchFeatherItem;
import xyz.nucleoid.extras.lobby.item.LobbyBlockItem;
import xyz.nucleoid.extras.lobby.item.LobbyHeadItem;
import xyz.nucleoid.extras.lobby.item.LobbyTallBlockItem;
import xyz.nucleoid.extras.lobby.item.LockSetterItem;
import xyz.nucleoid.extras.lobby.item.QuickArmorStandItem;
import xyz.nucleoid.extras.lobby.item.RuleBookItem;
import xyz.nucleoid.extras.lobby.item.tater.CreativeTaterBoxItem;
import xyz.nucleoid.extras.lobby.item.tater.TaterBoxItem;
import xyz.nucleoid.extras.lobby.item.tater.TaterGuidebookItem;
import xyz.nucleoid.plasmid.api.game.GameSpaceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class NEItems {
    private static final List<Item> TATERS = new ArrayList<>();

    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
        .displayName(Text.translatable("text.nucleoid_extras.name"))
        .icon(() -> new ItemStack(NEItems.QUTMC_LOGO))
        .entries((context, entries) -> {
            entries.add(NEItems.QUICK_ARMOR_STAND);
            entries.add(NEItems.END_PORTAL);
            entries.add(NEItems.END_GATEWAY);
            entries.add(NEItems.SAFE_TNT);
            entries.add(NEItems.LAUNCH_FEATHER);
            entries.add(NEItems.GOLD_LAUNCH_PAD);
            entries.add(NEItems.IRON_LAUNCH_PAD);
            entries.add(NEItems.CONTRIBUTOR_STATUE);
            entries.add(NEItems.LOCK_SETTER);
            entries.add(NEItems.INFINITE_DISPENSER);
            entries.add(NEItems.INFINITE_DROPPER);
            entries.add(NEItems.SNAKE_BLOCK);
            entries.add(NEItems.FAST_SNAKE_BLOCK);
            entries.add(NEItems.POWERED_SNAKE_BLOCK);
            entries.add(NEItems.POWERED_FAST_SNAKE_BLOCK);
            entries.add(NEItems.TRANSIENT_IRON_DOOR);
            entries.add(NEItems.TRANSIENT_OAK_DOOR);
            entries.add(NEItems.TRANSIENT_SPRUCE_DOOR);
            entries.add(NEItems.TRANSIENT_BIRCH_DOOR);
            entries.add(NEItems.TRANSIENT_JUNGLE_DOOR);
            entries.add(NEItems.TRANSIENT_ACACIA_DOOR);
            entries.add(NEItems.TRANSIENT_CHERRY_DOOR);
            entries.add(NEItems.TRANSIENT_DARK_OAK_DOOR);
            entries.add(NEItems.TRANSIENT_MANGROVE_DOOR);
            entries.add(NEItems.TRANSIENT_PALE_OAK_DOOR);
            entries.add(NEItems.TRANSIENT_BAMBOO_DOOR);
            entries.add(NEItems.TRANSIENT_CRIMSON_DOOR);
            entries.add(NEItems.TRANSIENT_WARPED_DOOR);
            entries.add(NEItems.TRANSIENT_COPPER_DOOR);
            entries.add(NEItems.TRANSIENT_EXPOSED_COPPER_DOOR);
            entries.add(NEItems.TRANSIENT_WEATHERED_COPPER_DOOR);
            entries.add(NEItems.TRANSIENT_OXIDIZED_COPPER_DOOR);
            entries.add(NEItems.TRANSIENT_WAXED_COPPER_DOOR);
            entries.add(NEItems.TRANSIENT_WAXED_EXPOSED_COPPER_DOOR);
            entries.add(NEItems.TRANSIENT_WAXED_WEATHERED_COPPER_DOOR);
            entries.add(NEItems.TRANSIENT_WAXED_OXIDIZED_COPPER_DOOR);
            entries.add(NEItems.BLACK_CONCRETE_POWDER);
            entries.add(NEItems.BLUE_CONCRETE_POWDER);
            entries.add(NEItems.BROWN_CONCRETE_POWDER);
            entries.add(NEItems.CYAN_CONCRETE_POWDER);
            entries.add(NEItems.GREEN_CONCRETE_POWDER);
            entries.add(NEItems.GRAY_CONCRETE_POWDER);
            entries.add(NEItems.LIGHT_BLUE_CONCRETE_POWDER);
            entries.add(NEItems.LIGHT_GRAY_CONCRETE_POWDER);
            entries.add(NEItems.LIME_CONCRETE_POWDER);
            entries.add(NEItems.MAGENTA_CONCRETE_POWDER);
            entries.add(NEItems.ORANGE_CONCRETE_POWDER);
            entries.add(NEItems.PINK_CONCRETE_POWDER);
            entries.add(NEItems.PURPLE_CONCRETE_POWDER);
            entries.add(NEItems.RED_CONCRETE_POWDER);
            entries.add(NEItems.WHITE_CONCRETE_POWDER);
            entries.add(NEItems.YELLOW_CONCRETE_POWDER);
            entries.add(NEItems.GAME_PORTAL_OPENER);
            entries.add(NEItems.TATER_BOX);
            entries.add(NEItems.CREATIVE_TATER_BOX);
            entries.add(NEItems.TATER_GUIDEBOOK);
            TATERS.forEach(entries::add);
        })
        .build();

    public static final Item QUTMC_LOGO = registerHead("qutmc_logo", NEBlocks.QUTMC_LOGO);
    public static final Item NUCLEOID_LOGO = registerHead("nucleoid_logo", NEBlocks.NUCLEOID_LOGO);

    public static final Item END_PORTAL = registerSimple("end_portal", NEBlocks.END_PORTAL, Items.BLACK_CARPET);
    public static final Item END_GATEWAY = registerSimple("end_gateway", NEBlocks.END_GATEWAY, Items.BLACK_WOOL);
    public static final Item SAFE_TNT = registerSimple("safe_tnt", NEBlocks.SAFE_TNT, Items.TNT);

    public static final Item GOLD_LAUNCH_PAD = registerSimple("gold_launch_pad", NEBlocks.GOLD_LAUNCH_PAD, Items.LIGHT_WEIGHTED_PRESSURE_PLATE);
    public static final Item IRON_LAUNCH_PAD = registerSimple("iron_launch_pad", NEBlocks.IRON_LAUNCH_PAD, Items.HEAVY_WEIGHTED_PRESSURE_PLATE);

    public static final Item CONTRIBUTOR_STATUE = registerSimple("contributor_statue", NEBlocks.CONTRIBUTOR_STATUE, Items.SMOOTH_STONE);

    public static final Item INFINITE_DISPENSER = registerSimple("infinite_dispenser", NEBlocks.INFINITE_DISPENSER, Items.DISPENSER);
    public static final Item INFINITE_DROPPER = registerSimple("infinite_dropper", NEBlocks.INFINITE_DROPPER, Items.DROPPER);
    public static final Item SNAKE_BLOCK = registerSimple("snake_block", NEBlocks.SNAKE_BLOCK, Items.LIME_CONCRETE);
    public static final Item FAST_SNAKE_BLOCK = registerSimple("fast_snake_block", NEBlocks.FAST_SNAKE_BLOCK, Items.LIGHT_BLUE_CONCRETE);
    public static final Item POWERED_SNAKE_BLOCK = registerSimple("powered_snake_block", NEBlocks.POWERED_SNAKE_BLOCK, Items.REDSTONE_BLOCK);
    public static final Item POWERED_FAST_SNAKE_BLOCK = registerSimple("powered_fast_snake_block", NEBlocks.POWERED_FAST_SNAKE_BLOCK, Items.REDSTONE_BLOCK);

    public static final Item TRANSIENT_IRON_DOOR = register("transient_iron_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_IRON_DOOR, settings, Items.IRON_DOOR));
    public static final Item TRANSIENT_OAK_DOOR = register("transient_oak_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_OAK_DOOR, settings, Items.OAK_DOOR));
    public static final Item TRANSIENT_SPRUCE_DOOR = register("transient_spruce_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_SPRUCE_DOOR, settings, Items.SPRUCE_DOOR));
    public static final Item TRANSIENT_BIRCH_DOOR = register("transient_birch_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_BIRCH_DOOR, settings, Items.BIRCH_DOOR));
    public static final Item TRANSIENT_JUNGLE_DOOR = register("transient_jungle_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_JUNGLE_DOOR, settings, Items.JUNGLE_DOOR));
    public static final Item TRANSIENT_ACACIA_DOOR = register("transient_acacia_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_ACACIA_DOOR, settings, Items.ACACIA_DOOR));
    public static final Item TRANSIENT_CHERRY_DOOR = register("transient_cherry_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_CHERRY_DOOR, settings, Items.CHERRY_DOOR));
    public static final Item TRANSIENT_DARK_OAK_DOOR = register("transient_dark_oak_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_DARK_OAK_DOOR, settings, Items.DARK_OAK_DOOR));
    public static final Item TRANSIENT_MANGROVE_DOOR = register("transient_mangrove_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_MANGROVE_DOOR, settings, Items.MANGROVE_DOOR));
    public static final Item TRANSIENT_PALE_OAK_DOOR = register("transient_pale_oak_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_PALE_OAK_DOOR, settings, Items.PALE_OAK_DOOR));
    public static final Item TRANSIENT_BAMBOO_DOOR = register("transient_bamboo_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_BAMBOO_DOOR, settings, Items.BAMBOO_DOOR));
    public static final Item TRANSIENT_CRIMSON_DOOR = register("transient_crimson_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_CRIMSON_DOOR, settings, Items.CRIMSON_DOOR));
    public static final Item TRANSIENT_WARPED_DOOR = register("transient_warped_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_WARPED_DOOR, settings, Items.WARPED_DOOR));
    public static final Item TRANSIENT_COPPER_DOOR = register("transient_copper_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_COPPER_DOOR, settings, Items.COPPER_DOOR));
    public static final Item TRANSIENT_EXPOSED_COPPER_DOOR = register("transient_exposed_copper_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_EXPOSED_COPPER_DOOR, settings, Items.EXPOSED_COPPER_DOOR));
    public static final Item TRANSIENT_WEATHERED_COPPER_DOOR = register("transient_weathered_copper_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_WEATHERED_COPPER_DOOR, settings, Items.WEATHERED_COPPER_DOOR));
    public static final Item TRANSIENT_OXIDIZED_COPPER_DOOR = register("transient_oxidized_copper_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_OXIDIZED_COPPER_DOOR, settings, Items.OXIDIZED_COPPER_DOOR));
    public static final Item TRANSIENT_WAXED_COPPER_DOOR = register("transient_waxed_copper_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_WAXED_COPPER_DOOR, settings, Items.WAXED_COPPER_DOOR));
    public static final Item TRANSIENT_WAXED_EXPOSED_COPPER_DOOR = register("transient_waxed_exposed_copper_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_WAXED_EXPOSED_COPPER_DOOR, settings, Items.WAXED_EXPOSED_COPPER_DOOR));
    public static final Item TRANSIENT_WAXED_WEATHERED_COPPER_DOOR = register("transient_waxed_weathered_copper_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_WAXED_WEATHERED_COPPER_DOOR, settings, Items.WAXED_WEATHERED_COPPER_DOOR));
    public static final Item TRANSIENT_WAXED_OXIDIZED_COPPER_DOOR = register("transient_waxed_oxidized_copper_door", new Item.Settings().useBlockPrefixedTranslationKey(), settings -> new LobbyTallBlockItem(NEBlocks.TRANSIENT_WAXED_OXIDIZED_COPPER_DOOR, settings, Items.WAXED_OXIDIZED_COPPER_DOOR));

    public static final Item BLACK_CONCRETE_POWDER = registerSimple("black_concrete_powder", NEBlocks.BLACK_CONCRETE_POWDER, Items.BLACK_CONCRETE_POWDER);
    public static final Item BLUE_CONCRETE_POWDER = registerSimple("blue_concrete_powder", NEBlocks.BLUE_CONCRETE_POWDER, Items.BLUE_CONCRETE_POWDER);
    public static final Item BROWN_CONCRETE_POWDER = registerSimple("brown_concrete_powder", NEBlocks.BROWN_CONCRETE_POWDER, Items.BROWN_CONCRETE_POWDER);
    public static final Item CYAN_CONCRETE_POWDER = registerSimple("cyan_concrete_powder", NEBlocks.CYAN_CONCRETE_POWDER, Items.CYAN_CONCRETE_POWDER);
    public static final Item GREEN_CONCRETE_POWDER = registerSimple("green_concrete_powder", NEBlocks.GREEN_CONCRETE_POWDER, Items.GREEN_CONCRETE_POWDER);
    public static final Item GRAY_CONCRETE_POWDER = registerSimple("gray_concrete_powder", NEBlocks.GRAY_CONCRETE_POWDER, Items.GRAY_CONCRETE_POWDER);
    public static final Item LIGHT_BLUE_CONCRETE_POWDER = registerSimple("light_blue_concrete_powder", NEBlocks.LIGHT_BLUE_CONCRETE_POWDER, Items.LIGHT_BLUE_CONCRETE_POWDER);
    public static final Item LIGHT_GRAY_CONCRETE_POWDER = registerSimple("light_gray_concrete_powder", NEBlocks.LIGHT_GRAY_CONCRETE_POWDER, Items.LIGHT_GRAY_CONCRETE_POWDER);
    public static final Item LIME_CONCRETE_POWDER = registerSimple("lime_concrete_powder", NEBlocks.LIME_CONCRETE_POWDER, Items.LIME_CONCRETE_POWDER);
    public static final Item MAGENTA_CONCRETE_POWDER = registerSimple("magenta_concrete_powder", NEBlocks.MAGENTA_CONCRETE_POWDER, Items.MAGENTA_CONCRETE_POWDER);
    public static final Item ORANGE_CONCRETE_POWDER = registerSimple("orange_concrete_powder", NEBlocks.ORANGE_CONCRETE_POWDER, Items.ORANGE_CONCRETE_POWDER);
    public static final Item PINK_CONCRETE_POWDER = registerSimple("pink_concrete_powder", NEBlocks.PINK_CONCRETE_POWDER, Items.PINK_CONCRETE_POWDER);
    public static final Item PURPLE_CONCRETE_POWDER = registerSimple("purple_concrete_powder", NEBlocks.PURPLE_CONCRETE_POWDER, Items.PURPLE_CONCRETE_POWDER);
    public static final Item RED_CONCRETE_POWDER = registerSimple("red_concrete_powder", NEBlocks.RED_CONCRETE_POWDER, Items.RED_CONCRETE_POWDER);
    public static final Item WHITE_CONCRETE_POWDER = registerSimple("white_concrete_powder", NEBlocks.WHITE_CONCRETE_POWDER, Items.WHITE_CONCRETE_POWDER);
    public static final Item YELLOW_CONCRETE_POWDER = registerSimple("yellow_concrete_powder", NEBlocks.YELLOW_CONCRETE_POWDER, Items.YELLOW_CONCRETE_POWDER);

    public static final Item TRANS_COLLECTABLE = registerHead("trans_collectable", NEBlocks.TRANS_COLLECTABLE);
    public static final Item ASEXUAL_COLLECTABLE = registerHead("asexual_collectable", NEBlocks.ASEXUAL_COLLECTABLE);
    public static final Item BI_COLLECTABLE = registerHead("bi_collectable", NEBlocks.BI_COLLECTABLE);
    public static final Item GAY_COLLECTABLE = registerHead("gay_collectable", NEBlocks.GAY_COLLECTABLE);
    public static final Item LESBIAN_COLLECTABLE = registerHead("lesbian_collectable", NEBlocks.LESBIAN_COLLECTABLE);
    public static final Item NONBINARY_COLLECTABLE = registerHead("nonbinary_collectable", NEBlocks.NONBINARY_COLLECTABLE);
    public static final Item PAN_COLLECTABLE = registerHead("pan_collectable", NEBlocks.PAN_COLLECTABLE);
    public static final Item GENDERFLUID_COLLECTABLE = registerHead("genderfluid_collectable", NEBlocks.GENDERFLUID_COLLECTABLE);
    public static final Item DEMISEXUAL_COLLECTABLE = registerHead("demisexual_collectable", NEBlocks.DEMISEXUAL_COLLECTABLE);

    public static final Item DND_CLUB_DICE = registerHead("dnd_club_dice", NEBlocks.DND_CLUB_DICE);
    public static final Item BIOMEDICAL_SOCIETY_VIRUS = registerHead("biomedical_society_virus", NEBlocks.BIOMEDICAL_SOCIETY_VIRUS);
    public static final Item QAC_CLUB_DUCK = registerHead("qac_club_duck", NEBlocks.QAC_CLUB_DUCK);
    public static final Item QUT_FUR_PROTOGEN = registerHead("qut_fur_protogen", NEBlocks.QUT_FUR_PROTOGEN);

    public static final Item CORRUPCOLLECTABLE = registerHead("corrupcollectable", NEBlocks.CORRUPCOLLECTABLE);

    public static final Item TATER_BOX = register("tater_box", new Item.Settings()
        .component(NEDataComponentTypes.TATER_SELECTION, TaterSelectionComponent.DEFAULT)
        .maxCount(1), settings -> new TaterBoxItem(settings));
    public static final Item CREATIVE_TATER_BOX = register("creative_tater_box", new Item.Settings()
        .component(NEDataComponentTypes.TATER_SELECTION, TaterSelectionComponent.DEFAULT)
        .maxCount(1), settings -> new CreativeTaterBoxItem(settings));

    public static final Item TATER_GUIDEBOOK = register("tater_guidebook", new Item.Settings()
        .component(NEDataComponentTypes.TATER_POSITIONS, TaterPositionsComponent.DEFAULT)
        .component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
        .maxCount(1), settings -> new TaterGuidebookItem(settings));
    public static final Item QUICK_ARMOR_STAND = register("quick_armor_stand", new Item.Settings(), settings -> new QuickArmorStandItem(settings));
    public static final Item GAME_PORTAL_OPENER = register("game_portal_opener", new Item.Settings().maxCount(1), settings -> new GamePortalOpenerItem(settings));
    public static final Item LAUNCH_FEATHER = register("launch_feather", new Item.Settings()
        .component(NEDataComponentTypes.LAUNCHER, LauncherComponent.DEFAULT)
        .maxCount(1), settings -> new LaunchFeatherItem(settings));

    public static final Item LOCK_SETTER = register("lock_setter", new Item.Settings()
        .component(DataComponentTypes.LOCK, LockSetterItem.createUnlockableLock())
        .component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
        .maxCount(1), LockSetterItem::new);

    public static final Item RULE_BOOK = register("rule_book", new Item.Settings()
        .rarity(Rarity.EPIC)
        .component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true), settings -> new RuleBookItem(settings));

    private static Item registerHead(String id, Block head) {
        Item.Settings baseSettings = new Item.Settings()
            .useBlockPrefixedTranslationKey()
            .equippableUnswappable(EquipmentSlot.HEAD);

        if (head instanceof TinyPotatoBlock tinyPotatoBlock) {
            return register(id, baseSettings, settings -> new LobbyHeadItem(head, settings, tinyPotatoBlock.getItemTexture()));
        } else if (head instanceof PolymerHeadBlock headBlock) {
            return register(id, baseSettings, settings -> new LobbyHeadItem(head, settings, headBlock.getPolymerSkinValue(head.getDefaultState(), BlockPos.ORIGIN, null)));
        }

        throw new IllegalArgumentException("Cannot register " + id + " as a head item because " + head + " is not a head");
    }

    private static Item registerSimple(String id, Block block, Item virtual) {
        return register(id, new Item.Settings().component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true).useBlockPrefixedTranslationKey(), settings -> new LobbyBlockItem(block, settings, virtual));
    }

    public static void register() {
        PolymerItemGroupUtils.registerPolymerItemGroup(NucleoidExtras.identifier("general"), ITEM_GROUP);

        ServerPlayConnectionEvents.JOIN.register(NEItems::onPlayerJoin);

        UseBlockCallback.EVENT.register(NEItems::onUseBlock);
        UseEntityCallback.EVENT.register(NEItems::onUseEntity);
    }

    private static boolean tryOfferStack(ServerPlayerEntity player, Item item, Consumer<ItemStack> consumer) {
        var inventory = player.getInventory();

        if (inventory.containsAny(Collections.singleton(item))) {
            return false;
        }

        var stack = new ItemStack(item);
        consumer.accept(stack);

        player.getInventory().offer(stack, true);
        return true;
    }

    private static boolean tryOfferStack(ServerPlayerEntity player, Item item) {
        return tryOfferStack(player, item, stack -> {});
    }

    private static void onPlayerJoin(ServerPlayNetworkHandler handler, PacketSender packetSender, MinecraftServer server) {
        giveLobbyItems(handler.getPlayer());
    }

    public static void giveLobbyItems(ServerPlayerEntity player) {
        var config = NucleoidExtrasConfig.get();

        tryOfferStack(player, TATER_BOX);

        if (config.rules() != null) {
            tryOfferStack(player, RULE_BOOK);
        }

        config.gamePortalOpener().ifPresent(gamePortal -> {
            tryOfferStack(player, GAME_PORTAL_OPENER, stack -> {
                stack.set(NEDataComponentTypes.GAME_PORTAL, new GamePortalComponent(gamePortal));
            });
        });
    }

    public static boolean canUseTaters(ServerPlayerEntity player) {
        return !GameSpaceManager.get().inGame(player);
    }

    private static ActionResult onUseBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (!player.getWorld().isClient() && hitResult != null && hand == Hand.MAIN_HAND) {
            ItemStack stack = player.getStackInHand(hand);
            BlockPos pos = hitResult.getBlockPos();

            PlayerLobbyState state = PlayerLobbyState.get(player);
            state.collectTaterFromBlock(world, pos, stack, (ServerPlayerEntity) player);
        }

        return ActionResult.PASS;
    }

    private static ActionResult onUseEntity(PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult) {
        if (!player.getWorld().isClient() && hitResult != null) {
            ItemStack stack = player.getStackInHand(hand);
            Vec3d hitPos = hitResult.getPos().subtract(entity.getPos());

            PlayerLobbyState state = PlayerLobbyState.get(player);
            state.collectTaterFromEntity(entity, hitPos, stack, (ServerPlayerEntity) player);
        }

        return ActionResult.PASS;
    }

    private static <T extends Item> T register(String id, Item.Settings settings, Function<Item.Settings, T> factory) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, NucleoidExtras.identifier(id));
        T item = factory.apply(settings.registryKey(key));

        if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof TinyPotatoBlock) {
            TATERS.add(item);
        }

        return Registry.register(Registries.ITEM, key, item);
    }
}
