/*
 * This file is licensed under the MIT License.
 *
 * Copyright (c) 2025 Pedro Souza
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
import io.github.morapowered.inventory.pages.config.PageConfigurationAbstractBuilder;
import io.github.morapowered.inventory.pages.simple.config.SimplePageInventoryConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public interface MultiPageInventoryConfiguration extends SimplePageInventoryConfiguration {

    static Builder builder() {
        return new MultiPageInventoryConfigurationImpl.BuilderImpl();
    }

    @NotNull Set<Integer> getSlots();

    @NotNull SimpleItem getPreviousItem();

    @NotNull SimpleItem getNextItem();

    interface Builder extends PageConfigurationAbstractBuilder<Builder, MultiPageInventoryConfiguration> {

        @Contract("_, _, _ -> this")
        @NotNull
        Builder startAndEnd(final int start, final int end, final Integer... noSlots);

        @Contract("_ -> this")
        @NotNull
        Builder noSlot(final int slot);

        @Contract("_ -> this")
        @NotNull
        Builder noSlots(final @NotNull Collection<Integer> slots);

        @Contract("_ -> this")
        @NotNull
        default Builder noSlots(final Integer... slots) {
            return noSlots(Arrays.asList(slots));
        }

        @Contract("_ -> this")
        @NotNull
        Builder slots(final Collection<Integer> slots);

        @Contract("_ -> this")
        @NotNull
        default Builder slots(final Integer... slots) {
            return slots(Arrays.asList(slots));
        }

        @Contract("_ -> this")
        @NotNull
        Builder slot(final int slot);

        @Contract("_ -> this")
        @NotNull
        Builder previousItem(final @NotNull SimpleItem item);

        @Contract("_ -> this")
        @NotNull
        Builder nextItem(final @NotNull SimpleItem item);

    }


}
