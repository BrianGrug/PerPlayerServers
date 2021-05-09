package com.mizuledevelopment.bungee;

import com.google.common.io.ByteStreams;
import com.mizuledevelopment.bungee.jedis.JedisManager;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

@Getter
public class BungeePlugin extends Plugin {

    @Getter private static BungeePlugin instance;
    private Configuration config;
    private JedisManager jedisManager;

    public void onEnable() {
        BungeePlugin.instance = this;

        try {
            this.copyResources();
        } catch (IOException ex) {
            this.getProxy().stop(ex.getMessage());
        }

        this.jedisManager = new JedisManager(this.config.getString("redis.address"), this.config.getInt("redis.port"), "Testing-Master", this.config.getString("redis.password"));
    }

    private void copyResources() throws IOException {
        File configFile = new File(this.getDataFolder(), "config.yml");

        if (!this.getDataFolder().exists())
            this.getDataFolder().mkdir();

        if (!configFile.exists()) {
            configFile.createNewFile();

            try (InputStream in = getResourceAsStream("config.yml");
                OutputStream out = new FileOutputStream(configFile)) {
                ByteStreams.copy(in, out);
            }
        }

        this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
    }

}
