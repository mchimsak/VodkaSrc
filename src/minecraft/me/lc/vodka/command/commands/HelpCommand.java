package me.lc.vodka.command.commands;


import me.lc.vodka.Vodka;
import me.lc.vodka.command.Command;
import me.lc.vodka.utils.PlayerUtils;

public class HelpCommand implements Command {
    Runnable r2 = ()-> System.out.println("233333333");
    @Override
    public boolean run(String[] args) {
        PlayerUtils.tellPlayer("Here are the list of commands:");
        for (Command c : Vodka.INSTANCE.COMMAND_MANAGER.getCommands().values()) {
            PlayerUtils.tellPlayer(c.usage());
        }
        return true;
    }

    @Override
    public String usage() {
        return "USAGE: ~help";
    }

}
