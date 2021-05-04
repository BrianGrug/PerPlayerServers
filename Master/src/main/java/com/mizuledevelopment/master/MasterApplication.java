package com.mizuledevelopment.master;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.mizuledevelopment.master.commands.CreateCommand;
import com.mizuledevelopment.master.commands.FindCommand;
import com.mizuledevelopment.master.commands.HelpCommand;
import com.mizuledevelopment.master.commands.rcon.AddCommand;
import com.mizuledevelopment.master.commands.rcon.ConnectCommand;
import com.mizuledevelopment.master.jedis.JedisManager;
import com.mizuledevelopment.master.jedis.JedisPublisher;
import com.mizuledevelopment.master.manager.NodeManager;
import com.mizuledevelopment.master.mongo.Mongo;
import io.github.revxrsal.cub.cli.core.CLIHandler;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class MasterApplication {

    @Getter private static CLIHandler cliHandler;
    @Getter private static NodeManager nodeManager;
    @Getter private static JedisManager jedisManager;
    @Getter private static String rconPassword; //TODO remove
    @Getter private static DockerClient dockerClient;

    @SneakyThrows
    public static void main(String[] args) {
        rconPassword = "Testing123";

        DockerClientConfig clientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerHost("tcp://192.168.1.27:2375").build();
        dockerClient = DockerClientBuilder.getInstance(clientConfig).build();

        new Mongo().connectMongo();

        nodeManager = new NodeManager();

        jedisManager = new JedisManager("192.168.1.12", 6379, "Testing-Master", null);
        new JedisPublisher(jedisManager).publishData("PING");

        Scanner scanner = new Scanner(System.in);
        cliHandler = new CLIHandler(scanner, new PrintStream(System.out));


        Arrays.asList(
                new ConnectCommand(),
                new FindCommand(),
                new AddCommand(),
                new HelpCommand(),
                new CreateCommand()
        ).forEach(cliHandler::registerCommand);

        cliHandler.requestInput();
    }
}
