package com.mizuledevelopment.bungee.jedis;

import com.mizuledevelopment.bungee.BungeePlugin;
import com.mizuledevelopment.bungee.utils.BungeeUtils;
import redis.clients.jedis.JedisPubSub;

import java.net.InetSocketAddress;

public class JedisSubscriber extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals(BungeePlugin.getInstance().getJedisManager().getJedisChannel())) return;
        String[] data = message.split("///");

        switch (data[0]) {
            case "ADD":
                if (data.length < 4) return;
                BungeeUtils.addServer(data[1], InetSocketAddress.createUnresolved(data[2], Integer.parseInt(data[3])), false);
                break;
            case "DELETE":
                if (data.length < 4) return;
                BungeeUtils.removeServer(data[1]);
                break;
            default:
                break;
        }
    }

}
