package io.github.morapowered.mlib.database.configuration;

import io.github.morapowered.mlib.database.DatabaseType;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@ConfigSerializable
public class DatabaseConfiguration {

    private DatabaseType type;
    private String host = "localhost:3306";
    private String username = "root";
    private String password = "";
    private String database = "mlib";
    private Map<String, String> properties = Map.ofEntries(Map.entry("useUnicode", "true"), Map.entry("characterEncoding", "utf8"));
    private PoolSettings poolSettings = new PoolSettings();
    private Map<String, String> tables = new HashMap<>();

    public DatabaseConfiguration() {
    }

    // To DriverBased constructor
    public DatabaseConfiguration(DatabaseType type, String host, String username, String password, String database, Map<String, String> properties, PoolSettings poolSettings, Map<String, String> tables) {
        this.type = type;
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
        this.properties = properties;
        this.poolSettings = poolSettings;
        this.tables = tables;
    }

    @ConfigSerializable
    public static class PoolSettings {
        private int maximumPoolSize = 10;
        private int minimumIdle = 10;
        private long maximumLifetime = 1800000;
        private long connectionTimeout = 5000;
        private long keepaliveTime = 0;

        public PoolSettings() {
        }

        public PoolSettings(int maximumPoolSize, int minimumIdle, long maximumLifetime, long connectionTimeout, long keepaliveTime) {
            this.maximumPoolSize = maximumPoolSize;
            this.minimumIdle = minimumIdle;
            this.maximumLifetime = maximumLifetime;
            this.connectionTimeout = connectionTimeout;
            this.keepaliveTime = keepaliveTime;
        }

        public int getMaximumPoolSize() {
            return maximumPoolSize;
        }

        public int getMinimumIdle() {
            return minimumIdle;
        }

        public long getMaximumLifetime() {
            return maximumLifetime;
        }

        public long getConnectionTimeout() {
            return connectionTimeout;
        }

        public long getKeepaliveTime() {
            return keepaliveTime;
        }
    }

    public DatabaseType getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public PoolSettings getPoolSettings() {
        return poolSettings;
    }

    public Map<String, String> getTables() {
        return tables;
    }

}
