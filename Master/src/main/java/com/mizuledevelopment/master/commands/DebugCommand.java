package com.mizuledevelopment.master.commands;

import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.manager.NodeManager;
import io.github.revxrsal.cub.annotation.Command;

public class DebugCommand {

    @Command("debug")
    public void debug(){
        MasterApplication.getInstance().getNodeManager().getActiveServers().forEach((s, serverModel) -> System.out.println(s));
    }

}
