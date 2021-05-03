package com.mizuledevelopment.master.manager;

import com.mizuledevelopment.master.rcon.RconClient;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
public class ServerModel {

    private String host;
    private String name;
    private UUID uuid;
    private int rconPort;
    private int serverPort;
    private String rconPassword;
    private RconClient rconClient;
    private long time;

}
