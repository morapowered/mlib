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

package io.github.morapowered.database.sql;

import com.zaxxer.hikari.HikariConfig;
import io.github.morapowered.database.sql.configuration.DriverBasedDatabaseConfiguration;

/*
 * Based on concepts and structure from the "LuckPerms" (https://github.com/LuckPerms/LuckPerms/)
 * (c) lucko (Luck) <luck@lucko.me> and contributors — licensed under the MIT License
 * Rewritten and adapted by Pedro Souza in 2025
 */
public abstract class DriverBasedHikariConnectionFactory extends HikariConnectionFactory {

    protected DriverBasedHikariConnectionFactory(String poolName) {
        super(poolName);
    }

    protected abstract String driverClassName();

    protected abstract String driverJdbcIdentifier();

    @Override
    protected void configure(HikariConfig config, String address, String port, DriverBasedDatabaseConfiguration configuration) {
        config.setDriverClassName(driverClassName());
        config.setJdbcUrl(String.format("jdbc:%s://%s:%s/%s", driverJdbcIdentifier(), address, port, configuration.getDatabase()));
        config.setUsername(configuration.getUsername());
        config.setPassword(configuration.getPassword());
    }

}
