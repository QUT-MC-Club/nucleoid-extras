package xyz.nucleoid.extras.data.provider;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRequirements.CriterionMerger;
import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.nucleoid.extras.NucleoidExtras;
import xyz.nucleoid.extras.lobby.NEBlocks;
import xyz.nucleoid.extras.lobby.NECriteria;
import xyz.nucleoid.extras.lobby.NEItems;
import xyz.nucleoid.extras.lobby.block.tater.TinyPotatoBlock;
import xyz.nucleoid.extras.lobby.criterion.TaterCollectedCriterion;
import xyz.nucleoid.extras.lobby.criterion.TaterCount;
import xyz.nucleoid.extras.lobby.criterion.WearTaterCriterion;

public class NEAdvancementProvider extends FabricAdvancementProvider {
    public NEAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registries, Consumer<AdvancementEntry> consumer) {
        var root = accept(consumer, "root", null, Advancement.Builder.createUntelemetered()
                .display(
                        NEItems.NUCLEOID_LOGO,
                        Text.translatable("advancements.nucleoid_extras.root.title"),
                        Text.translatable("advancements.nucleoid_extras.root.description"),
                        Identifier.ofVanilla("textures/block/lime_concrete.png"),
                        AdvancementFrame.TASK,
                        false,
                        false,
                        false
                )
                .criterion("get_tater", NECriteria.TATER_COLLECTED.create(
                        new TaterCollectedCriterion.Conditions(Optional.empty(), Optional.of(new TaterCount.Value(1)))
                ))
        );

        // Based on number collected
        var firstTater = accept(consumer, "first_tater", NEBlocks.QUTMC_LOGO, requiringTatersCollected(1).parent(root));
//        var tenTaters = accept(consumer, "ten_taters", NEBlocks.IRON_TATER, AdvancementFrame.GOAL, requiringTatersCollected(10).parent(firstTater));
//        var twentyFiveTaters = accept(consumer, "twenty_five_taters", NEBlocks.GOLD_TATER, AdvancementFrame.GOAL, requiringTatersCollected(25).parent(tenTaters));
//        var fiftyTaters = accept(consumer, "fifty_taters", NEBlocks.DIAMOND_TATER, AdvancementFrame.GOAL, requiringTatersCollected(50).parent(twentyFiveTaters));
//        var oneHundredTaters = accept(consumer, "one_hundred_taters", NEBlocks.EMERALD_TATER, AdvancementFrame.GOAL, requiringTatersCollected(100).parent(fiftyTaters));
//        var twoHundredTaters = accept(consumer, "two_hundred_taters", NEBlocks.NETHERITE_TATER, AdvancementFrame.GOAL, requiringTatersCollected(200).parent(oneHundredTaters));
//
//        accept(consumer, "all_taters", null, requiringTatersCollected(new TaterCount.All())
//                .display(
//                        NEBlocks.TATER_OF_UNDYING,
//                        Text.translatable("advancements.nucleoid_extras.all_taters.title"),
//                        Text.translatable("advancements.nucleoid_extras.all_taters.description"),
//                        null,
//                        AdvancementFrame.CHALLENGE,
//                        true,
//                        true,
//                        false
//                )
//                .parent(twoHundredTaters)
//        );

        accept(consumer, "pride_collectables", NEBlocks.GAY_COLLECTABLE, requiringTatersCollected(NEBlocks.ASEXUAL_COLLECTABLE, NEBlocks.BI_COLLECTABLE, NEBlocks.DEMISEXUAL_COLLECTABLE, NEBlocks.GAY_COLLECTABLE, NEBlocks.GENDERFLUID_COLLECTABLE, NEBlocks.LESBIAN_COLLECTABLE, NEBlocks.NONBINARY_COLLECTABLE, NEBlocks.PAN_COLLECTABLE, NEBlocks.TRANS_COLLECTABLE).parent(firstTater));

    }

    private static Advancement.Builder requiringTatersCollected(int count) {
        return requiringTatersCollected(new TaterCount.Value(count));
    }

    private static Advancement.Builder requiringTatersCollected(TaterCount count) {
        var builder = Advancement.Builder.createUntelemetered();

        var name = "get_" + count.count(null) + "_tater" + (count.count(null) == 1 ? "" : "s");
        var conditions = new TaterCollectedCriterion.Conditions(Optional.empty(), Optional.of(count));

        builder.criterion(name, NECriteria.TATER_COLLECTED.create(conditions));

        return builder;
    }

    private static Advancement.Builder requiringTatersCollected(Block... taters) {
        var builder = Advancement.Builder.createUntelemetered();

        for (Block tater : taters) {
            var id = Registries.BLOCK.getId(tater);
            var name = "get_" + id.getPath();

            var conditions = new TaterCollectedCriterion.Conditions(getTaterEntry(tater), Optional.empty());

            builder.criterion(name, NECriteria.TATER_COLLECTED.create(conditions));
        }

        return builder;
    }

    private static AdvancementEntry accept(Consumer<AdvancementEntry> consumer, String path, ItemConvertible icon, Advancement.Builder builder) {
        return accept(consumer, path, icon, AdvancementFrame.TASK, builder);
    }

    private static AdvancementEntry accept(Consumer<AdvancementEntry> consumer, String path, ItemConvertible icon, AdvancementFrame frame, Advancement.Builder builder) {
        if (icon != null) {
            builder.display(
                    icon,
                    Text.translatable("advancements.nucleoid_extras." + path + ".title"),
                    Text.translatable("advancements.nucleoid_extras." + path + ".description"),
                    null,
                    frame,
                    true,
                    true,
                    false
            );
        }

        var id = NucleoidExtras.identifier("taters/" + path);
        var advancement = builder.build(id);

        consumer.accept(advancement);
        return advancement;
    }

    private static Optional<RegistryEntry<Block>> getTaterEntry(Block block) {
        if (block instanceof TinyPotatoBlock tater) {
            return Optional.of(tater.getRegistryEntry());
        }

        throw new IllegalArgumentException("Not a tater: " + block);
    }
}
