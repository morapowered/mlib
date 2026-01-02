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

package io.github.morapowered.database.sql.configurate.serializers;

import io.github.morapowered.database.sql.configurate.util.KeyConstants;
import io.github.morapowered.database.sql.configurate.util.Util;
import io.github.morapowered.database.sql.configuration.PoolConfiguration;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class PoolConfigurationSerializer implements TypeSerializer<PoolConfiguration> {
    @Override
    public PoolConfiguration deserialize(Type type, ConfigurationNode node) throws SerializationException {
        return PoolConfiguration.builder()
                .maximumPoolSize(Util.requireIntOrThrow(node.node(KeyConstants.MAXIMUM_POOL_SIZE)))
                .minimumIdle(Util.requireIntOrThrow(node.node(KeyConstants.MINIMUM_IDLE)))
                .maximumLifetime(Util.requireIntOrThrow(node.node(KeyConstants.MAXIMUM_LIFETIME)))
                .connectionTimeout(Util.requireIntOrThrow(node.node(KeyConstants.CONNECTION_TIMEOUT)))
                .keepAliveTime(Util.requireIntOrThrow(node.node(KeyConstants.KEEP_ALIVE_TIME)))
                .build();
    }

    @Override
    public void serialize(Type type, @Nullable PoolConfiguration value, ConfigurationNode node) throws SerializationException {
        if (value == null) {
            node.set(null);
            return;
        }
        node.set(KeyConstants.MAXIMUM_POOL_SIZE).set(value.getMaximumPoolSize());
        node.set(KeyConstants.MINIMUM_IDLE).set(value.getMinimumIdle());
        node.set(KeyConstants.MAXIMUM_LIFETIME).set(value.getMaximumLifetime());
        node.set(KeyConstants.CONNECTION_TIMEOUT).set(value.getConnectionTimeout());
        node.set(KeyConstants.KEEP_ALIVE_TIME).set(value.getKeepAliveTime());
    }
}
