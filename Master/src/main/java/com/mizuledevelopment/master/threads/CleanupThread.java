package com.mizuledevelopment.master.threads;

import com.mizuledevelopment.master.manager.NodeManager;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CleanupThread {

    public void cleanup() {
        Runnable heartbeat = () -> NodeManager.getActiveServers().forEach((name, server) -> {
            if ((System.currentTimeMillis() - server.getTime()) > 15000) {
                NodeManager.getActiveServers().remove(name);
            }
        });
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(heartbeat, 0, 3, TimeUnit.SECONDS);
    }
}
