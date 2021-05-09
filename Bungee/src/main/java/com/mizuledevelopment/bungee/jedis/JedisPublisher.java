package com.mizuledevelopment.bungee.jedis;

import com.mizuledevelopment.bungee.BungeePlugin;

public class JedisPublisher {

    public void publishData(String message) {
        BungeePlugin.getInstance().getJedisManager().getJedisPool().getResource().publish(BungeePlugin.getInstance().getJedisManager().getJedisChannel(), message);
    }

}
