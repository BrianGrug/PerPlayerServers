package com.mizuledevelopment.node;

import com.mizuledevelopment.node.jedis.JedisManager;
import com.mizuledevelopment.node.threads.PingRunnable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class NodePlugin extends JavaPlugin {

    @Getter private static NodePlugin instance;
    private JedisManager jedisManager;

    private final String serverID = System.getenv("ID");

    public void onEnable() {
        NodePlugin.instance = this;

        this.jedisManager = new JedisManager(this.getConfig().getString("redis.host"), this.getConfig().getInt("redis.port"), "Testing-Master", this.getConfig().getString("password"));
        System.out.println("SERVER ID: " + this.serverID);

        new PingRunnable().runTaskTimer(this, 0L, 100L);
    }

}
