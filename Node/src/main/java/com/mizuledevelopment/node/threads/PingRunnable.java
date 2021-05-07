package com.mizuledevelopment.node.threads;

import com.mizuledevelopment.node.jedis.JedisPublisher;
import org.bukkit.scheduler.BukkitRunnable;

public class PingRunnable extends BukkitRunnable {

    @Override
    public void run() {
        new JedisPublisher().publishData("PING///" + System.getenv("ID"));
    }
}
