package com.mizuledevelopment.master.threads;

import com.mizuledevelopment.master.MasterApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CleanupThread {

    public void cleanup() {
        Runnable heartbeat = () -> MasterApplication.getInstance().getNodeManager().getActiveServers().forEach((name, server) -> {
            if ((System.currentTimeMillis() - server.getTime()) > 15000)
                MasterApplication.getInstance().getNodeManager().getActiveServers().remove(name);
        });

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(heartbeat, 0, 3, TimeUnit.SECONDS);
    }

}
