package me.lc.vodka.module.impl.movement;

import java.util.ArrayList;
import java.util.Iterator;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class WallSpeed extends Module {
   int state = 0;
   double boost = 0.0D;
   double startY = 0.0D;
   boolean settedTimer = false;
   boolean isDoing;
   int i = 0;
   Runnable r2 = ()-> System.out.println("233333333");

   public WallSpeed() {
      super("WallSpeed", 0,Category.MOVEMENT);
   }

   @EventTarget
   public void onUpdate(EventUpdate e) {
      this.onAAC334();
   }

   public void onAAC334() {
      if(!this.mc.gameSettings.keyBindForward.isKeyDown()) {
         this.i = -10;
         this.boost = 0.0D;
      } else {
         ArrayList sensors = new ArrayList();
         sensors.add(new BlockPos(this.mc.thePlayer.posX + 0.7D, this.mc.thePlayer.posY + 1.0D, this.mc.thePlayer.posZ));
         sensors.add(new BlockPos(this.mc.thePlayer.posX - 0.7D, this.mc.thePlayer.posY + 1.0D, this.mc.thePlayer.posZ));
         sensors.add(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0D, this.mc.thePlayer.posZ + 0.7D));
         sensors.add(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0D, this.mc.thePlayer.posZ - 0.7D));
         boolean isWall = false;
         Iterator var4 = sensors.iterator();

         while(var4.hasNext()) {
            BlockPos willBe = (BlockPos)var4.next();
            if(this.mc.theWorld.getBlockState(willBe).getBlock().getBlockBoundsMaxY() == this.mc.theWorld.getBlockState(willBe).getBlock().getBlockBoundsMinY() + 1.0D && !this.mc.theWorld.getBlockState(willBe).getBlock().isTranslucent() && this.mc.theWorld.getBlockState(willBe).getBlock() != Blocks.water && !(this.mc.theWorld.getBlockState(willBe).getBlock() instanceof BlockSlab) || this.mc.theWorld.getBlockState(willBe).getBlock() == Blocks.barrier) {
               isWall = true;
               break;
            }
         }

         if(isWall && !this.mc.thePlayer.isCollidedHorizontally && !this.mc.gameSettings.keyBindJump.isKeyDown() && this.mc.thePlayer.motionY <= 0.1D) {
            this.isDoing = true;
            ++this.i;
            this.settedTimer = true;
            this.mc.timer.timerSpeed = 1.01F;
            this.boost = Math.sqrt(Math.abs(this.mc.thePlayer.motionX) * Math.abs(this.mc.thePlayer.motionX) + Math.abs(this.mc.thePlayer.motionZ) * Math.abs(this.mc.thePlayer.motionZ));
            if(this.mc.thePlayer.onGround) {
               this.mc.thePlayer.jump();
               if(this.i > 20) {
                  this.mc.thePlayer.motionX *= 1.027999997138977D;
                  this.mc.thePlayer.motionZ *= 1.027999997138977D;
               } else if(this.i > 10) {
                  this.mc.thePlayer.motionX *= 1.0230000019073486D;
                  this.mc.thePlayer.motionZ *= 1.0230000019073486D;
               }

               this.mc.thePlayer.motionY = 0.09000000357627869D;
            } else {
               this.mc.thePlayer.motionY -= 0.10000000149011612D;
               if(this.i > 20) {
                  this.mc.thePlayer.motionX *= 1.0241999626159668D;
                  this.mc.thePlayer.motionZ *= 1.0241999626159668D;
               } else if(this.i > 10) {
                  this.mc.thePlayer.motionX *= 1.0210000276565552D;
                  this.mc.thePlayer.motionZ *= 1.0210000276565552D;
               }
            }

         } else {
            this.i = 0;
            if(this.boost > 0.22D && this.mc.thePlayer.onGround) {
               this.move(this.mc.thePlayer.rotationYaw, (float)this.boost);
            }

            this.boost /= 1.0180000066757202D;
         }
      }
   }

   public void move(float yaw, float multiplyer) {
      double moveX = -Math.sin(Math.toRadians((double)yaw)) * (double)multiplyer;
      double moveZ = Math.cos(Math.toRadians((double)yaw)) * (double)multiplyer;
      this.mc.thePlayer.motionX = moveX;
      this.mc.thePlayer.motionZ = moveZ;
   }
}
