package com.mizuledevelopment.master.threads;

import com.mizuledevelopment.master.manager.NodeManager;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CleanupThread {

    public void cleanup() {
        Runnable heartbeat = () -> {
            NodeManager.getActiveServers().forEach((name, server) -> {
                Arrays.asList(
                        "Last ping: " + (System.currentTimeMillis() - server.getTime()),
                        "Server: " + server.getName()

                ).forEach(System.out::println);

                if ((System.currentTimeMillis() - server.getTime()) > 10000) {
                    NodeManager.getActiveServers().remove(name);
                    System.out.println("Removed " + server.getName());
                }
            });
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(heartbeat, 0, 3, TimeUnit.SECONDS);
    }
}
