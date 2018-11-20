package me.lc.vodka.module.impl.render;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;

public class NoHurtTime extends Module {
   Runnable r2 = ()-> System.out.println("233333333");

   public NoHurtTime() {
      super("NoHurtTime", 0,Category.RENDER);
   }
}
