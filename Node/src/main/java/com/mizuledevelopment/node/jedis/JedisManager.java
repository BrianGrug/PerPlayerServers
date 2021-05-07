package com.mizuledevelopment.node.jedis;

import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class JedisManager {

    @Getter private final JedisPool jedisPool;
    @Getter private final Jedis jedis;
    @Getter private final String jedisPassword;

    @Getter private final String jedisChannel;

    public JedisManager(String host, int port, String jedisChannel, String jedisPassword) {
        this.jedisChannel = jedisChannel;
        this.jedisPassword = jedisPassword;

        this.jedisPool = new JedisPool(host, port);

        this.jedis = this.jedisPool.getResource();

        if (jedisPassword != null) {
            this.jedis.auth(jedisPassword);
        }

        new Thread(() -> this.jedis.subscribe(this.startPubSub(), jedisChannel)).start();
    }

    private JedisPubSub startPubSub() {
        return new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                if(!channel.equals(jedisChannel)) return;
                String[] data = message.split("///");

                switch (data[0]) {
                    case "STOP":
                        //TODO Add other cases, stop is just a placeholder
                        break;
                    default:
                        break;
                }
            }
        };
    }
}