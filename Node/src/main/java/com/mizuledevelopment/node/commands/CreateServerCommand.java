package com.mizuledevelopment.node.commands;

import com.mizuledevelopment.node.jedis.JedisPublisher;
import io.github.revxrsal.cub.annotation.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CreateServerCommand {

    @Command("createserver")
    public void createServer(Player player, String name, String image) {
        new JedisPublisher().publishData("CREATE///" + name + "///" + image);

        player.sendMessage(ChatColor.GREEN + "Creating " + name + " with image " + image);
    }
}
