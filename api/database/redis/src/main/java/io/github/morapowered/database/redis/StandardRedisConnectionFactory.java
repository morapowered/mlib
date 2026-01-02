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

package io.github.morapowered.database.redis;

import io.github.morapowered.database.redis.configuration.RedisConfiguration;
import io.github.morapowered.database.redis.configuration.StandardRedisConfiguration;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.RedisURI;
import org.jetbrains.annotations.NotNull;

public class StandardRedisConnectionFactory implements RedisConnectionFactory {

    public static StandardRedisConnectionFactory create() {
        return new StandardRedisConnectionFactory();
    }

    private RedisClient client;

    public StandardRedisConnectionFactory() {
        // Empty constructor
    }

    @Override
    public void setup(@NotNull RedisConfiguration passedConfiguration) {
        if (!(passedConfiguration instanceof StandardRedisConfiguration configuration)) {
            throw new IllegalStateException("Invalid standard configuration");
        }
        String[] split = configuration.getHost().split(":");
        String address = split[0];
        int port = split.length > 1 ? Integer.parseInt(split[1]) : RedisURI.DEFAULT_REDIS_PORT;

        RedisURI uri = RedisURI.builder()
                .withHost(address)
                .withPort(port)
                .withPassword(configuration.getPassword())
                .withDatabase(configuration.getDatabase())
                .withSsl(configuration.isSsl())
                .build();
        this.client = RedisClient.create(uri);
    }

    @Override
    public void shutdown() {
        if (client != null) {
            client.shutdown();
        }

    }

    @Override
    public @NotNull RedisClient getClient() throws RedisConnectionException {
        if (client == null) {
            throw new IllegalStateException("Client not initialized (client returned null)");
        }
        return client;
    }
}
