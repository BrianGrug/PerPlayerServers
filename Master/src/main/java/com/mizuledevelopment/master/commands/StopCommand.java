package com.mizuledevelopment.master.commands;

import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.shared.objects.ServerModel;
import com.mizuledevelopment.shared.rcon.RconClient;
import io.github.revxrsal.cub.annotation.Command;
import io.github.revxrsal.cub.annotation.Description;

public class StopCommand {

    @Command("stop")
    @Description("Stop a server")
    public void startServer(String name) {
        RconClient.open(MasterApplication.getInstance().getNodeManager().getServer(name)).sendCommand("stop");
    }

}
