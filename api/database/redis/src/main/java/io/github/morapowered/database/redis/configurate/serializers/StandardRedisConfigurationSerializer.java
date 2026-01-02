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

package io.github.morapowered.database.redis.configurate.serializers;

import io.github.morapowered.database.redis.configurate.util.KeyConstants;
import io.github.morapowered.database.redis.configurate.util.Util;
import io.github.morapowered.database.redis.configuration.StandardRedisConfiguration;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class StandardRedisConfigurationSerializer implements TypeSerializer<StandardRedisConfiguration> {

    @Override
    public StandardRedisConfiguration deserialize(Type type, ConfigurationNode node) throws SerializationException {
        return StandardRedisConfiguration.builder()
                .host(Util.requireStringOrThrow(node.node(KeyConstants.HOST)))
                .password(Util.requireStringOrThrow(node.node(KeyConstants.PASSWORD)))
                .database(Util.requireIntOrThrow(node.node(KeyConstants.DATABSE)))
                .ssl(Util.requireBoolenOrThrow(node.node(KeyConstants.USE_SSL)))
                .build();
    }

    @Override
    public void serialize(Type type, @Nullable StandardRedisConfiguration value, ConfigurationNode node) throws SerializationException {
        if (value == null) {
            node.set(null);
            return;
        }
        node.node(KeyConstants.HOST).set(value.getHost());
        node.node(KeyConstants.PASSWORD).set(value.getPassword());
        node.node(KeyConstants.DATABSE).set(value.getDatabase());
        node.node(KeyConstants.USE_SSL).set(value.isSsl());
    }
}
