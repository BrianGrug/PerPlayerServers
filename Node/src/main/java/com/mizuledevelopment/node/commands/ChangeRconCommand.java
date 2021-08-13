package com.mizuledevelopment.node.commands;

import com.mizuledevelopment.node.jedis.JedisPublisher;
import io.github.revxrsal.cub.annotation.Command;
import io.github.revxrsal.cub.annotation.Subcommand;
import io.github.revxrsal.cub.bukkit.annotation.CommandPermission;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

@Command("change")
public class ChangeRconCommand {


    @Subcommand("rcon")
    @SneakyThrows
    @CommandPermission("node.change.rcon")
    public void changeRcon(String password) {
        Properties config = new Properties();

        File serverProperties = new File("config.properties");

        config.load(new FileInputStream(serverProperties));

        FileOutputStream outputStream = new FileOutputStream(serverProperties.getPath());
        config.setProperty("rcon.password", password);
        config.store(outputStream, null);


        new JedisPublisher().publishData("CHANGE///" + System.getenv("ID") + "///RCON///" + password);
    }
}
