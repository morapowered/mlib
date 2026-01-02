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
import io.github.morapowered.configuration.util.UpdatedVersion;
import io.github.morapowered.configuration.value.NodeConfiguration;
import io.github.morapowered.configuration.value.ObjectMappingConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ScopedConfigurationNode;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;
import org.spongepowered.configurate.transformation.ConfigurationTransformation;

import java.io.IOException;
import java.util.function.Consumer;

public interface ConfigurationFactory<L extends AbstractConfigurationLoader<@NotNull N>,
        N extends ScopedConfigurationNode<@NotNull N>,
        B extends AbstractConfigurationLoader.Builder<B, L>> {

    static <L1 extends AbstractConfigurationLoader<@NotNull N1>,
            N1 extends ScopedConfigurationNode<@NotNull N1>,
            B1 extends AbstractConfigurationLoader.Builder<B1, L1>> Builder<L1, N1, B1> builder(Class<B1> loaderClass) {
        return new ConfigurationFactoryImpl.BuilderImpl<>(loaderClass);
    }

    @NotNull B getBuilder();

    @NotNull NodeConfiguration<L, N> load(final @NotNull ConfigurationSource source) throws IOException;

    default @NotNull NodeConfiguration<L, N> load(final @NotNull ConfigurationSource source, final @NotNull ConfigurationTransformation.Versioned versioned) throws IOException {
        final NodeConfiguration<L, N> nodeConfiguration = load(source);
        updateNode(nodeConfiguration.getNode(), versioned, (startVersion, endVersion) -> nodeConfiguration.save());
        return nodeConfiguration;
    }

    @NotNull <T> ObjectMappingConfiguration<T, L, N> loadObject(final @NotNull ConfigurationSource source, Class<T> objectClass) throws IOException;

    default @NotNull <T> ObjectMappingConfiguration<T, L, N> loadObject(final @NotNull ConfigurationSource source, Class<T> objectClass, final @NotNull ConfigurationTransformation.Versioned versioned) throws IOException {
        load(source, versioned); // Update node first, post load object mapped
        return loadObject(source, objectClass);
    }

    @NotNull <T> ObjectMappingConfiguration<T, L, N> loadObjectOrDefault(final @NotNull ConfigurationSource source, @NotNull T defaultValue) throws IOException;

    default @NotNull <T> ObjectMappingConfiguration<T, L, N> loadObjectOrDefaultAndSave(final @NotNull ConfigurationSource source, @NotNull T defaultValue) throws IOException {
        final ObjectMappingConfiguration<T, L, N> objectMappingConfiguration = loadObjectOrDefault(source, defaultValue);
        objectMappingConfiguration.saveObject();
        return objectMappingConfiguration;
    }

    default void updateNode(final @NotNull ConfigurationNode node, final @NotNull ConfigurationTransformation.Versioned versioned) throws IOException {
        updateNode(node, versioned, (startVersion, endVersion) -> {
        });
    }

    default void updateNode(final @NotNull ConfigurationNode node, final @NotNull ConfigurationTransformation.Versioned versioned, UpdatedVersion updatedVersion) throws IOException {
        if (!node.virtual()) {
            final int startVersion = versioned.version(node);
            versioned.apply(node);
            final int endVersion = versioned.version(node);
            if (startVersion != endVersion) {
                updatedVersion.onUpdate(startVersion, endVersion);
            }
        }
    }

    interface Builder<L extends AbstractConfigurationLoader<@NotNull N>,
            N extends ScopedConfigurationNode<@NotNull N>,
            B extends AbstractConfigurationLoader.Builder<@NotNull B, @NotNull L>> {

        @Contract("_ -> this")
        Builder<L, N, B> configure(Consumer<B> consumer);

        ConfigurationFactory<L, N, B> build();

    }

}
