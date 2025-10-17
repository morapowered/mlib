package io.github.morapowered.mlib.database.sql.provider.hikari;

import io.github.morapowered.mlib.database.sql.StatementProcessor;

import java.util.HashMap;

public class MariaDBConnectionFactory extends DriverBasedHikariConnectionFactory {

    protected MariaDBConnectionFactory(String poolName) {
        super(poolName);
    }

    @Override
    protected String defaultPort() {
        return "3306";
    }

    @Override
    protected String driverClassName() {
        return "org.mariadb.jdbc.Driver";
    }

    @Override
    protected String driverJdbcIdentifier() {
        return "mariadb";
    }

    @Override
    public String getImplementationName() {
        return "MariaDB";
    }

    @Override
    public StatementProcessor getStatementProcessor() {
        return StatementProcessor.USE_BACKTICKS;
    }

}
