package com.mizuledevelopment.master.commands.rcon;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.master.manager.ServerModel;
import com.mizuledevelopment.master.rcon.RconClient;
import io.github.revxrsal.cub.annotation.Command;
import io.github.revxrsal.cub.annotation.Description;
import io.github.revxrsal.cub.annotation.Flag;
import io.github.revxrsal.cub.annotation.Named;
import lombok.SneakyThrows;
import org.apache.commons.lang.RandomStringUtils;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AddCommand {


    @Command("add")
    @Description("Add a server to the Rcon list")
    @SneakyThrows
    public void connect(@Named("host") String host, @Named("Recon Port") @Flag("rp") int rconPort, @Named("Server Port") @Flag("sp") int serverPort, @Flag("name") String name, @Flag("pass") String rconPassword) {
        RconClient rconClient = RconClient.open(host, rconPort, rconPassword);

        ServerModel serverModel = new ServerModel();

        serverModel.setServerPort(serverPort);
        serverModel.setHost(host);
        serverModel.setName(name);
        serverModel.setRconClient(rconClient);
        serverModel.setRconPort(rconPort);
        serverModel.setTime(System.currentTimeMillis());
        serverModel.setUuid(UUID.randomUUID());
        serverModel.setRconPassword(rconPassword);

/*        final Container container = new TcpDocker(URI.create("http://192.168.1.27:2375")).images().pull("daddyimpregnant/testing", "latest").run();

        container.docker().info().forEach((test, tes) -> System.out.println(tes));
        System.out.println(container.docker().info().get(""));*/

        //if(name != null) MasterApplication.getNodeManager().getActiveServers().put(name, serverModel);
    }
}
