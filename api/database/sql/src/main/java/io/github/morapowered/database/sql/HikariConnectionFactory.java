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

package io.github.morapowered.database.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.morapowered.database.sql.configuration.DatabaseConfiguration;
import io.github.morapowered.database.sql.configuration.DriverBasedDatabaseConfiguration;
import io.github.morapowered.database.sql.configuration.PoolConfiguration;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/*
 * Based on concepts and structure from the "LuckPerms" (https://github.com/LuckPerms/LuckPerms/)
 * (c) lucko (Luck) <luck@lucko.me> and contributors — licensed under the MIT License
 * Rewritten and adapted by Pedro Souza in 2025
 */
public abstract class HikariConnectionFactory implements ConnectionFactory {


    private final @Getter String poolName;
    private HikariDataSource dataSource;

    protected HikariConnectionFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public void setup(DatabaseConfiguration passedConfiguration) {
        if (!(passedConfiguration instanceof DriverBasedDatabaseConfiguration configuration)) {
            throw new IllegalStateException("Invalid configuration; driver based configuration is required.");
        }
        HikariConfig config = new HikariConfig();

        config.setPoolName(poolName);
        String[] addressSplit = configuration.getHost().split(":");
        String address = addressSplit[0];
        String port = addressSplit.length > 1 ? addressSplit[1] : defaultPort();

        configure(config, address, port, configuration);

        HashMap<String, String> properties = new HashMap<>(configuration.getProperties());
        overrideProperties(properties);
        properties.forEach(config::addDataSourceProperty);

        PoolConfiguration poolConfiguration = configuration.getPoolConfiguration();
        config.setMaximumPoolSize(poolConfiguration.getMaximumPoolSize());
        config.setMinimumIdle(poolConfiguration.getMinimumIdle());
        config.setMaxLifetime(poolConfiguration.getMaximumLifetime());
        config.setKeepaliveTime(poolConfiguration.getKeepAliveTime());
        config.setConnectionTimeout(poolConfiguration.getConnectionTimeout());

        this.dataSource = new HikariDataSource(config);
    }

    @Override
    public void shutdown() {
        if (this.dataSource != null) {
            this.dataSource.close();
        }
    }

    protected abstract void configure(HikariConfig config, String address, String port, DriverBasedDatabaseConfiguration configuration);

    protected abstract String defaultPort();

    protected void overrideProperties(HashMap<String, String> properties) {
        // https://github.com/brettwooldridge/HikariCP/wiki/Rapid-Recovery
        properties.put("socketTimeout", String.valueOf(TimeUnit.SECONDS.toMillis(30)));
    }

    @Override
    public @NotNull Connection getConnection() throws SQLException {
        if (this.dataSource == null) {
            throw new SQLException("Unable to get a connection from the pool. (hikari is null)");
        }

        Connection connection = this.dataSource.getConnection();
        if (connection == null) {
            throw new SQLException("Unable to get a connection from the pool. (getConnection returned null)");
        }
        return connection;
    }


}
