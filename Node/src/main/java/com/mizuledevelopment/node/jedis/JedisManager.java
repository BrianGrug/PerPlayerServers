package com.mizuledevelopment.node.jedis;

import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

@Getter
public class JedisManager {

    private final JedisPool jedisPool;
    private final String jedisChannel;

    private final JedisSubscriber jedisSubscriber = new JedisSubscriber();
    private final JedisPublisher jedisPublisher = new JedisPublisher();

    public JedisManager(String host, int port, String jedisChannel, String jedisPassword) {
        this.jedisPool = new JedisPool(host, port);
        this.jedisChannel = jedisChannel;

        if (jedisPassword != null)
            this.jedisPool.getResource().auth(jedisPassword);

        new Thread(() -> this.jedisPool.getResource().subscribe(this.jedisSubscriber, this.jedisChannel)).start();
    }

}
