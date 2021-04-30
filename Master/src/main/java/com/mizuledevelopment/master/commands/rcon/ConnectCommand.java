package com.mizuledevelopment.master.commands.rcon;

import com.mizuledevelopment.master.rcon.RconClient;
import io.github.revxrsal.cub.annotation.Command;
import io.github.revxrsal.cub.annotation.Description;
import io.github.revxrsal.cub.annotation.Flag;
import io.github.revxrsal.cub.annotation.Named;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;

public class ConnectCommand {

    @Command(value = "connect")
    @Description("Connect to other servers via Rcon")
    @SneakyThrows
    public void connect(@Named("host") String host, @Named("port") int port, @Flag("pass") String password, @Named("command") String command) {
        RconClient rconClient = RconClient.open(host, port, password);

        System.out.println(rconClient.sendCommand(command));
    }
}
