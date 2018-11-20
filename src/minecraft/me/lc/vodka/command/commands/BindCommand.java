package me.lc.vodka.command.commands;

import me.lc.vodka.utils.PlayerUtils;
import me.lc.vodka.Vodka;
import org.lwjgl.input.Keyboard;

import me.lc.vodka.command.Command;
import me.lc.vodka.module.Module;

public class BindCommand implements Command {

    @Override
    public boolean run(String[] args) {
        if (args.length == 3) {

            Module m = Vodka.INSTANCE.MODULE_MANAGER.getModule(args[1]);

            if (m == null)
                return false;

            m.setKeyCode(Keyboard.getKeyIndex(args[2].toUpperCase()));
            PlayerUtils.tellPlayer(m.getName() + " has been bound to " + args[2] + ".");
            return true;
        }
        return false;
    }

    @Override
    public String usage() {
        return "USAGE: ~bind [module] [key]";
    }

}
