package com.mizuledevelopment.master.jedis;

import redis.clients.jedis.Jedis;

public class JedisPublisher {

    private Jedis jedis;
    private final JedisManager jedisManager;

    public JedisPublisher(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }

    public void publishData(String message) {
        try {
            this.jedis = jedisManager.getJedisPool().getResource();

            if(jedisManager.getJedisPassword() != null) jedis.auth(jedisManager.getJedisPassword());

            jedis.publish(jedisManager.getJedisChannel(), message);
        } finally {
            this.jedis.close();
        }
    }
}
