package com.mizuledevelopment.master;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.mizuledevelopment.master.commands.*;
import com.mizuledevelopment.master.commands.rcon.AddCommand;
import com.mizuledevelopment.master.commands.rcon.ConnectCommand;
import com.mizuledevelopment.master.jedis.JedisManager;
import com.mizuledevelopment.master.manager.NodeManager;
import com.mizuledevelopment.master.mongo.MongoManager;
import com.mizuledevelopment.master.threads.CleanupThread;
import io.github.revxrsal.cub.cli.core.CLIHandler;
import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

@Getter
public class MasterApplication {

    @Getter private static MasterApplication instance;

    private final CLIHandler cliHandler = new CLIHandler(new Scanner(System.in), new PrintStream(System.out));

    private final Properties config = new Properties();
    private final String rconPassword; //TODO remove

    private final DockerClient dockerClient;
    private final MongoManager mongoManager;
    private final JedisManager jedisManager;

    private final NodeManager nodeManager = new NodeManager();

    private MasterApplication() {
        if (!new File("config.properties").exists()) {
            System.out.println("Config not found. Generating new one.");
            this.copyResource();
        }

        try {
            this.config.load(new FileInputStream("config.properties"));
        } catch (IOException ex) {
            this.mongoManager = null;
            this.jedisManager = null;
            this.rconPassword = null;
            this.dockerClient = null;

            System.out.println("Error in loading the config, please delete the old to generate new one.");
            ex.printStackTrace();
            return;
        }

        this.rconPassword = this.config.getProperty("rcon.password");

        DockerClientConfig clientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerHost(this.config.getProperty("docker.host")).build();
        this.dockerClient = DockerClientBuilder.getInstance(clientConfig).build();

        this.mongoManager = new MongoManager(this.config.getProperty("mongo.host"), Integer.parseInt(this.config.getProperty("mongo.port")));
        this.jedisManager = new JedisManager(this.config.getProperty("redis.host"), Integer.parseInt(this.config.getProperty("redis.port")), "Testing-Master", System.getProperty("redis.password"));

        Arrays.asList(
                new ConnectCommand(),
                new FindCommand(),
                new AddCommand(),
                new HelpCommand(),
                new CreateCommand(),
                new StartCommand(),
                new StopCommand(),
                new DebugCommand(),
                new RemoveCommand()
        ).forEach(this.cliHandler::registerCommand);

        new CleanupThread().cleanup();

        this.cliHandler.requestInput();
    }

    public void copyResource() {
        try {
            URL inputUrl = ClassLoader.getSystemResource("config.properties");
            File dest = new File("config.properties");
            FileUtils.copyURLToFile(inputUrl, dest);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MasterApplication.instance = new MasterApplication();
    }

}
