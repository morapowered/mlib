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

package io.github.morapowered.database.redis.configuration;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface StandardRedisConfiguration extends RedisConfiguration {

    static StandardRedisConfiguration create(@NotNull String host, @NotNull String password, int database, boolean ssl) {
        return new StandardRedisConfigurationImpl(host, password, database, ssl);
    }

    static Builder builder() {
        return new StandardRedisConfigurationImpl.BuilderImpl();
    }

    @NotNull String getHost();

    @NotNull String getPassword();

    int getDatabase();

    boolean isSsl();

    interface Builder {

        @Contract("_ -> this")
        @NotNull Builder host(final @NotNull String host);

        @Contract("_ -> this")
        @NotNull Builder password(final @NotNull String password);

        @Contract("_ -> this")
        @NotNull Builder database(final int database);

        @Contract("_ -> this")
        @NotNull Builder ssl(final boolean useSsl);

        @NotNull StandardRedisConfiguration build();

    }

}
