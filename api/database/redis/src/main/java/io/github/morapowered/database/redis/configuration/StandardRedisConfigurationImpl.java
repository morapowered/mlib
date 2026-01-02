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

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

@Getter
public class StandardRedisConfigurationImpl implements StandardRedisConfiguration {

    private final String host;
    private final String password;
    private final int database;
    private final boolean ssl;


    StandardRedisConfigurationImpl(String host, String password, Integer database, Boolean ssl) {
        this.host = Objects.requireNonNull(host, "host");
        this.password = Objects.requireNonNull(password, "password");
        this.database = Optional.ofNullable(database).orElse(0);
        this.ssl = Optional.ofNullable(ssl).orElse(false);
    }

    static final class BuilderImpl implements StandardRedisConfiguration.Builder {

        private String host;
        private String password;
        private Integer database;
        private Boolean ssl;


        @Override
        public @NotNull Builder host(@NotNull String host) {
            this.host = Objects.requireNonNull(host, "host");
            return this;
        }

        @Override
        public @NotNull Builder password(@NotNull String password) {
            this.password = Objects.requireNonNull(password, "password");
            return this;
        }

        @Override
        public @NotNull Builder database(int database) {
            this.database = database;
            return this;
        }

        @Override
        public @NotNull Builder ssl(boolean useSsl) {
            this.ssl = useSsl;
            return this;
        }

        @Override
        public @NotNull StandardRedisConfiguration build() {
            return new StandardRedisConfigurationImpl(host, password, database, ssl);
        }
    }


}
