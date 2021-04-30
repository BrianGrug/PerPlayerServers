package com.mizuledevelopment.master.manager;

import com.mizuledevelopment.master.rcon.RconClient;
import lombok.Getter;

import java.util.HashMap;

public class NodeManager {

    @Getter private HashMap<String, ServerModel> activeServers;

    public NodeManager() {
        activeServers = new HashMap<>();
    }
}
