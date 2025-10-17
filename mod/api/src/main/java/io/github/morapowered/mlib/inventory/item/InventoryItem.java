package io.github.morapowered.mlib.inventory.item;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.ButtonAction;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import io.github.morapowered.mlib.adventure.ModAdventureUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.ItemLore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public interface InventoryItem {

    @NotNull InventoryItemType getType();

    @NotNull Item getItem();

    @NotNull List<Integer> getSlots();

    @Nullable String getDisplayName();

    @Nullable List<String> getLore();

    @Nullable Integer getCustomModelData();

    default boolean hasSlots() {
        return !getSlots().isEmpty();
    }

    default ItemStack createStack() {
        return createStack(string -> string, string -> string);
    }

    default ItemStack createStack(Function<String, String> displayNameFormatter, Function<String, String> loreLineFormatter) {
        ItemStack stack = new ItemStack(getItem());
        if (getDisplayName() != null) {
            stack.set(DataComponents.ITEM_NAME, ModAdventureUtil.text(displayNameFormatter.apply(getDisplayName())));
        }
        if (getLore() != null) {
            stack.set(DataComponents.LORE, new ItemLore(getLore().stream()
                    .map(line -> ModAdventureUtil.text(loreLineFormatter.apply(line)))
                    .toList()));
        }
        if (getCustomModelData() != null) {
            stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(getCustomModelData()));
        }
        return stack;
    }

    default Button createButton(Consumer<ButtonAction> actionConsumer,
                                Consumer<ItemStack> stackConsumer,
                                Function<String, String> displayNameFormatter,
                                Function<String, String> loreLineFormatter) {
        ItemStack stack = createStack(displayNameFormatter, loreLineFormatter);
        stackConsumer.accept(stack);
        return GooeyButton.builder()
                .display(stack)
                .onClick(actionConsumer)
                .build();
    }

    default void createSimpleButton(ChestTemplate.Builder builder,
                                    Consumer<ButtonAction> actionConsumer,
                                    Consumer<ItemStack> stackConsumer,
                                    Function<String, String> displayNameFormatter,
                                    Function<String, String> loreLineFormatter) {

        getSlots().forEach(slot -> builder.set(slot, createButton(actionConsumer, stackConsumer, displayNameFormatter, loreLineFormatter)));
    }

    default void createSimpleButton(ChestTemplate template,
                                    Consumer<ButtonAction> actionConsumer,
                                    Consumer<ItemStack> stackConsumer,
                                    Function<String, String> displayNameFormatter,
                                    Function<String, String> loreLineFormatter) {
        getSlots().forEach(slot -> template.set(slot, createButton(actionConsumer, stackConsumer, displayNameFormatter, loreLineFormatter)));

    }

    abstract class Builder<T extends Builder<T>> {

        protected Item item;
        protected List<Integer> slots = new ArrayList<>();
        protected String displayName;
        protected List<String> lore;
        protected Integer customModelData;

        public T item(final @NotNull String name) {
            this.item(ResourceLocation.parse(Objects.requireNonNull(name, "name")));
            return asBuilder();
        }

        public T item(final @NotNull ResourceLocation id) {
            this.item(ResourceKey.create(BuiltInRegistries.ITEM.key(), Objects.requireNonNull(id, "id")));
            return asBuilder();
        }

        public T item(final @NotNull ResourceKey<Item> key) {
            this.item = BuiltInRegistries.ITEM.getOrThrow(Objects.requireNonNull(key, "key"));
            return asBuilder();
        }

        public T item(final @NotNull Item item) {
            this.item = Objects.requireNonNull(item, "item");
            return asBuilder();
        }

        public T slot(final int slot) {
            slots.add(slot);
            return asBuilder();
        }

        public T slots(final int... slots) {
            for (int slot : slots) {
                this.slots.add(slot);
            }
            return asBuilder();
        }

        public T slots(List<Integer> slots) {
            this.slots.addAll(Objects.requireNonNull(slots, "slots"));
            return asBuilder();
        }

        public T displayName(final @Nullable String displayName) {
            this.displayName = displayName;
            return asBuilder();
        }

        public T lore(final @Nullable List<String> lore) {
            this.lore = lore;
            return asBuilder();
        }

        public T lore(final String... lore) {
            if (this.lore == null) {
                this.lore = new ArrayList<>();
            }
            this.lore.addAll(Arrays.asList(lore));
            return asBuilder();
        }

        public T customModelData(final @Nullable Integer customModelData) {
            this.customModelData = customModelData;
            return asBuilder();
        }

        protected abstract T asBuilder();

    }

}
