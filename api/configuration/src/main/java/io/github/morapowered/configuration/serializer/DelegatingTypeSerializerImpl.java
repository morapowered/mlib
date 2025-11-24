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

package io.github.morapowered.configuration.serializer;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationOptions;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

public class DelegatingTypeSerializerImpl<T> implements DelegatingTypeSerializer<T> {

    private final DelegateSerializer<T> serializer;
    private final DelegateDeserializer<T> deserializer;
    private final BiFunction<Type, ConfigurationOptions, T> emptyValue;

    public DelegatingTypeSerializerImpl(DelegateSerializer<T> serializer,
                                        DelegateDeserializer<T> deserializer,
                                        BiFunction<Type, ConfigurationOptions, T> emptyValue) {
        this.serializer = Objects.requireNonNull(serializer, "serializer");
        this.deserializer = Objects.requireNonNull(deserializer, "deserializer");
        this.emptyValue = Optional.ofNullable(emptyValue).orElse((type, configurationOptions) -> null);
    }

    @Override
    public @NotNull DelegateSerializer<T> getSerializer() {
        return serializer;
    }

    @Override
    public @NotNull DelegateDeserializer<T> getDeserializer() {
        return deserializer;
    }

    @Override
    public @NotNull BiFunction<Type, ConfigurationOptions, T> getEmptyValue() {
        return emptyValue;
    }

    static final class BuilderImpl<T> implements DelegatingTypeSerializer.Builder<T> {

        private DelegateSerializer<T> serializer;

        private DelegateDeserializer<T> deserializer;
        private BiFunction<Type, ConfigurationOptions, T> emptyValue;

        BuilderImpl() {
        }

        @Override
        public @NotNull Builder<T> serializer(@NotNull DelegateSerializer<T> delegate) {
            this.serializer = Objects.requireNonNull(delegate, "delegate");
            return this;
        }

        @Override
        public @NotNull Builder<T> deserializer(@NotNull DelegateDeserializer<T> delegate) {
            this.deserializer = Objects.requireNonNull(delegate, "delegate");
            return this;
        }

        @Override
        public @NotNull Builder<T> emptyValue(@NotNull BiFunction<Type, ConfigurationOptions, T> function) {
            this.emptyValue = Objects.requireNonNull(function, "function");
            return this;
        }

        @Override
        public @NotNull DelegatingTypeSerializer<T> build() {
            return new DelegatingTypeSerializerImpl<>(serializer, deserializer, emptyValue);
        }
    }

}
