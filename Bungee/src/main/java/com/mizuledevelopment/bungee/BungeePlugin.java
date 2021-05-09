package com.mizuledevelopment.bungee;

import com.google.common.io.ByteStreams;
import com.mizuledevelopment.bungee.jedis.BungeeJedisManager;
import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.net.URL;

public class BungeePlugin extends Plugin {

    @Getter private static BungeePlugin instance; //Just to keep code style consistent
    @Getter private static Configuration config;
    @Getter private static BungeeJedisManager bungeeJedisManager;

    @SneakyThrows
    public void onEnable() {
        instance = this;

        if(!new File(getDataFolder(), "config.yml").exists()) {
            copyResources();
        }

        ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));

        bungeeJedisManager = new BungeeJedisManager(config.getString("redis.host"), config.getInt("redis.port"),
                "Testing-Master", config.getString("redis.password"));

    }
    @SneakyThrows
    private boolean copyResources() {

        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File config = new File(getDataFolder(), "config.yml");

        InputStream is = getResourceAsStream("config.yml");
        OutputStream os = new FileOutputStream(config);
        ByteStreams.copy(is, os);

        return true;
    }
}
