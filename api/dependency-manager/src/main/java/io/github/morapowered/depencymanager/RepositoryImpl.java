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

package io.github.morapowered.depencymanager;

import io.github.morapowered.util.function.CheckedBiFunction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URLConnection;
import java.util.Objects;
import java.util.Optional;

/*
 * Based on concepts and structure from the "LuckPerms" (https://github.com/LuckPerms/LuckPerms/)
 * (c) lucko (Luck) <luck@lucko.me> and contributors — licensed under the MIT License
 * Rewritten and adapted by Pedro Souza in 2025
 */
@Getter
@EqualsAndHashCode
@ToString
public class RepositoryImpl implements Repository {

    private final String url;
    private final CheckedBiFunction<Dependency, Repository, URLConnection, IOException> connection;

    public RepositoryImpl(String url, CheckedBiFunction<Dependency, Repository, URLConnection, IOException> connection) {
        Objects.requireNonNull(url, "url");
        this.url = url.endsWith("/") ? url : url + "/";
        this.connection = Optional.ofNullable(connection).orElse(Repository.DEFAULT_CONNECTION);
    }

    public static final class BuilderImpl implements Repository.Builder {
        private String url;
        private CheckedBiFunction<Dependency, Repository, URLConnection, IOException> connection;

        BuilderImpl() {
        }


        public @NotNull Builder url(@NotNull String url) {
            this.url = Objects.requireNonNull(url, "url");
            return this;
        }

        @Override
        public @NotNull Builder connection(@NotNull CheckedBiFunction<Dependency, Repository, URLConnection, IOException> connection) {
            this.connection = Objects.requireNonNull(connection, "connection");
            return this;
        }


        public @NotNull RepositoryImpl build() {
            return new RepositoryImpl(url, connection);
        }
    }
}
