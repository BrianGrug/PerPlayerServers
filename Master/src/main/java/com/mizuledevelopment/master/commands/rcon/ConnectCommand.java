package com.mizuledevelopment.master.commands.rcon;

import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.manager.NodeManager;
import com.mizuledevelopment.master.objects.ServerModel;
import com.mizuledevelopment.master.rcon.RconClient;
import io.github.revxrsal.cub.annotation.*;
import lombok.SneakyThrows;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConnectCommand {

    @Command(value = "connect", aliases = {"reconnect", "establish", "use"})
    @Description("Connect to other servers via Rcon")
    public void connect(@Named("host") String name) {
        ServerModel serverModel = MasterApplication.getInstance().getNodeManager().getServer(name);

        RconClient rconClient = RconClient.open(MasterApplication.getInstance().getConfig().getProperty("docker.ip"), serverModel.getRconPort(), MasterApplication.getInstance().getRconPassword());

        MasterApplication.getInstance().getNodeManager().save(serverModel);

        System.out.print("> ");

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            System.out.print("> ");
            if (input.equals(":q!")) break;

            String response = rconClient.sendCommand(input);
            System.out.println("< " + (response.isEmpty() ? "(empty response)" : response));
            System.out.print("> ");
        }
    }

}
