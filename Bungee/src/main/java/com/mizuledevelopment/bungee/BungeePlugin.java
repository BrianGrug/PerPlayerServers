package com.mizuledevelopment.bungee;

import com.mizuledevelopment.bungee.jedis.BungeeJedisManager;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BungeePlugin extends Plugin {

    @Getter private static BungeePlugin instance; //Just to keep code style consistent
    @Getter private static Configuration config;
    @Getter private static BungeeJedisManager bungeeJedisManager;

    public void onEnable() {
        instance = this;

        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        bungeeJedisManager = new BungeeJedisManager(config.getString("redis.host"), config.getInt("redis.port"),
                "Testing-Master", config.getString("redis.password"));

    }
}
