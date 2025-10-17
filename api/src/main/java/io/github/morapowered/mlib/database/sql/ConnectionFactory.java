package io.github.morapowered.mlib.database.sql;

import io.github.morapowered.mlib.database.configuration.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {

    String getImplementationName();

    void setup(DatabaseConfiguration configuration);

    void shutdown();

    Connection getConnection() throws SQLException;

    StatementProcessor getStatementProcessor();

}
