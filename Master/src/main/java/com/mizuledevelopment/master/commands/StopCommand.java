package com.mizuledevelopment.master.commands;

import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.manager.NodeManager;
import io.github.revxrsal.cub.annotation.Command;
import io.github.revxrsal.cub.annotation.Description;
import io.github.revxrsal.cub.annotation.Flag;
import io.github.revxrsal.cub.annotation.Optional;

public class StopCommand {

    @Command("stop")
    @Description("Stop a server")
    public void startServer(String name, @Optional @Flag("force") boolean force) {
        if (force) {
            MasterApplication.getDockerClient().stopContainerCmd(NodeManager.getServer(name).getContainerID()).exec();
        } else {
            NodeManager.getServer(name).getRconClient().sendCommand("stop");
        }
    }
}