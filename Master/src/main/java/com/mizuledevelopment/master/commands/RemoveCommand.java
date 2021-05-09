package com.mizuledevelopment.master.commands;

import com.mizuledevelopment.master.utils.DockerUtils;
import io.github.revxrsal.cub.annotation.Command;

public class RemoveCommand {


    @Command("remove")
    public void removeServer(String name) {
        DockerUtils.removeServer(name);
    }
}