package me.lc.vodka.module.impl.movement;


import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;

import java.util.Arrays;

public class LongJump extends Module {
   private Setting sleepTicks;
   private Setting sleepNext;
   private Setting pressSpace;
   private long startJump = 0L;
   private boolean setted;

   public LongJump() {
      super("LongJump",0, Category.MOVEMENT);
      Vodka.INSTANCE.SETTING_MANAGER.addSetting(sleepTicks = (new Setting(this,"Ticks",50.0D,50.0D, 2000.0D, true)));
      Vodka.INSTANCE.SETTING_MANAGER.addSetting(sleepNext = (new Setting(this,"Sleep",false)));
      Vodka.INSTANCE.SETTING_MANAGER.addSetting(pressSpace = (new Setting(this,"Keypress",false)));


   }
   Runnable r2 = ()-> System.out.println("233333333");

   public void onDisable() {
      this.startJump = 0L;
      super.onDisable();
   }

   @EventTarget
   public void onUpdate(EventUpdate e) {
      if(this.mc.thePlayer.onGround && !this.setted) {
         this.startJump = System.currentTimeMillis();
         this.setted = true;
      }

      if((double)(System.currentTimeMillis() - this.startJump) < this.sleepTicks.getCurrentValue() && this.sleepNext.getBooleanValue() && this.mc.thePlayer.onGround) {
         this.mc.thePlayer.motionY = 0.0D;
         this.mc.thePlayer.motionX = 0.0D;
         this.mc.thePlayer.motionZ = 0.0D;
      } else {
         if(this.mc.gameSettings.keyBindJump.pressed || !this.pressSpace.getBooleanValue()) {
            if(this.mc.thePlayer.moveStrafing == 0.0F && this.mc.thePlayer.moveForward == 0.0F) {
               return;
            }

            if(this.mc.thePlayer.onGround) {
               this.mc.thePlayer.motionX = 0.0D;
               this.mc.thePlayer.motionZ = 0.0D;
               this.mc.thePlayer.jump();
               this.startJump = System.currentTimeMillis();
               this.setted = false;
            } else {
               long sinceStart = System.currentTimeMillis() - this.startJump;
               if(sinceStart > 150L) {
                  if(sinceStart < 200L) {
                     this.portMove(this.mc.thePlayer.rotationYaw, 0.2F, 0.0F);
                  }

                  if(sinceStart > 250L && sinceStart < 500L) {
                     this.move(this.mc.thePlayer.rotationYaw, 0.3F);
                  }

                  if(sinceStart > 500L && sinceStart < 1000L) {
                     this.move(this.mc.thePlayer.rotationYaw, 0.5F);
                  }
               }
            }
         }

      }
   }
   public void portMove(float yaw, float multiplyer, float up) {
      double moveX = -Math.sin(Math.toRadians((double)yaw)) * (double)multiplyer;
      double moveZ = Math.cos(Math.toRadians((double)yaw)) * (double)multiplyer;
      double moveY = (double)up;
      this.mc.thePlayer.setPosition(moveX + this.mc.thePlayer.posX, moveY + this.mc.thePlayer.posY, moveZ + this.mc.thePlayer.posZ);
   }

   public void move(float yaw, float multiplyer, float up) {
      double moveX = -Math.sin(Math.toRadians((double)yaw)) * (double)multiplyer;
      double moveZ = Math.cos(Math.toRadians((double)yaw)) * (double)multiplyer;
      this.mc.thePlayer.motionX = moveX;
      this.mc.thePlayer.motionY = (double)up;
      this.mc.thePlayer.motionZ = moveZ;
   }

   public void move(float yaw, float multiplyer) {
      double moveX = -Math.sin(Math.toRadians((double)yaw)) * (double)multiplyer;
      double moveZ = Math.cos(Math.toRadians((double)yaw)) * (double)multiplyer;
      this.mc.thePlayer.motionX = moveX;
      this.mc.thePlayer.motionZ = moveZ;
   }
}
