package io.github.morapowered.mlib.redis;

import io.github.morapowered.mlib.redis.configuration.RedisConfiguration;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;

import java.io.Closeable;
import java.io.IOException;

public class RedisConnectionFactory implements Closeable {

    private final RedisConfiguration configuration;
    private RedisClient client;

    public RedisConnectionFactory(RedisConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setup() {
        RedisURI.Builder builder = RedisURI.builder();
        if (configuration.getSentinel().isEnabled()) {
            RedisConfiguration.SentinelConfiguration sentinelConfiguration = configuration.getSentinel();
            builder.withSentinelMasterId(sentinelConfiguration.getMaster());
            for (RedisConfiguration.SentinelConfiguration.SentinelNode node : configuration.getSentinel().getNodes()) {
                builder.withSentinel(node.getHost(), node.getPort(), node.getPassword());
            }
        } else {
            builder.withHost(configuration.getHost())
                    .withPort(configuration.getPort())
                    .withAuthentication(configuration.getUsername(), configuration.getPassword())
                    .withSsl(configuration.isUseSsl())
                    .withDatabase(configuration.getDatabase());
        }
        RedisURI uri = builder.build();
        client = RedisClient.create(uri);
    }

    @Override
    public void close() throws IOException {
        if (client != null) {
            client.close();
        }
    }
}
