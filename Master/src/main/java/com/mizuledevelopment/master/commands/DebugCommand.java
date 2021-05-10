package com.mizuledevelopment.master.commands;

import com.mizuledevelopment.master.MasterApplication;
import io.github.revxrsal.cub.annotation.Command;

public class DebugCommand {

    @Command("debug")
    public void debug(){
        MasterApplication.getInstance().getNodeManager().getActiveServers().forEach((s, serverModel) -> System.out.println(s));
    }

}
