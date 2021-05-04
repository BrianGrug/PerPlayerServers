package com.mizuledevelopment.master.objects;

import com.mizuledevelopment.master.rcon.RconClient;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
public class ServerModel {

    private String host;
    private String name;
    private String containerID;
    private UUID uuid;
    private int rconPort;
    private int serverPort;
    private String rconPassword;
    private long time;

}
