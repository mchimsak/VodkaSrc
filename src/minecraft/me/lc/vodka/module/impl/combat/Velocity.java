package me.lc.vodka.module.impl.combat;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventTick;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;

public class Velocity extends Module
{
    public Velocity() {
        super("Velocity", 0,Category.COMBAT);
    }
    Runnable r2 = ()-> System.out.println("233333333");
    
    @EventTarget
    public void onPacketTake(EventUpdate event) {
        if(this.isToggled()){
            if((mc.thePlayer.hurtTime == 1)||(mc.thePlayer.hurtTime ==2)||(mc.thePlayer.hurtTime ==3)||(mc.thePlayer.hurtTime ==4)||(mc.thePlayer.hurtTime ==5)||(mc.thePlayer.hurtTime ==6)||(mc.thePlayer.hurtTime ==7)||(mc.thePlayer.hurtTime ==8)){
                double yaw = mc.thePlayer.rotationYawHead;
                yaw = Math.toRadians(yaw);
                double dX = -Math.sin(yaw) * 0.05D;
                double dZ = -Math.cos(yaw) * 0.05D;
                mc.thePlayer.motionX = dX;
                mc.thePlayer.motionZ = dZ;
            }
        }
    }
}
