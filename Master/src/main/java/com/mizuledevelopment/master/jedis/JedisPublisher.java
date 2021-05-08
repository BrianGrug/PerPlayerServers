package com.mizuledevelopment.master.jedis;

import com.mizuledevelopment.master.MasterApplication;

public class JedisPublisher {

    public void publishData(String message) {
        MasterApplication.getInstance().getJedisManager().getJedisPool().getResource().publish(MasterApplication.getInstance().getJedisManager().getJedisChannel(), message);
    }

}
