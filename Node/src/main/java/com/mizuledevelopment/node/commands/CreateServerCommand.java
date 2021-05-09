package com.mizuledevelopment.node.commands;

import com.mizuledevelopment.node.jedis.JedisPublisher;
import io.github.revxrsal.cub.annotation.Command;

public class CreateServerCommand {

    @Command("createserver")
    public void createServer(String name, String image) {
        new JedisPublisher().publishData("CREATE///" + name + "///" + image);
    }
}
