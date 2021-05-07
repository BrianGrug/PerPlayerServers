package com.mizuledevelopment.master.commands;

import com.mizuledevelopment.master.manager.NodeManager;
import io.github.revxrsal.cub.annotation.Command;

public class DebugCommand {

    @Command("debug")
    public void debug(){
        NodeManager.getActiveServers().forEach((s, serverModel) -> System.out.println(s));
    }
}
