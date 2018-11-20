package me.lc.vodka.module.impl.movement;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;

public class Sprint extends Module {

        public Sprint() {
                super("Sprint",0,Category.MOVEMENT);
        }
        Runnable r2 = ()-> System.out.println("233333333");

        @EventTarget
        public void onUpdate(EventUpdate e) {
                if(isToggled()) {
                        this.mc.thePlayer.setSprinting(true);

                }else {
                        this.mc.thePlayer.setSprinting(false);
                }

        }
}
