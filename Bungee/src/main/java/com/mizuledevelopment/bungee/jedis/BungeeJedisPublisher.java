package com.mizuledevelopment.bungee.jedis;

import com.mizuledevelopment.bungee.BungeePlugin;
import redis.clients.jedis.Jedis;

public class BungeeJedisPublisher {

    public void publishData(String message) {
        Jedis jedis = BungeePlugin.getBungeeJedisManager().getJedisPool().getResource();

        if (BungeePlugin.getBungeeJedisManager().getJedisPassword() != null) {
            jedis.auth(BungeePlugin.getBungeeJedisManager().getJedisPassword());
        }

        jedis.publish(BungeePlugin.getBungeeJedisManager().getJedisChannel(), message);
        jedis.close();
    }
}
