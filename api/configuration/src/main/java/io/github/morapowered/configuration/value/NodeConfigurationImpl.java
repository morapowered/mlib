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

import io.github.morapowered.util.io.Duplex;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ScopedConfigurationNode;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;

import java.util.Objects;

public class NodeConfigurationImpl<L extends AbstractConfigurationLoader<N>, N extends ScopedConfigurationNode<@NotNull N>> implements NodeConfiguration<L, N> {

    private final Duplex duplex;
    private final L loader;
    private final N node;


    public NodeConfigurationImpl(Duplex duplex, L loader, N node) {
        this.duplex = duplex;
        this.loader = Objects.requireNonNull(loader, "loader");
        this.node = Objects.requireNonNull(node, "node");

    }

    @Override
    public @NotNull Duplex getDuplex() {
        return duplex;
    }

    @Override
    public @NotNull L getLoader() {
        return loader;
    }

    @Override
    public @NotNull N getNode() {
        return node;
    }

}