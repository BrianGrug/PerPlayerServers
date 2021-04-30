package com.mizuledevelopment.master;

import com.mizuledevelopment.master.commands.HelpCommand;
import com.mizuledevelopment.master.commands.rcon.AddCommand;
import com.mizuledevelopment.master.commands.rcon.ConnectCommand;
import com.mizuledevelopment.master.commands.rcon.ReconnectCommand;
import com.mizuledevelopment.master.jedis.JedisManager;
import com.mizuledevelopment.master.jedis.JedisPublisher;
import com.mizuledevelopment.master.manager.NodeManager;
import io.github.revxrsal.cub.cli.core.CLIHandler;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.PrintStream;
import java.util.Scanner;

public class MasterApplication {

    @Getter private static CLIHandler cliHandler;
    @Getter private static NodeManager nodeManager;
    @Getter private static JedisManager jedisManager;
    @SneakyThrows
    public static void main(String[] args) {

        nodeManager = new NodeManager();

        jedisManager = new JedisManager("192.168.1.12", 6379, null);
        JedisPublisher.sendMessage("Ping!");

        Scanner scanner = new Scanner(System.in);
        cliHandler = new CLIHandler(scanner, new PrintStream(System.out));

        cliHandler.registerCommand(new ConnectCommand());
        cliHandler.registerCommand(new ReconnectCommand());
        cliHandler.registerCommand(new AddCommand());
        cliHandler.registerCommand(new HelpCommand());

        cliHandler.requestInput();
    }
}
