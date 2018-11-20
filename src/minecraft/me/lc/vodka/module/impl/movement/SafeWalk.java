package me.lc.vodka.module.impl.movement;


import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventSafeWalk;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;

public class SafeWalk
extends Module {
    public SafeWalk() {
        super("SafeWalk", 0,Category.MOVEMENT);
    }

    @EventTarget
    public void onSafeWalk(EventSafeWalk event) {
        event.setSafe(true);
    }
}

