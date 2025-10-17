package io.github.morapowered.mlib.database.sql.provider.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.morapowered.mlib.database.DatabaseType;
import io.github.morapowered.mlib.database.configuration.DatabaseConfiguration;
import io.github.morapowered.mlib.database.sql.ConnectionFactory;
import io.github.morapowered.mlib.util.AbstractBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class HikariConnectionFactory implements ConnectionFactory {


    private final String poolName;
    private HikariDataSource dataSource;

    protected HikariConnectionFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public void setup(DatabaseConfiguration configuration) {
        HikariConfig config = new HikariConfig();

        config.setPoolName(poolName);
        String[] addressSplit = configuration.getHost().split(":");
        String address = addressSplit[0];
        String port = addressSplit.length > 1 ? addressSplit[1] : defaultPort();

        configure(config, address, port, configuration);

        HashMap<String, String> properties = new HashMap<>(configuration.getProperties());
        overrideProperties(properties);
        properties.forEach(config::addDataSourceProperty);

        DatabaseConfiguration.PoolSettings poolSettings = configuration.getPoolSettings();
        config.setMaximumPoolSize(poolSettings.getMaximumPoolSize());
        config.setMinimumIdle(poolSettings.getMinimumIdle());
        config.setMaxLifetime(poolSettings.getMaximumLifetime());
        config.setKeepaliveTime(poolSettings.getKeepaliveTime());
        config.setConnectionTimeout(poolSettings.getConnectionTimeout());

        this.dataSource = new HikariDataSource(config);
    }

    @Override
    public void shutdown() {
        if (this.dataSource != null) {
            this.dataSource.close();
        }
    }

    protected abstract void configure(HikariConfig config, String address, String port, DatabaseConfiguration configuration);

    protected abstract String defaultPort();

    protected void overrideProperties(HashMap<String, String> properties) {
        // https://github.com/brettwooldridge/HikariCP/wiki/Rapid-Recovery
        properties.put("socketTimeout", String.valueOf(TimeUnit.SECONDS.toMillis(30)));
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (this.dataSource == null) {
            throw new SQLException("Unable to get a connection from the pool. (hikari is null)");
        }

        Connection connection = this.dataSource.getConnection();
        if (connection == null) {
            throw new SQLException("Unable to get a connection from the pool. (getConnection returned null)");
        }
        return connection;
    }

    public String getPoolName() {
        return poolName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements AbstractBuilder<ConnectionFactory> {

        private DatabaseType type;
        private String poolName;


        public Builder type(final @NotNull DatabaseType type) {
            this.type = Objects.requireNonNull(type, "type");
            return this;
        }


        @Contract("_ -> this")
        public Builder poolName(final @NotNull String poolName) {
            this.poolName = Objects.requireNonNull(poolName, "poolName");
            return this;
        }

        @Override
        public ConnectionFactory build() {
            Objects.requireNonNull(type, "type needs be set");
            return switch (type) {
                case MYSQL -> new MySQLConnectionFactory(poolName);
                case MARIADB -> new MariaDBConnectionFactory(poolName);
                case POSTEGRESQL -> new PostgressConnectionFactory(poolName);
                default -> throw new IllegalStateException("unsupported type: " + type);
            };
        }
    }
}
