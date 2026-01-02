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

package io.github.morapowered.database.sql.configuration;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Getter
public class PoolConfigurationImpl implements PoolConfiguration {

    public static PoolConfiguration DEFAULT = new PoolConfigurationImpl(10, 10, 1800000, 5000, 0);

    private final int maximumPoolSize;
    private final int minimumIdle;
    private final int maximumLifetime;
    private final int connectionTimeout;
    private final int keepAliveTime;

    PoolConfigurationImpl(int maximumPoolSize, int minimumIdle, int maximumLifetime, int connectionTimeout, int keepAliveTime) {
        this.maximumPoolSize = maximumPoolSize;
        this.minimumIdle = minimumIdle;
        this.maximumLifetime = maximumLifetime;
        this.connectionTimeout = connectionTimeout;
        this.keepAliveTime = keepAliveTime;
    }

    static final class BuilderImpl implements Builder {

        private Integer maximumPoolSize;
        private Integer minimumIdle;
        private Integer maximumLifetime;
        private Integer connectionTimeout;
        private Integer keepAliveTime;

        BuilderImpl() {
        }


        @Override
        public @NotNull Builder maximumPoolSize(int maximumPoolSize) {
            this.maximumPoolSize = maximumPoolSize;
            return this;
        }

        @Override
        public @NotNull Builder minimumIdle(int minimumIdle) {
            this.minimumIdle = minimumIdle;
            return this;
        }

        @Override
        public @NotNull Builder maximumLifetime(int maximumLifetime) {
            this.maximumLifetime = maximumLifetime;
            return this;
        }

        @Override
        public @NotNull Builder connectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        @Override
        public @NotNull Builder keepAliveTime(int keepAliveTime) {
            this.keepAliveTime = keepAliveTime;
            return this;
        }

        @Override
        public @NotNull PoolConfiguration build() {
            return new PoolConfigurationImpl(
                    Optional.ofNullable(maximumPoolSize).orElse(DEFAULT.getMaximumPoolSize()),
                    Optional.ofNullable(minimumIdle).orElse(DEFAULT.getMinimumIdle()),
                    Optional.ofNullable(maximumLifetime).orElse(DEFAULT.getMaximumLifetime()),
                    Optional.ofNullable(connectionTimeout).orElse(DEFAULT.getConnectionTimeout()),
                    Optional.ofNullable(keepAliveTime).orElse(DEFAULT.getKeepAliveTime())
            );
        }
    }
}
