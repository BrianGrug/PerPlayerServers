package com.mizuledevelopment.master.commands;

import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.manager.NodeManager;
import com.mizuledevelopment.master.objects.ServerModel;
import io.github.revxrsal.cub.annotation.Command;

import java.util.Arrays;

public class FindCommand {

    @Command("find")
    public void findServer(String name){
        ServerModel server = MasterApplication.getInstance().getNodeManager().getServer(name);

        Arrays.asList(
                "Server name: " + server.getName(),
                "Server port: " + server.getServerPort(),
                "Rcon port: " + server.getRconPort(),
                "Server UUID: " + server.getUuid(),
                "Last ping: " + server.getTime(),
                "Online? " + MasterApplication.getInstance().getNodeManager().getActiveServers().containsKey(server.getName())
        ).forEach(System.out::println);
    }

}
