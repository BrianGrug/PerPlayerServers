package com.mizuledevelopment.shared;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.InternalServerErrorException;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.mizuledevelopment.shared.objects.ServerModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerServers {

    private final DockerClient dockerClient;

    public PlayerServers(DockerClientConfig dockerClientConfig) {
        this.dockerClient = DockerClientBuilder.getInstance(dockerClientConfig).build();
    }

    public void createServer(ServerModel serverModel, String image) {

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
        serverModel.setRconPort(portBindings.get(13582));
        serverModel.setTime(System.currentTimeMillis());
        serverModel.setUuid(UUID.randomUUID());

        CreateContainerResponse container = dockerClient.createContainerCmd(image)
                .withPortBindings(ports)
                .withName(serverModel.getUuid().toString())
                .withEnv("ID=" + serverModel.getName())
                .exec();

        serverModel.setContainerID(container.getId());

        dockerClient.startContainerCmd(container.getId()).exec();
    }
}
