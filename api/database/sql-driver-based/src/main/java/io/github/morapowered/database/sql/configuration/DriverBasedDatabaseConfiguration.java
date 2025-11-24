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

package io.github.morapowered.database.sql.configuration;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public interface DriverBasedDatabaseConfiguration extends DatabaseConfiguration {

    static DriverBasedDatabaseConfiguration create(final @NotNull String host,
                                                   final @NotNull String username,
                                                   final @NotNull String password,
                                                   final @NotNull String database,
                                                   final @NotNull PoolConfiguration poolConfiguration,
                                                   final @NotNull HashMap<String, String> properties,
                                                   final @NotNull HashMap<String, String> tables) {
        return new DriverBasedDatabaseConfigurationImpl(host, username, password, database, poolConfiguration, properties, tables);
    }


    static Builder builder() {
        return new DriverBasedDatabaseConfigurationImpl.BuilderImpl();
    }


    @NotNull String getHost();

    @NotNull String getUsername();

    @NotNull String getPassword();

    @NotNull String getDatabase();

    @NotNull Map<String, String> getProperties();

    @NotNull PoolConfiguration getPoolConfiguration();

    @NotNull Map<String, String> getTables();

    interface Builder {

        @Contract("_ -> this")
        @NotNull Builder host(final @NotNull String host);

        @Contract("_ -> this")
        @NotNull Builder username(final @NotNull String username);

        @Contract("_ -> this")
        @NotNull Builder password(final @NotNull String password);

        @Contract("_ -> this")
        @NotNull Builder database(final @NotNull String database);

        @Contract("_ -> this")
        @NotNull Builder properties(final @NotNull Map<String, String> properties);

        @Contract("_, _ -> this")
        @NotNull Builder property(final @NotNull String propertyName, final @NotNull String propertyValue);

        @Contract("_ -> this")
        @NotNull Builder poolConfiguration(final @NotNull PoolConfiguration configuration);

        @Contract("_ -> this")
        @NotNull Builder tables(final @NotNull Map<String, String> tables);

        @Contract("_, _ -> this")
        @NotNull Builder table(final @NotNull String id, final @NotNull String tableName);

        @NotNull DriverBasedDatabaseConfiguration build();

    }


}
