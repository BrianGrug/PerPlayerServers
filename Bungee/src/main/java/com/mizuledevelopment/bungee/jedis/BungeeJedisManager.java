package com.mizuledevelopment.bungee.jedis;

import com.mizuledevelopment.bungee.utils.BungeeUtils;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.net.InetSocketAddress;

public class BungeeJedisManager {

    @Getter
    private final JedisPool jedisPool;
    @Getter private final Jedis jedis;
    @Getter private final String jedisPassword;

    @Getter private final String jedisChannel;

    public BungeeJedisManager(String host, int port, String jedisChannel, String jedisPassword) {
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
                    case "ADD":
                        if(data.length < 4) return;
                        BungeeUtils.addServer(data[1], InetSocketAddress.createUnresolved(data[2], Integer.parseInt(data[3])), false);
                        break;
                    case "DELETE":
                        if(data.length < 4) return;
                        BungeeUtils.removeServer(data[1]);
                        break;
                    default:
                        break;
                }
            }
        };
    }
}
