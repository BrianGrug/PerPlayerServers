package com.mizuledevelopment.node.jedis;

import com.mizuledevelopment.node.NodePlugin;
import redis.clients.jedis.Jedis;

public class JedisPublisher {

    public void publishData(String message) {
        NodePlugin.getInstance().getJedisManager().getJedisPool().getResource().publish(NodePlugin.getInstance().getJedisManager().getJedisChannel(), message);
    }

}
