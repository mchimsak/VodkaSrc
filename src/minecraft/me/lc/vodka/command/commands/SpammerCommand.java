package me.lc.vodka.command.commands;

import me.lc.vodka.Vodka;
import me.lc.vodka.command.Command;
import me.lc.vodka.module.Module;
import me.lc.vodka.module.impl.other.Spammer;
import me.lc.vodka.utils.PlayerUtils;

public class SpammerCommand implements Command {
    Runnable r2 = ()-> System.out.println("233333333");
    @Override
    public boolean run(String[] args) {

        if (args.length == 2) {

            Spammer.M.setCurrentOption(args[1]);

            return true;
        }


        return false;
    }

    @Override
    public String usage() {
        return "USAGE: ~spammer [text]";
    }
}
