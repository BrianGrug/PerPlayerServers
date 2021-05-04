package com.mizuledevelopment.master.commands;

import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.manager.NodeManager;
import io.github.revxrsal.cub.annotation.Command;

public class StartCommand {

    @Command("start")
    public void startServer(String name) {
        MasterApplication.getDockerClient().startContainerCmd(NodeManager.getServer(name).getContainerID()).exec();
    }
}
