package com.mizuledevelopment.master.commands;

import com.mizuledevelopment.master.MasterApplication;
import io.github.revxrsal.cub.annotation.Command;
import io.github.revxrsal.cub.annotation.Description;

import java.util.Arrays;

public class HelpCommand {

    @Command("help")
    @Description("Returns all commands")
    public void onHelp() {
        MasterApplication.getCliHandler().getCommands().forEach((s, handledCommand) -> System.out.println(handledCommand.getName() + ": " + handledCommand.getDescription()));
    }
}
