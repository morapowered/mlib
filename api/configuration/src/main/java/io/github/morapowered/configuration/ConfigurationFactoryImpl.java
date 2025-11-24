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

package io.github.morapowered.configuration;

import io.github.morapowered.configuration.value.NodeConfiguration;
import io.github.morapowered.configuration.value.NodeConfigurationImpl;
import io.github.morapowered.configuration.value.ObjectMappingConfiguration;
import io.github.morapowered.configuration.value.ObjectMappingConfigurationImpl;
import io.github.morapowered.util.io.Duplex;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
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
    public @NotNull NodeConfiguration<L, N> load(@NotNull Duplex duplex) throws IOException {
        Objects.requireNonNull(duplex, "duplex");
        if (duplex.isReadable()) {
            throw new ConfigurateException("No source present to read from!");
        }
        L loader = builder.source(duplex::createReader).sink(duplex::createWriter).build();
        N node = loader.load();
        return new NodeConfigurationImpl<>(duplex, loader, node);
    }

    @Override
    public @NotNull <T> ObjectMappingConfiguration<T, L, N> loadObject(@NotNull Duplex duplex, Class<T> objectClass) throws IOException {
        Objects.requireNonNull(duplex, "duplex");
        Objects.requireNonNull(objectClass, "objectClass");
        if (duplex.isReadable()) {
            throw new ConfigurateException("No source present to read from!");
        }
        L loader = builder.source(duplex::createReader).sink(duplex::createWriter).build();
        N node = loader.load();
        T value = node.get(objectClass);
        return new ObjectMappingConfigurationImpl<>(duplex, loader, node, value);
    }

    @Override
    public @NotNull <T> ObjectMappingConfiguration<T, L, N> loadObjectOrDefault(@NotNull Duplex duplex, @NotNull T defaultValue) throws IOException {
        Objects.requireNonNull(duplex, "duplex");
        Objects.requireNonNull(defaultValue, "defaultValue");
        if (duplex.isReadable()) {
            throw new ConfigurateException("No source present to read from!");
        }
        L loader = builder.source(duplex::createReader).sink(duplex::createWriter).build();
        N node = loader.load();
        @SuppressWarnings("unchecked")
        T value = (T) node.get(defaultValue.getClass(), defaultValue);
        return new ObjectMappingConfigurationImpl<>(duplex, loader, node, value);
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
