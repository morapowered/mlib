package io.github.morapowered.mlib.redis.configuration;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;

@ConfigSerializable
public class RedisConfiguration {

    private String host = "localhost";
    private int port = 6379;
    private String username = "default";
    private String password = "";
    private int database = 0;
    private boolean useSsl = false;
    private SentinelConfiguration sentinel = new SentinelConfiguration();

    public RedisConfiguration() {
    }

    public RedisConfiguration(String host, int port, String username, String password, boolean useSsl, int database, SentinelConfiguration sentinel) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.useSsl = useSsl;
        this.database = database;
        this.sentinel = sentinel;
    }

    @ConfigSerializable
    public static class SentinelConfiguration {

        private boolean enabled = false;
        private String master = "";
        private List<SentinelNode> nodes = List.of(new SentinelNode("example", 6379, "me_password"));

        public SentinelConfiguration() {
        }

        public SentinelConfiguration(boolean enabled, String master, List<SentinelNode> nodes) {
            this.enabled = enabled;
            this.master = master;
            this.nodes = nodes;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public String getMaster() {
            return master;
        }

        public List<SentinelNode> getNodes() {
            return nodes;
        }

        @ConfigSerializable
        public static class SentinelNode {

            private String host;
            private int port;
            private String password;

            public SentinelNode() {
            }

            public SentinelNode(String host, int port, String password) {
                this.host = host;
                this.port = port;
                this.password = password;
            }

            public String getHost() {
                return host;
            }

            public int getPort() {
                return port;
            }

            public String getPassword() {
                return password;
            }
        }

    }


    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getDatabase() {
        return database;
    }

    public boolean isUseSsl() {
        return useSsl;
    }

    public SentinelConfiguration getSentinel() {
        return sentinel;
    }
}
