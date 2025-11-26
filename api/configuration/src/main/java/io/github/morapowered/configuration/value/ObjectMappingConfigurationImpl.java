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

package io.github.morapowered.configuration.value;

import io.github.morapowered.configuration.util.ConfigurationSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ScopedConfigurationNode;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;

public class ObjectMappingConfigurationImpl<T, L extends AbstractConfigurationLoader<@NotNull N>, N extends ScopedConfigurationNode<@NotNull N>> extends  NodeConfigurationImpl<L, N> implements  ObjectMappingConfiguration<T, L, N> {

    private final T value;

    public ObjectMappingConfigurationImpl(ConfigurationSource source, L loader, N node, T value) {
        super(source, loader, node);
        this.value = value;
    }

    @Override
    public @Nullable T getValue() {
        return value;
    }

}
