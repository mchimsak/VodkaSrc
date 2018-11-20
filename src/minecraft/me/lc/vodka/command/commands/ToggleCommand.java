package me.lc.vodka.command.commands;

import me.lc.vodka.Vodka;
import me.lc.vodka.command.Command;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventRender2D;
import me.lc.vodka.module.Module;
import me.lc.vodka.module.impl.render.hud.components.Notifiactions;
import me.lc.vodka.ui.notifications.Notification;
import me.lc.vodka.ui.notifications.NotificationManager;
import me.lc.vodka.ui.notifications.NotificationType;
import me.lc.vodka.utils.PlayerUtils;

public class ToggleCommand implements Command {
    Runnable r2 = ()-> System.out.println("233333333");
    @Override
    public boolean run(String[] args) {

        if (args.length == 2) {

            Module module = Vodka.INSTANCE.MODULE_MANAGER.getModule(args[1]);

            if (module == null) {
                PlayerUtils.tellPlayer("The module with the name " + args[1] + " does not exist.");
                return true;
            }

            module.toggle();

            if(module.isToggled()){
            	PlayerUtils.tellPlayer("§8"+args[1] + " §6onEnable §2✔");
                NotificationManager.show(new Notification(NotificationType.INFO,"INFO",args[1] + "onEnable ",1));
            }else {
            	PlayerUtils.tellPlayer("§8"+args[1] + " §6onDisable§4✘");
                NotificationManager.show(new Notification(NotificationType.INFO,"INFO",args[1] + " onDisable",1));
            }

            return true;
        }

        return false;
    }

    @Override
    public String usage() {
        return "USAGE: ~toggle [module]";
    }
}
