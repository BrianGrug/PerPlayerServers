package com.mizuledevelopment.master.commands;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.InternalServerErrorException;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.jedis.JedisPublisher;
import com.mizuledevelopment.master.manager.NodeManager;
import com.mizuledevelopment.master.objects.ServerModel;
import com.mizuledevelopment.master.rcon.RconClient;
import io.github.revxrsal.cub.annotation.Command;
import io.github.revxrsal.cub.annotation.Description;
import io.github.revxrsal.cub.annotation.Named;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.*;

public class CreateCommand {


    @Command("create")
    @Description("Create a server with Docker")
    @SneakyThrows
    public void createServer(@Named("Name") String name, @Named("Image") String type) {

        ServerModel serverModel = new ServerModel();

        Ports ports = new Ports();
        HashMap<Integer, Integer> portBindings = new HashMap<>();

        Arrays.asList(
                25565,
                13582
        ).forEach(port -> {
            try {
                int randomPort = ThreadLocalRandom.current().nextInt(49152, 65535 + 1);
                ports.bind(ExposedPort.tcp(port), Ports.Binding.bindPort(randomPort));
                portBindings.put(port, randomPort);
            }catch (InternalServerErrorException e) {
                int randomPort = ThreadLocalRandom.current().nextInt(49152, 65535 + 1);
                ports.bind(ExposedPort.tcp(port), Ports.Binding.bindPort(randomPort));
                portBindings.put(port, randomPort);
            }
        });

        serverModel.setServerPort(portBindings.get(25565));
        serverModel.setHost(MasterApplication.getConfig().getProperty("docker.ip"));
        serverModel.setName(name);
        serverModel.setRconPort(portBindings.get(13582));
        serverModel.setTime(System.currentTimeMillis());
        serverModel.setUuid(UUID.randomUUID());
        serverModel.setRconPassword(MasterApplication.getRconPassword());

        CreateContainerResponse container = MasterApplication.getDockerClient().createContainerCmd(MasterApplication.getConfig().getProperty("docker.user") +  "/" + type + ":latest")
                .withPortBindings(ports)
                .withName(serverModel.getUuid().toString())
                .withEnv("ID=" + name)
                .exec();

        serverModel.setContainerID(container.getId());

        MasterApplication.getDockerClient().startContainerCmd(container.getId()).exec();



        NodeManager.save(serverModel);

        NodeManager.getServerCache().put(name, serverModel);

        new JedisPublisher().publishData("CREATE///" + serverModel.getName() + "///" + serverModel.getServerPort());
    }
}
