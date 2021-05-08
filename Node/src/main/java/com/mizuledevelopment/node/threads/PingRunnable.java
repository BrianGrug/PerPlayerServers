package com.mizuledevelopment.node.threads;

import com.mizuledevelopment.node.NodePlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PingRunnable extends BukkitRunnable {

    private final String pingMessage = "PING///" + NodePlugin.getInstance().getServerID();

    @Override
    public void run() {
        NodePlugin.getInstance().getJedisManager().getJedisPublisher().publishData(this.pingMessage);
    }

}
