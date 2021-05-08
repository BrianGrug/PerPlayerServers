package com.mizuledevelopment.master.commands;

import com.github.dockerjava.api.command.RemoveContainerCmd;
import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.manager.NodeManager;
import com.mizuledevelopment.master.objects.ServerModel;
import com.mizuledevelopment.master.rcon.RconClient;
import io.github.revxrsal.cub.annotation.Command;

public class RemoveCommand {

    @Command("remove")
    public void removeServer(String name) {
        ServerModel server = MasterApplication.getInstance().getNodeManager().getServer(name);
        RemoveContainerCmd container = MasterApplication.getInstance().getDockerClient().removeContainerCmd(server.getContainerID());
        container.withRemoveVolumes(true).exec();

        MasterApplication.getInstance().getNodeManager().removeServer(server.getName());
    }

}
