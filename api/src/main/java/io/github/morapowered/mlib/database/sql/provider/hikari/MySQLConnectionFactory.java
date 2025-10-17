package io.github.morapowered.mlib.database.sql.provider.hikari;

import io.github.morapowered.mlib.database.sql.StatementProcessor;

import java.util.HashMap;

public class MySQLConnectionFactory extends DriverBasedHikariConnectionFactory {

    protected MySQLConnectionFactory(String poolName) {
        super(poolName);
    }

    @Override
    protected String defaultPort() {
        return "3306";
    }

    @Override
    protected String driverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    protected String driverJdbcIdentifier() {
        return "mysql";
    }

    @Override
    public String getImplementationName() {
        return "MySQL";
    }

    @Override
    public StatementProcessor getStatementProcessor() {
        return StatementProcessor.USE_BACKTICKS;
    }

    @Override
    protected void overrideProperties(HashMap<String, String> properties) {
        properties.putIfAbsent("cachePrepStmts", "true");
        properties.putIfAbsent("prepStmtCacheSize", "250");
        properties.putIfAbsent("prepStmtCacheSqlLimit", "2048");
        properties.putIfAbsent("useServerPrepStmts", "true");
        properties.putIfAbsent("useLocalSessionState", "true");
        properties.putIfAbsent("rewriteBatchedStatements", "true");
        properties.putIfAbsent("cacheResultSetMetadata", "true");
        properties.putIfAbsent("cacheServerConfiguration", "true");
        properties.putIfAbsent("elideSetAutoCommits", "true");
        properties.putIfAbsent("maintainTimeStats", "false");
        properties.putIfAbsent("alwaysSendSetIsolation", "false");
        properties.putIfAbsent("cacheCallableStmts", "true");

        super.overrideProperties(properties);
    }
}
