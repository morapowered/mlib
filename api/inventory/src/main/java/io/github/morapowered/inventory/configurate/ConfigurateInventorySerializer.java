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

package io.github.morapowered.inventory.configurate;

import io.github.morapowered.inventory.configurate.serializer.ConfigurateItemDeserializerFunction;
import io.github.morapowered.inventory.configurate.serializer.ConfigurateItemSerializer;
import io.github.morapowered.inventory.configurate.serializer.ConfigurateItemSerializerFunction;
import io.github.morapowered.inventory.configurate.serializer.DelegatingConfigurateItemSerializer;
import io.github.morapowered.inventory.item.ItemType;
import io.github.morapowered.inventory.item.SimpleItem;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.serialize.TypeSerializer;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

public interface ConfigurateInventorySerializer {

    static ConfigurateInventorySerializer configurate() {
        return ConfigurateInventorySerializerImpl.INSTANCE;
    }

    static Builder builder() {
        return new ConfigurateInventorySerializerImpl.BuilderImpl();
    }

    @NotNull TypeSerializerCollection serializers();

    interface Builder {


        @Contract("_, _ -> this")
        @NotNull <T> Builder register(final @NotNull Class<T> clazz, final @NotNull TypeSerializer<T> serializer);

        @Contract("_, _ -> this")
        @NotNull <T extends SimpleItem> Builder register(final @NotNull ItemType type, final @NotNull ConfigurateItemSerializer<T> serializer);

        @Contract
        default @NotNull <T extends SimpleItem> Builder register(final @NotNull ItemType type,
                                                                 final @NotNull ConfigurateItemSerializerFunction<T> serializerFunction,
                                                                 final @NotNull ConfigurateItemDeserializerFunction<T> deserializerFunction) {
            return register(type, new DelegatingConfigurateItemSerializer<>(serializerFunction, deserializerFunction));
        }

        @NotNull ConfigurateInventorySerializer build();

    }

}
