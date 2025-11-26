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

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class DriverBasedDatabaseConfigurationImpl implements DriverBasedDatabaseConfiguration {

    public static final Map<String, String> DEFAULT_PROPERTIES = Map.ofEntries(Map.entry("useUnicode", "true"), Map.entry("characterEncoding", "utf8"));

    private final String host;
    private final String username;
    private final String password;
    private final String database;
    private final PoolConfiguration poolConfiguration;
    private final HashMap<String, String> properties;
    private final HashMap<String, String> tables;

    DriverBasedDatabaseConfigurationImpl(String host, String username, String password, String database, PoolConfiguration poolConfiguration, HashMap<String, String> properties, HashMap<String, String> tables) {
        this.host = Objects.requireNonNull(host, "host");
        this.username = Objects.requireNonNull(username, "username");
        this.password = Objects.requireNonNull(password, "password");
        this.database = Objects.requireNonNull(database, "database");
        this.poolConfiguration = Objects.requireNonNull(poolConfiguration, "poolConfiguration");
        this.properties = Objects.requireNonNull(properties, "properties");
        this.tables = Objects.requireNonNull(tables, "tables");
    }

    static final class BuilderImpl implements Builder {

        private String host;
        private String username;
        private String password;
        private String database;
        private PoolConfiguration poolConfiguration = PoolConfiguration.defaults();
        private final HashMap<String, String> properties = new HashMap<>(DEFAULT_PROPERTIES);
        private final HashMap<String, String> tables = new HashMap<>();

        BuilderImpl() {
        }


        @Override
        public @NotNull Builder host(@NotNull String host) {
            this.host = Objects.requireNonNull(host, "host");
            return this;
        }

        @Override
        public @NotNull Builder username(@NotNull String username) {
            this.username = Objects.requireNonNull(username, "username");
            return this;
        }

        @Override
        public @NotNull Builder password(@NotNull String password) {
            this.password = Objects.requireNonNull(password, "password");
            return this;
        }

        @Override
        public @NotNull Builder database(@NotNull String database) {
            this.database = Objects.requireNonNull(database, "database");
            return this;
        }

        @Override
        public @NotNull Builder properties(@NotNull Map<String, String> properties) {
            this.properties.putAll(Objects.requireNonNull(properties, "properties"));
            return this;
        }

        @Override
        public @NotNull Builder property(@NotNull String propertyName, @NotNull String propertyValue) {
            this.properties.put(Objects.requireNonNull(propertyName, "propertyName"), Objects.requireNonNull(propertyValue, "propertyValue"));
            return this;
        }

        @Override
        public @NotNull Builder poolConfiguration(@NotNull PoolConfiguration configuration) {
            this.poolConfiguration = Objects.requireNonNull(configuration, "configuration");
            return this;
        }

        @Override
        public @NotNull Builder tables(@NotNull Map<String, String> tables) {
            this.tables.putAll(Objects.requireNonNull(tables, "tables"));
            return this;
        }

        @Override
        public @NotNull Builder table(@NotNull String id, @NotNull String tableName) {
            this.tables.put(Objects.requireNonNull(id, "id"), Objects.requireNonNull(tableName, "tableName"));
            return this;
        }

        @Override
        public @NotNull DriverBasedDatabaseConfiguration build() {
            return new DriverBasedDatabaseConfigurationImpl(host, username, password, database, poolConfiguration, properties, tables);
        }
    }
}
