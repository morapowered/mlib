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

package io.github.morapowered.configuration;

import io.github.morapowered.configuration.util.ConfigurationSource;
import io.github.morapowered.configuration.value.NodeConfiguration;
import io.github.morapowered.configuration.value.NodeConfigurationImpl;
import io.github.morapowered.configuration.value.ObjectMappingConfiguration;
import io.github.morapowered.configuration.value.ObjectMappingConfigurationImpl;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ScopedConfigurationNode;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.function.Consumer;

public class ConfigurationFactoryImpl<L extends AbstractConfigurationLoader<@NotNull N>,
        N extends ScopedConfigurationNode<@NotNull N>,
        B extends AbstractConfigurationLoader.Builder<B, L>> implements ConfigurationFactory<L, N, B> {

    private final B builder;

    public ConfigurationFactoryImpl(B builder) {
        this.builder = Objects.requireNonNull(builder, "builder");
    }

    @Override
    public @NotNull B getBuilder() {
        return builder;
    }

    @Override
    public @NotNull NodeConfiguration<L, N> load(@NotNull ConfigurationSource source) throws IOException {
        Objects.requireNonNull(source, "source");
        L loader = builder.source(source.getSource()).sink(source.getSink()).build();
        N node = loader.load();
        return new NodeConfigurationImpl<>(source, loader, node);
    }

    @Override
    public @NotNull <T> ObjectMappingConfiguration<T, L, N> loadObject(@NotNull ConfigurationSource source, Class<T> objectClass) throws IOException {
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(objectClass, "objectClass");
        L loader = builder.source(source.getSource()).sink(source.getSink()).build();
        N node = loader.load();
        T value = node.get(objectClass);
        return new ObjectMappingConfigurationImpl<>(source, loader, node, value);
    }

    @Override
    public @NotNull <T> ObjectMappingConfiguration<T, L, N> loadObjectOrDefault(@NotNull ConfigurationSource source, @NotNull T defaultValue) throws IOException {
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(defaultValue, "defaultValue");
        L loader = builder.source(source.getSource()).sink(source.getSink()).build();
        N node = loader.load();
        @SuppressWarnings("unchecked")
        T value = (T) node.get(defaultValue.getClass(), defaultValue);
        return new ObjectMappingConfigurationImpl<>(source, loader, node, value);
    }

    static class BuilderImpl<L extends AbstractConfigurationLoader<@NotNull N>,
            N extends ScopedConfigurationNode<@NotNull N>,
            B extends AbstractConfigurationLoader.Builder<@NotNull B, @NotNull L>> implements Builder<L, N, B> {

        private final B builder;

        BuilderImpl(Class<B> builderClass) {
            try {
                Constructor<B> constructor = builderClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                builder = constructor.newInstance();
                constructor.setAccessible(false);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new IllegalStateException("invalid builder", e);
            }
        }


        @Override
        public Builder<L, N, B> configure(Consumer<B> consumer) {
            consumer.accept(builder);
            return this;
        }

        @Override
        public ConfigurationFactory<L, N, B> build() {
            return new ConfigurationFactoryImpl<>(builder);
        }

    }
}
