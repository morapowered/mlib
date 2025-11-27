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

package io.github.morapowered.database.mongo.configuration;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
public class MongoConfigurationImpl implements MongoConfiguration {

    private final String connectionString;

    MongoConfigurationImpl(String connectionString) {
        this.connectionString = Objects.requireNonNull(connectionString, "connectionString");
    }

    static final class BuilderImpl implements MongoConfiguration.Builder {

        private String connectionString;

        BuilderImpl() {
        }

        @Override
        public @NotNull Builder connectionString(@NotNull String connectionString) {
            this.connectionString = Objects.requireNonNull(connectionString, "connectionString");
            return this;
        }

        @Override
        public @NotNull MongoConfiguration build() {
            return new MongoConfigurationImpl(connectionString);
        }
    }

}
