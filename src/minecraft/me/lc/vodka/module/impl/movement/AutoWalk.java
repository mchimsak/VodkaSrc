package me.lc.vodka.module.impl.movement;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;

public class AutoWalk
extends Module {
    public AutoWalk() {
        super("AutoWalk", 0,Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.mc.gameSettings.keyBindForward.pressed = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.gameSettings.keyBindForward.pressed = false;
    }
}

