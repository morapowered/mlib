/*
 * This file is licensed under the MIT License.
 *
 * Copyright (c) 2025-2026 Pedro Souza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.morapowered.inventory.pages.multi.config;

import io.github.morapowered.inventory.item.SimpleItem;
import io.github.morapowered.inventory.pages.config.PageConfigurationAbstractBuilderImpl;
import io.github.morapowered.inventory.pages.simple.config.SimplePageInventoryConfigurationImpl;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
public class MultiPageInventoryConfigurationImpl extends SimplePageInventoryConfigurationImpl implements MultiPageInventoryConfiguration {

    private final Set<Integer> slots;
    private final SimpleItem previousItem;
    private final SimpleItem nextItem;

    MultiPageInventoryConfigurationImpl(String title, int rows, HashMap<String, SimpleItem> staticItems, final @NotNull Set<Integer> slots, final @NotNull SimpleItem previousItem, final @NotNull SimpleItem nextItem) {
        super(title, rows, staticItems);
        this.slots = Objects.requireNonNull(slots, "slots");
        this.previousItem = Objects.requireNonNull(previousItem, "previousItem");
        this.nextItem = Objects.requireNonNull(nextItem, "nextItem");
    }

    static class BuilderImpl extends PageConfigurationAbstractBuilderImpl<MultiPageInventoryConfiguration.Builder, MultiPageInventoryConfiguration> implements MultiPageInventoryConfiguration.Builder {

        protected Integer start; // Default: 10
        protected Integer end; // Default: 43
        protected Set<Integer> noSlots; // Default [17, 18, 26, 27, 35, 36]
        protected Set<Integer> slots;
        protected SimpleItem previousItem = SimpleItem.builder()
                .slot(46)
                .displayName("{green}Previous Page")
                .lore("{gray}Click to go to the previous page.")
                .build();
        protected SimpleItem nextItem = SimpleItem.builder()
                .slot(52)
                .displayName("{green}Next Page")
                .lore("{gray}Click to go to the next page.")
                .build();


        BuilderImpl() {
            this.rows = 6;
        }

        @Override
        public MultiPageInventoryConfiguration.@NotNull Builder startAndEnd(int start, int end, Integer... noSlots) {
            if (start > end) {
                throw new IllegalArgumentException("Start needs to bigger than the end.");
            }
            if (start < 0) {
                throw new IllegalArgumentException("Invalid start range [0, 53]: " + start);
            }
            if (end > 53) {
                throw new IllegalArgumentException("Invalid end range [0, 53]: " + end);
            }
            this.start = start;
            this.end = end;
            if (noSlots != null) {
                if (this.noSlots == null) {
                    this.noSlots = new HashSet<>();
                }
                for (Integer noSlot : noSlots) {
                    if (noSlot < 0 || noSlot > 53) {
                        throw new IllegalArgumentException("Invalid slot range [0, 53]: " + noSlot);
                    }
                    this.noSlots.add(noSlot);
                }
            }
            return this;
        }

        @Override
        public MultiPageInventoryConfiguration.@NotNull Builder noSlot(int slot) {
            if (slot < 0 || slot > 53) {
                throw new IllegalArgumentException("Invalid slot range [0, 53]: " + slot);
            }
            if (noSlots == null) {
                noSlots = new HashSet<>();
            }
            noSlots.add(slot);
            return this;
        }

        @Override
        public MultiPageInventoryConfiguration.@NotNull Builder noSlots(@NotNull Collection<Integer> slots) {
            if (noSlots == null) {
                noSlots = new HashSet<>();
            }
            for (Integer noSlot : slots) {
                if (noSlot < 0 || noSlot > 53) {
                    throw new IllegalArgumentException("Invalid slot range [0, 53]: " + noSlot);
                }
                this.noSlots.add(noSlot);
            }
            return this;
        }

        @Override
        public MultiPageInventoryConfiguration.@NotNull Builder slots(Collection<Integer> slots) {
            if (this.slots == null) {
                this.slots = new HashSet<>();
            }
            for (Integer slot : slots) {
                if (slot < 0 || slot > 53) {
                    throw new IllegalArgumentException("Invalid slot range [0, 53]: " + slot);
                }
                this.slots.add(slot);
            }
            return this;
        }

        @Override
        public MultiPageInventoryConfiguration.@NotNull Builder slot(int slot) {
            if (slot < 0 || slot > 53) {
                throw new IllegalArgumentException("Invalid slot range [0, 53]: " + slot);
            }
            if (this.slots == null) {
                this.slots = new HashSet<>();
            }
            this.slots.add(slot);
            return this;
        }

        @Override
        public MultiPageInventoryConfiguration.@NotNull Builder previousItem(@NotNull SimpleItem item) {
            this.previousItem = Objects.requireNonNull(item, "item");
            return this;
        }

        @Override
        public MultiPageInventoryConfiguration.@NotNull Builder nextItem(@NotNull SimpleItem item) {
            this.nextItem = Objects.requireNonNull(item, "item");
            return this;
        }

        @Override
        public @NotNull BuilderImpl asBuilder() {
            return this;
        }

        @Override
        public @NotNull MultiPageInventoryConfiguration build() {
            Set<Integer> finalSlots = new HashSet<>();
            if (start != null && end != null) {
                for (int slot = start; slot <= end; slot++) {
                    if (noSlots != null && noSlots.contains(slot)) {
                        continue;
                    }
                    finalSlots.add(slot);
                }
            }
            if (slots != null) {
                finalSlots.addAll(slots);
            }
            return new MultiPageInventoryConfigurationImpl(title, rows, staticItems, finalSlots, previousItem, nextItem);
        }
    }

}
