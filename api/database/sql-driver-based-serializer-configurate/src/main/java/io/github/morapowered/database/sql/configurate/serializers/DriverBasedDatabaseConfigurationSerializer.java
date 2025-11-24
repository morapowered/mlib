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

package io.github.morapowered.database.sql.configurate.serializers;

import io.github.morapowered.database.sql.configurate.util.KeyConstants;
import io.github.morapowered.database.sql.configurate.util.Util;
import io.github.morapowered.database.sql.configuration.DriverBasedDatabaseConfiguration;
import io.github.morapowered.database.sql.configuration.PoolConfiguration;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class DriverBasedDatabaseConfigurationSerializer implements TypeSerializer<DriverBasedDatabaseConfiguration> {

    @Override
    public DriverBasedDatabaseConfiguration deserialize(Type type, ConfigurationNode node) throws SerializationException {
        return DriverBasedDatabaseConfiguration.builder()
                .host(Util.requireStringOrThrow(node.node(KeyConstants.HOST)))
                .username(Util.requireStringOrThrow(node.node(KeyConstants.USERNAME)))
                .password(Util.requireStringOrThrow(node.node(KeyConstants.PASSWORD)))
                .database(Util.requireStringOrThrow(node.node(KeyConstants.DATABASE)))
                .poolConfiguration(Util.requireOrThrow(node.node(KeyConstants.POOL_CONFIGURATION), PoolConfiguration.class))
                .properties(Util.mapOrThrow(node.node(KeyConstants.PROPERTIES)))
                .tables(Util.mapOrEmpty(node, KeyConstants.TABLES))
                .build();
    }

    @Override
    public void serialize(Type type, @Nullable DriverBasedDatabaseConfiguration value, ConfigurationNode node) throws SerializationException {
        if (value == null) {
            node.set(null);
            return;
        }
        node.node(KeyConstants.HOST).set(value.getHost());
        node.node(KeyConstants.USERNAME).set(value.getUsername());
        node.node(KeyConstants.PASSWORD).set(value.getPassword());
        node.node(KeyConstants.POOL_CONFIGURATION).set(PoolConfiguration.class, value.getPoolConfiguration());
        node.node(KeyConstants.PROPERTIES).set(value.getProperties());
        if (!value.getTables().isEmpty()) {
            node.node(KeyConstants.TABLES).set(value.getTables());
        }
    }
}
