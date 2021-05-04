package com.mizuledevelopment.master.thread;

import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.jedis.JedisPublisher;
import com.mizuledevelopment.master.manager.NodeManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartbeatThread  {

    public void startHeartbeat() {
        Runnable heartbeat = () -> {
            NodeManager.getServerCache().forEach((name, server) -> {
                if ((System.currentTimeMillis() - server.getTime()) > 10000) NodeManager.getServerCache().remove(name);
                server.setTime(System.currentTimeMillis());
            });
            new JedisPublisher(MasterApplication.getJedisManager()).publishData("PING");
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(heartbeat, 0, 3, TimeUnit.SECONDS);
    }
}