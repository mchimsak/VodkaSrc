package me.lc.vodka.module.impl.movement;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class WaterSpeed extends Module {

   boolean goUp = false;
   boolean ready = false;

   public WaterSpeed() {
      super("WaterSpeed", 0,Category.MOVEMENT);
   }
   Runnable r2 = ()-> System.out.println("233333333");


   @EventTarget
   public void onAAC321(EventUpdate e) {
      if(!this.mc.thePlayer.isInWater()) {
         this.ready = false;
      } else if(this.mc.gameSettings.keyBindJump.isKeyDown()) {
         this.ready = false;
      } else {
         BlockPos pos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.5D, this.mc.thePlayer.posZ);
         BlockPos pos2 = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.55D, this.mc.thePlayer.posZ);
         if(this.goUp && this.mc.theWorld.getBlockState(pos).getBlock() != Blocks.water) {
            this.goUp = false;
         }

         if(!this.goUp && this.mc.theWorld.getBlockState(pos2).getBlock() == Blocks.water) {
            this.goUp = true;
            this.ready = true;
         }

         if(this.ready) {
               if(this.goUp) {
                  this.mc.thePlayer.motionY += 0.02800000086426735D;
                  this.mc.thePlayer.motionX *= 1.1729999780654907D;
                  this.mc.thePlayer.motionZ *= 1.1729999780654907D;
               } else {
                  this.mc.thePlayer.motionY += -0.05999999865889549D;
               }

         } else {
            this.mc.thePlayer.motionY += 0.029999999329447746D;
         }

      }
   }
}
