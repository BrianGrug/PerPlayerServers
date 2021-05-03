package com.mizuledevelopment.master.commands.rcon;

import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.manager.ServerModel;
import com.mizuledevelopment.master.rcon.RconClient;
import io.github.revxrsal.cub.annotation.*;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConnectCommand {

    @Command(value = "connect")
    @Description("Connect to other servers via Rcon")
    @SneakyThrows
    public void connect(@Named("host") String name, @Optional String command, @Optional @Flag("t") boolean interactive) {

        ServerModel serverModel = MasterApplication.getNodeManager().getActiveServers().get(name);

        RconClient rconClient = RconClient.open("192.168.1.27", serverModel.getRconPort(), MasterApplication.getRconPassword());

        Scanner scanner = new Scanner(System.in);

        if (interactive) {
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();

                System.out.print("> ");

                if (input.equals(":q!")) break;

                String response = rconClient.sendCommand(input);

                System.out.println("< " + (response.isEmpty() ? "(empty response)" : response));
                System.out.print("> ");
            }
        }else {
            String response = rconClient.sendCommand(command);

            System.out.println("< " + (response.isEmpty() ? "(empty response)" : response));
        }
    }
}
