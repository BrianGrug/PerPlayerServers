package com.mizuledevelopment.master.commands;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.InternalServerErrorException;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.manager.NodeManager;
import com.mizuledevelopment.master.manager.ServerModel;
import com.mizuledevelopment.master.rcon.RconClient;
import io.github.revxrsal.cub.annotation.Command;
import io.github.revxrsal.cub.annotation.Description;
import lombok.SneakyThrows;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CreateCommand {


    @Command("create")
    @Description("Create a server with Docker")
    @SneakyThrows
    public void createServer(String name) {

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

        CreateContainerResponse container = MasterApplication.getDockerClient().createContainerCmd("daddyimpregnant/testing:latest")
                .withPortBindings(ports)
                .withName(name)
                .exec();

        MasterApplication.getDockerClient().startContainerCmd(container.getId()).exec();

        serverModel.setServerPort(portBindings.get(25565));
        serverModel.setHost("192.168.1.27");
        serverModel.setContainerID(container.getId());
        serverModel.setName(name);
        serverModel.setRconClient(null);
        serverModel.setRconPort(portBindings.get(13582));
        serverModel.setTime(System.currentTimeMillis());
        serverModel.setUuid(UUID.randomUUID());
        serverModel.setRconPassword(MasterApplication.getRconPassword());

        NodeManager.save(serverModel);

        NodeManager.getServerCache().put(name, serverModel);
    }
}
