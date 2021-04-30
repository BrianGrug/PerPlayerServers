package com.mizuledevelopment.node.jedis;

import lombok.Getter;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.UUID;

public class JedisManager {

    @Getter private JedisPool jedisPool;
    @Getter private Jedis jedis;

    public JedisManager(String host, int port, String password) {
        jedisPool = new JedisPool(host, port);
        jedis = jedisPool.getResource();

        if (password != null) jedis.auth(password);

        new Thread(() -> jedis.subscribe(listen(), "Testing-Master")).start();
    }

    private JedisPubSub listen() {
        return new JedisPubSub() {
            public void onMessage(String channel, String message) {

                if(!channel.equals("Testing-Master")) return;

                String[] raw = message.split("/:");
                String type = raw[0];

                switch (type) {
                    case "STOP":
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
                        break;
                    case "PING":
                        System.out.println("Connected to Master server!");
                    default:
                        break;
                }
            }
        };
    }
}
