package com.mizuledevelopment.node;

import com.mizuledevelopment.node.commands.ChangeRconCommand;
import com.mizuledevelopment.node.commands.CreateServerCommand;
import com.mizuledevelopment.node.commands.RemoveServerCommand;
import com.mizuledevelopment.node.jedis.JedisManager;
import com.mizuledevelopment.node.threads.PingRunnable;
import io.github.revxrsal.cub.bukkit.BukkitCommandHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

public class NodePlugin extends JavaPlugin {

    @Getter
    private static NodePlugin plugin;
    @Getter
    private static JedisManager jedisManager;

    @Getter
    @Setter
    private static long lastPing;

    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        jedisManager = new JedisManager(getConfig().getString("redis.host"), getConfig().getInt("redis.port"),
                "Testing-Master", getConfig().getString("redis.password"));

        System.out.println("SERVER ID: " + System.getenv("ID"));

        BukkitCommandHandler handler = BukkitCommandHandler.create(this);

        handler.registerCommand(new ChangeRconCommand(), new CreateServerCommand(), new RemoveServerCommand());

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new PingRunnable(), 0L, 100L);
    }
}
