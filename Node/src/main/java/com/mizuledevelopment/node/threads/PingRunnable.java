package com.mizuledevelopment.node.threads;

import com.mizuledevelopment.node.NodePlugin;

public class PingRunnable implements Runnable {

    private final String pingMessage = "PING///" + NodePlugin.getInstance().getServerID();

    @Override
    public void run() {
        NodePlugin.getInstance().getJedisManager().getJedisPublisher().publishData(this.pingMessage);
    }

}
