package com.mizuledevelopment.master.jedis;

import com.mizuledevelopment.master.MasterApplication;
import redis.clients.jedis.Jedis;

public class JedisPublisher {

    public static void sendMessage(String message) {
        Jedis jedis = MasterApplication.getJedisManager().jedisPool.getResource();

        //TODO add auth but I need a fucking config
        jedis.publish("Testing-Master", message);
        jedis.close();
    }
}
