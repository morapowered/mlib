package io.github.morapowered.mlib.database.sql.provider.hikari;

import io.github.morapowered.mlib.database.sql.StatementProcessor;

import java.util.HashMap;

public class PostgressConnectionFactory extends DriverBasedHikariConnectionFactory {

    protected PostgressConnectionFactory(String poolName) {
        super(poolName);
    }

    @Override
    protected String defaultPort() {
        return "5432";
    }

    @Override
    protected String driverClassName() {
        return "org.postgresql.Driver";
    }

    @Override
    protected String driverJdbcIdentifier() {
        return "postgresql";
    }

    @Override
    public String getImplementationName() {
        return "PostgreSQL";
    }

    @Override
    protected void overrideProperties(HashMap<String, String> properties) {
        super.overrideProperties(properties);

        // remove the default config properties which don't exist for PostgreSQL
        properties.remove("useUnicode");
        properties.remove("characterEncoding");
    }

    @Override
    public StatementProcessor getStatementProcessor() {
        return StatementProcessor.USE_DOUBLE_QUOTES;
    }
}
