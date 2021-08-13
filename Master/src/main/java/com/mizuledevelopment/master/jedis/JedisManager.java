package com.mizuledevelopment.master.jedis;

import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.manager.NodeManager;
import com.mizuledevelopment.master.objects.ServerModel;
import com.mizuledevelopment.master.utils.DockerUtils;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class JedisManager {

    @Getter
    private final JedisPool jedisPool;
    @Getter
    private final Jedis jedis;
    @Getter
    private final String jedisPassword;
    @Getter
    private final String jedisChannel;

    public JedisManager(String host, int port, String jedisChannel, String jedisPassword) {
        this.jedisChannel = jedisChannel;
        this.jedisPassword = jedisPassword;

        jedisPool = new JedisPool(host, port);

        jedis = jedisPool.getResource();

        if (jedisPassword != null) jedis.auth(jedisPassword);

        new Thread(() -> jedis.subscribe(startPubSub(), jedisChannel)).start();
    }

    private JedisPubSub startPubSub() {
        return new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {

                if(!channel.equals(jedisChannel)) return;

                String[] data = message.split("///");
                switch (data[0]) {
                    case "PING":
                        if(data.length == 1) return;

                        ServerModel server = NodeManager.getServer(data[1]);
                        server.setTime(System.currentTimeMillis());

                        NodeManager.cache(server);
                        break;
                    case "CHANGE":
                        ServerModel serverModel = NodeManager.getServer(data[1]);
                        if(data[2].equals("RCON")) serverModel.setRconPassword(data[3]);
                    case "CREATE":
                        DockerUtils.createServer(data[1], data[2]);
                        break;
                    case "REMOVE":
                        DockerUtils.removeServer(data[1]);
                        break;
                    default:
                        break;
                }
            }
        };
    }
}