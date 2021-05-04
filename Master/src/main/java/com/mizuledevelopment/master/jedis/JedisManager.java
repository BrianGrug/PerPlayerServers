package com.mizuledevelopment.master.jedis;

import com.mizuledevelopment.master.manager.NodeManager;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class JedisManager {

    @Getter
    private final JedisPool jedisPool;
    @Getter
    private final Jedis jedis;
    @Getter
    private final String jedisPassword;
    @Getter
    private final String jedisChannel;

    public JedisManager(String host, int port, String jedisChannel, String jedisPassword) {
        this.jedisChannel = jedisChannel;
        this.jedisPassword = jedisPassword;

        jedisPool = new JedisPool(host, port);

        jedis = jedisPool.getResource();

        if (jedisPassword != null) jedis.auth(jedisPassword);

        new Thread(() -> jedis.subscribe(startPubSub(), jedisChannel)).start();
    }

    private JedisPubSub startPubSub() {
        return new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {

                if(!channel.equals(jedisChannel)) return;

                String[] data = message.split("///");
                //TODO Fix jedis multiplying
                switch (data[0]) {
                    case "PING":
                        if(data.length == 1) return;

                        NodeManager.getServer(data[1]).setTime(System.currentTimeMillis());
                        break;
                    default:
                        break;
                }
            }
        };
    }
}