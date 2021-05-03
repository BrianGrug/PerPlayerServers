package com.mizuledevelopment.master.commands.rcon;

import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.rcon.RconClient;
import io.github.revxrsal.cub.annotation.Command;
import io.github.revxrsal.cub.annotation.Description;

public class ReconnectCommand {


    @Command("reconnect")
    @Description("Reconnect to Rcon servers")
    public void onReconnect(String name, String command) {
        System.out.println(MasterApplication.getNodeManager().getActiveServers().get(name).getRconClient().sendCommand(command));
    }
}
