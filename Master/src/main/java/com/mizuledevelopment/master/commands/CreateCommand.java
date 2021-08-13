package com.mizuledevelopment.master.commands;

import com.mizuledevelopment.master.utils.DockerUtils;
import io.github.revxrsal.cub.annotation.Command;
import io.github.revxrsal.cub.annotation.Description;
import io.github.revxrsal.cub.annotation.Named;
import lombok.SneakyThrows;

public class CreateCommand {


    @Command("create")
    @Description("Create a server with Docker")
    @SneakyThrows
    public void createServer(@Named("Name") String name, @Named("Image") String type) {
        DockerUtils.createServer(name, type);
    }
}
