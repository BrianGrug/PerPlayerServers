package com.mizuledevelopment.node.threads;

import com.mizuledevelopment.node.jedis.JedisPublisher;

public class PingRunnable implements Runnable {

    @Override
    public void run() {
        new JedisPublisher().publishData("PING///" + System.getenv("ID"));
    }
}
