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

package io.github.morapowered.configuration.serializer;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.function.BiFunction;

public interface DelegatingTypeSerializer<T> extends TypeSerializer<T> {

    static <V> DelegatingTypeSerializer<V> of(final @NotNull DelegateSerializer<V> serializer, final @NotNull DelegateDeserializer<V> deserializer) {
        return of(serializer, deserializer, (type, configurationOptions) -> null);
    }

    static <V> DelegatingTypeSerializer<V> of(final @NotNull DelegateSerializer<V> serializer,
                                              final @NotNull DelegateDeserializer<V> deserializer,
                                              final @NotNull BiFunction<Type, ConfigurationOptions, V> emptyValue) {
        return new DelegatingTypeSerializerImpl<>(serializer, deserializer, emptyValue);
    }

    static <V> Builder<V> builder() {
        return new DelegatingTypeSerializerImpl.BuilderImpl<>();
    }

    @NotNull DelegateSerializer<T> getSerializer();

    @NotNull DelegateDeserializer<T> getDeserializer();

    @NotNull BiFunction<Type, ConfigurationOptions, T> getEmptyValue();

    @Override
    default T deserialize(Type type, ConfigurationNode node) throws SerializationException {
        return getDeserializer().deserialize(type, node);
    }

    @Override
    default void serialize(Type type, @Nullable T obj, ConfigurationNode node) throws SerializationException {
        getSerializer().serialize(type, obj, node);
    }

    @Override
    @Nullable
    default T emptyValue(Type specificType, ConfigurationOptions options) {
        return getEmptyValue().apply(specificType, options);
    }

    interface Builder<T> {

        @Contract("_ -> this")
        @NotNull Builder<T> serializer(final @NotNull DelegateSerializer<T> delegate);

        @Contract("_ -> this")
        @NotNull Builder<T> deserializer(final @NotNull DelegateDeserializer<T> delegate);

        @Contract("_ -> this")
        @NotNull Builder<T> emptyValue(final @NotNull BiFunction<Type, ConfigurationOptions, T> function);

        @NotNull DelegatingTypeSerializer<T> build();
    }

}
