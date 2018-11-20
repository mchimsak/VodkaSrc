package me.lc.vodka.module.impl.movement;


import java.util.ArrayList;
import java.util.Arrays;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class Jesus extends Module {
   private Setting Mod;
   private long lastSwim = 0L;

   public static ArrayList<String> options = new ArrayList<>();
   public Jesus() {
      super("Jesus",0, Category.MOVEMENT);
      Vodka.INSTANCE.SETTING_MANAGER.addSetting(Mod = (new Setting(this,"MOD","Vanilla",options)));
      options.add(0,"Vanilla");
      options.add(1,"NCP");
      options.add(2,"AAC");
   }
   Runnable r2 = ()-> System.out.println("233333333");

   @EventTarget
   public void o(EventUpdate e) {
         BlockPos under;
         switch (Mod.getCurrentOptionIndex()) {

            case 0://Vanilla
               if(this.mc.thePlayer.isInWater()) {
                  this.mc.thePlayer.motionY = 0.0D;
                  this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, (double)((int)this.mc.thePlayer.posY + 1), this.mc.thePlayer.posZ);
                  return;
               }

               under = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.prevPosY - 0.4000000059604645D, this.mc.thePlayer.posZ);
               boolean water2 = this.mc.theWorld.getBlockState(under).getBlock() == Blocks.water;
               if(water2) {
                  this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, (double)((int)this.mc.thePlayer.posY), this.mc.thePlayer.posZ);
                  this.mc.thePlayer.onGround = true;
                  this.mc.thePlayer.motionY = 0.0D;
               }
               break;
            case 1://NCP
               under = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.prevPosY, this.mc.thePlayer.posZ);
               BlockPos water = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.prevPosY + 0.6D, this.mc.thePlayer.posZ);
               boolean water1 = this.mc.theWorld.getBlockState(under).getBlock() == Blocks.water && this.mc.theWorld.getBlockState(water).getBlock() != Blocks.water;
               if(this.mc.thePlayer.motionY < 0.0D && water1) {
                  this.mc.thePlayer.motionY = 0.4000000059604645D;
                  this.mc.thePlayer.motionX *= 1.0299999713897705D;
                  this.mc.thePlayer.motionZ *= 1.0299999713897705D;
               }
               break;

            case 2://AAC
               if(this.mc.gameSettings.keyBindJump.pressed) {
                  return;
               }

               if(this.mc.thePlayer.worldObj.handleMaterialAcceleration(this.mc.thePlayer.getEntityBoundingBox().expand(0.0D, -1.0D, 0.0D).contract(0.001D, 0.0D, 0.001D), Material.water, this.mc.thePlayer)) {
                  this.mc.gameSettings.keyBindJump.pressed = true;
                  this.lastSwim = 1L;
               } else if(this.lastSwim == 1L) {
                  this.mc.gameSettings.keyBindJump.pressed = false;
                  this.lastSwim = 0L;
               }
               break;
         }

   }
}
