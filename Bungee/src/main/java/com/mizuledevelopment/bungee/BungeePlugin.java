package com.mizuledevelopment.bungee;

import com.mizuledevelopment.bungee.jedis.JedisManager;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class BungeePlugin extends Plugin {

    @Getter private static BungeePlugin instance;
    private Configuration config;
    private JedisManager jedisManager;

    public void onEnable() {
        BungeePlugin.instance = this;

        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            } catch (IOException ex) {
                this.getProxy().stop(ex.getMessage());
            }
        }

        this.jedisManager = new JedisManager(this.config.getString("redis.host"), this.config.getInt("redis.port"), "Testing-Master", this.config.getString("redis.password"));
    }

}
