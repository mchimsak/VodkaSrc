package me.lc.vodka.module.impl.movement;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Glide extends Module {
        private Setting Mod;
        private   ArrayList<String> options = new ArrayList<>();
        public Glide() {
            super("Glide",0,Category.MOVEMENT);
            Vodka.INSTANCE.SETTING_MANAGER.addSetting(Mod = (new Setting( this,"MOD","Vanilla",options)));
            options.add(0,"Vanilla");
            options.add(1,"AAC1");
        }
    Runnable r2 = ()-> System.out.println("233333333");

    @EventTarget
    public void onMovement(EventUpdate e) {

            switch (Mod.getCurrentOptionIndex()) {
                case 0://Vanilla
                    double oldY = this.mc.thePlayer.motionY;
                    float oldJ = this.mc.thePlayer.jumpMovementFactor;
                    if (isToggled()) {
                        if ((this.mc.thePlayer.motionY < 0.0D) && (this.mc.thePlayer.isAirBorne) && (!this.mc.thePlayer.isInWater()) && (!this.mc.thePlayer.isOnLadder()) &&
                                (!this.mc.thePlayer.isInsideOfMaterial(net.minecraft.block.material.Material.lava))) {
                            this.mc.thePlayer.motionY = -0.125D;
                            this.mc.thePlayer.jumpMovementFactor *= 1.12337F;
                        }
                    }
                    else {
                        this.mc.thePlayer.motionY = oldY;
                        this.mc.thePlayer.jumpMovementFactor = oldJ;
                    }
                    break;
                case 1://AAC1
                    if (mc.thePlayer.fallDistance >= 1.8F) {
                        mc.thePlayer.motionY = 0;
                        mc.timer.timerSpeed = 0.2F;
                        mc.thePlayer.fallDistance = 0.0F;
                        setTimerSpeed(0.276F);
                    } else {
                        mc.timer.timerSpeed = 0.21F;
                    }
                    break;
            }

        }

    public static void setTimerSpeed(float timerSpeed) {
        try {
            Class<?> mcClass = mc.getClass();
            Field timerField = mcClass.getDeclaredField("timer"); // mc.timer
            timerField.setAccessible(true);
            try {
                Object timer = timerField.get(mcClass);
                Class<?> timerClass = timer.getClass();
                Field timerSpeedField = timerClass.getDeclaredField("timerSpeed"); // mc.timer.timerSpeed
                timerSpeedField.setAccessible(true);
                timerSpeedField.setFloat(timer, timerSpeed); // mc.timer.timerSpeed = timerSpeed
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
        public void onDisable() {
        mc.timer.timerSpeed = 1F;
        super.onDisable();
    }
}
