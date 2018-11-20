package me.lc.vodka.module.impl.combat;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;

public class Reach extends Module {
	public Reach(){
		super("Reach", 0,Category.COMBAT);
	}
	Runnable r2 = ()-> System.out.println("233333333");
	public static boolean Reach;

    @EventTarget
    public void onUpdate(EventUpdate event){
    	Reach = true;
    }
}
