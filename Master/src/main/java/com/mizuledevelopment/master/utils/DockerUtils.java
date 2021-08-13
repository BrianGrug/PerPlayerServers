package com.mizuledevelopment.master.utils;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.RemoveContainerCmd;
import com.github.dockerjava.api.exception.InternalServerErrorException;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.jedis.JedisPublisher;
import com.mizuledevelopment.master.manager.NodeManager;
import com.mizuledevelopment.master.objects.ServerModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DockerUtils {

    public static void createServer(String name, String image) {
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

        CreateContainerResponse container = MasterApplication.getDockerClient().createContainerCmd(MasterApplication.getConfig().getProperty("docker.user") +  "/" + image + ":latest")
                .withPortBindings(ports)
                .withName(serverModel.getUuid().toString())
                .withEnv("ID=" + name)
                .exec();

        serverModel.setContainerID(container.getId());

        MasterApplication.getDockerClient().startContainerCmd(container.getId()).exec();



        NodeManager.save(serverModel);

        NodeManager.getServerCache().put(name, serverModel);

        new JedisPublisher().publishData("ADD///" + serverModel.getName() + "///" + serverModel.getServerPort());
    }
    public static void removeServer(String name) {
        RemoveContainerCmd container = MasterApplication.getDockerClient().removeContainerCmd(NodeManager.getServer(name).getContainerID());
        container.withRemoveVolumes(true).exec();

        NodeManager.removeServer(NodeManager.getServer(name));
    }
}
