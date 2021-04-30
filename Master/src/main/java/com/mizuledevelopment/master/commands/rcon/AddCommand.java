package com.mizuledevelopment.master.commands.rcon;

import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.manager.NodeManager;
import com.mizuledevelopment.master.manager.ServerModel;
import com.mizuledevelopment.master.rcon.RconClient;
import io.github.revxrsal.cub.annotation.Command;
import io.github.revxrsal.cub.annotation.Flag;
import io.github.revxrsal.cub.annotation.Named;

import java.util.UUID;

public class AddCommand {


    @Command("add")
    public void connect(@Named("host") String host, @Named("Recon Port") @Flag("rp") int rconPort, @Named("Server Port") @Flag("sp") int serverPort, @Flag("name") String name, @Flag("pass") String password) {
        RconClient rconClient = RconClient.open(host, rconPort, password);

        ServerModel serverModel = new ServerModel(host, name, UUID.randomUUID(), rconPort, serverPort, password, rconClient, System.currentTimeMillis());

        if(name != null) MasterApplication.getNodeManager().getActiveServers().put(name, serverModel);
    }
}
