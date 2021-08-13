package com.mizuledevelopment.node.commands;

import com.mizuledevelopment.node.jedis.JedisPublisher;
import io.github.revxrsal.cub.annotation.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RemoveServerCommand {

    @Command("removeserver")
    public void removeServer(Player player, String server) {
        new JedisPublisher().publishData("REMOVE///" + server);

        player.sendMessage(ChatColor.GREEN + "Removing " + server + ".. please wait");
    }

}
