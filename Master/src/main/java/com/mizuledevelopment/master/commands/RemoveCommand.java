package com.mizuledevelopment.master.commands;

import com.github.dockerjava.api.command.RemoveContainerCmd;
import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.manager.NodeManager;
import com.mizuledevelopment.master.rcon.RconClient;
import io.github.revxrsal.cub.annotation.Command;

public class RemoveCommand {


    @Command("remove")
    public void removeServer(String name) {
        RemoveContainerCmd container = MasterApplication.getDockerClient().removeContainerCmd(NodeManager.getServer(name).getContainerID());
        container.withRemoveVolumes(true).exec();

        NodeManager.removeServer(NodeManager.getServer(name));
    }
}