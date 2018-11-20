package me.lc.vodka.module.impl.world;


import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;

public class Timer extends Module {
	public static Setting value;
	private static double timer =1D;
    /*    */   public Timer()
    /*    */   {
    	super("Timer", 0, Category.WORLD);
    		Vodka.INSTANCE.SETTING_MANAGER.addSetting(value= new Setting(this,"value",timer,1D,20D,false));
    }
    /*    */  	@EventTarget
    public void onUpdate(EventUpdate e) {
    	 if (isToggled()) {
		net.minecraft.util.Timer.timerSpeed = (float) Timer.value.getCurrentValue();
 }
    }
    Runnable r2 = ()-> System.out.println("233333333");

    public void onDisable() {
        super.onDisable();
		net.minecraft.util.Timer.timerSpeed =1F;
    }

}
