package io.github.morapowered.mlib.database.sql.provider.hikari;

import com.zaxxer.hikari.HikariConfig;
import io.github.morapowered.mlib.database.configuration.DatabaseConfiguration;

public abstract class DriverBasedHikariConnectionFactory extends HikariConnectionFactory {

    protected DriverBasedHikariConnectionFactory(String poolName) {
        super(poolName);
    }

    protected abstract String driverClassName();

    protected abstract String driverJdbcIdentifier();

    @Override
    protected void configure(HikariConfig config, String address, String port, DatabaseConfiguration configuration) {
        config.setDriverClassName(driverClassName());
        config.setJdbcUrl(String.format("jdbc:%s://%s:%s/%s", driverJdbcIdentifier(), address, port, configuration.getDatabase()));
        config.setUsername(configuration.getUsername());
        config.setPassword(configuration.getPassword());
    }

}
