package com.mizuledevelopment.master.objects;

import lombok.Data;

import java.util.UUID;

@Data
public class ServerModel {

    private String host;
    private String name;
    private String containerID;
    private UUID uuid = UUID.randomUUID();
    private int rconPort;
    private int serverPort;
    private String rconPassword;
    private long time;

}
