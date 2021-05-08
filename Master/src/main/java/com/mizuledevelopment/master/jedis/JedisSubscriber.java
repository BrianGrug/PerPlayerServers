package com.mizuledevelopment.master.jedis;

import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.objects.ServerModel;
import redis.clients.jedis.JedisPubSub;

public class JedisSubscriber extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        if(!channel.equals(MasterApplication.getInstance().getJedisManager().getJedisChannel())) return;
        String[] data = message.split("///");

        //TODO Fix jedis multiplying
        switch (data[0]) {
            case "PING":
                if(data.length == 1) return;

                ServerModel server = MasterApplication.getInstance().getNodeManager().getServer(data[1]);
                server.setTime(System.currentTimeMillis());

                MasterApplication.getInstance().getNodeManager().cache(server);
                break;
            default:
                break;
        }
    }

}
