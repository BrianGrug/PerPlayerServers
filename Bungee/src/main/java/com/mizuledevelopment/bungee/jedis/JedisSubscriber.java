package com.mizuledevelopment.bungee.jedis;

import com.mizuledevelopment.bungee.BungeePlugin;
import redis.clients.jedis.JedisPubSub;

public class JedisSubscriber extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals(BungeePlugin.getInstance().getJedisManager().getJedisChannel())) return;
        String[] data = message.split("///");

        switch (data[0]) {
            case "STOP":
                //TODO Add other cases, stop is just a placeholder
                break;
            default:
                break;
        }
    }

}
