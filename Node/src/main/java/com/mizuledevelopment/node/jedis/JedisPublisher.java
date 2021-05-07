package com.mizuledevelopment.node.jedis;

import com.mizuledevelopment.node.NodePlugin;
import redis.clients.jedis.Jedis;

public class JedisPublisher {

    public void publishData(String message) {
        Jedis jedis = NodePlugin.getJedisManager().getJedisPool().getResource();

        if (NodePlugin.getJedisManager().getJedisPassword() != null) jedis.auth(NodePlugin.getJedisManager().getJedisPassword());

        jedis.publish(NodePlugin.getJedisManager().getJedisChannel(), message);
        jedis.close();
    }
}
