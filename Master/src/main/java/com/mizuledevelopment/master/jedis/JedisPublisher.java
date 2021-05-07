package com.mizuledevelopment.master.jedis;

import com.mizuledevelopment.master.MasterApplication;
import redis.clients.jedis.Jedis;

public class JedisPublisher {

    public void publishData(String message) {
        Jedis jedis = MasterApplication.getJedisManager().getJedisPool().getResource();

        if (MasterApplication.getJedisManager().getJedisPassword() != null) jedis.auth(MasterApplication.getJedisManager().getJedisPassword());

        jedis.publish(MasterApplication.getJedisManager().getJedisChannel(), message);
        jedis.close();
    }
}
