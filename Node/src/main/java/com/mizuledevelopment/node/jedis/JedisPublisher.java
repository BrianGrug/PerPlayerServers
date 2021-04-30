package com.mizuledevelopment.node.jedis;

import com.mizuledevelopment.node.NodePlugin;
import redis.clients.jedis.Jedis;

public class JedisPublisher {

    public static void sendMessage(String message) {
        Jedis jedis = NodePlugin.getJedisManager().getJedisPool().getResource();

        //TODO add auth but I need a fucking config
        jedis.publish("Testing-Master", message);
        jedis.close();
    }
}
