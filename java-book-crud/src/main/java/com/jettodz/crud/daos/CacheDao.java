package com.jettodz.crud.daos;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import jakarta.annotation.PreDestroy;

@Service 
public class CacheDao {
    
    private final RedisClient client;
    private final StatefulRedisConnection<String, String> idempotencyConnection;

    public CacheDao(@Value("${lettuce.redis.host}") String redisHost, @Value("${lettuce.redis.port}") String redisPort) {
        this.client = RedisClient.create(RedisURI.create("redis://" + redisHost + ":" + redisPort));
        this.idempotencyConnection = client.connect();
    }

    /**
     * Sets a key-value pair in the cache if it does not already exist, with a 1-day expiration.
     * @param key - UUID to set in the cache
     * @param value - the value to associate with the key
     * @return true if the key was set, false if it already exists
     */
    public boolean setIdempotent(String key, String value) {
        var setArgs = SetArgs.Builder.nx().ex(Duration.ofDays(1));
        var result = idempotencyConnection.sync().set(key, value, setArgs);
        return "OK".equals(result);
    }

    public boolean updateIdempotent(String key, String value) {
        var setArgs = SetArgs.Builder.xx().keepttl();
        var result = idempotencyConnection.sync().set(key, value, setArgs);
        return "OK".equals(result);
    }

    public boolean deleteIdempotent(String key) {
        return idempotencyConnection.sync().del(key) > 0;
    }

    public String getIdempotent(String key) {
        var result = idempotencyConnection.sync().get(key);
        return result;
    }

    @PreDestroy 
    public void shutdown() {
        idempotencyConnection.close();
        client.shutdown();
    }

}
