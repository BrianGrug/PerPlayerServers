package com.mizuledevelopment.master.jedis;

import com.amihaiemil.docker.Docker;
import com.amihaiemil.docker.Image;
import com.amihaiemil.docker.Images;
import com.amihaiemil.docker.UnixDocker;
import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.manager.ServerModel;
import com.mizuledevelopment.master.rcon.RconClient;
import lombok.SneakyThrows;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.io.File;
import java.util.UUID;

public class JedisManager {

    JedisPool jedisPool;
    Jedis jedis;

    public JedisManager(String host, int port, String password) {
        jedisPool = new JedisPool(host, port);
        jedis = jedisPool.getResource();

        if (password != null) jedis.auth(password);

        new Thread(() -> jedis.subscribe(listen(), "Testing-Master")).start();
    }

    private JedisPubSub listen() {
        return new JedisPubSub() {
            public void onMessage(String channel, String message) {
                String[] raw = message.split("/:");
                String type = raw[0];
                RconClient rconClient;

                switch (type) {
                    case "ADD":
                        rconClient = RconClient.open(raw[1], Integer.parseInt(raw[2]), raw[3]);
                        MasterApplication.getNodeManager().getActiveServers().put(raw[2],
                                new ServerModel(raw[1], raw[2], UUID.randomUUID(), Integer.parseInt(raw[3]), Integer.parseInt(raw[4]), raw[5], rconClient, System.currentTimeMillis()));
                        break;
                    case "CREATE":
                        rconClient = RconClient.open(raw[1], Integer.parseInt(raw[2]), raw[3]);
                        MasterApplication.getNodeManager().getActiveServers().put(raw[2],
                                new ServerModel(raw[1], raw[2], UUID.randomUUID(), Integer.parseInt(raw[3]), Integer.parseInt(raw[4]), raw[5], rconClient, System.currentTimeMillis()));

                        //TODO finish creation
                        break;
                    case "STOP":
                        MasterApplication.getNodeManager().getActiveServers().get(raw[1]).getRconClient().sendCommand("stop");
                        break;
                    default:
                        break;
                }
            }
        };
    }
}
