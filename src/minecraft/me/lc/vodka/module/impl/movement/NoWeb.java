package me.lc.vodka.module.impl.movement;


import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class NoWeb extends Module {
   double speed = 0.0D;
   Runnable r2 = ()-> System.out.println("233333333");

   public NoWeb() {
      super("NoWeb", 0,Category.MOVEMENT);
   }
   @EventTarget
   public void onUpdate(EventUpdate e) {
      BlockPos pos1 = new BlockPos(this.mc.thePlayer.posX + 0.3D, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + 0.3D);
      BlockPos pos2 = new BlockPos(this.mc.thePlayer.posX - 0.3D, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + 0.3D);
      BlockPos pos3 = new BlockPos(this.mc.thePlayer.posX + 0.3D, this.mc.thePlayer.posY, this.mc.thePlayer.posZ - 0.3D);
      BlockPos pos4 = new BlockPos(this.mc.thePlayer.posX - 0.3D, this.mc.thePlayer.posY, this.mc.thePlayer.posZ - 0.3D);
      BlockPos posu1 = new BlockPos(this.mc.thePlayer.posX + 0.3D, this.mc.thePlayer.posY + 1.0D, this.mc.thePlayer.posZ + 0.3D);
      BlockPos posu2 = new BlockPos(this.mc.thePlayer.posX - 0.3D, this.mc.thePlayer.posY + 1.0D, this.mc.thePlayer.posZ + 0.3D);
      BlockPos posu3 = new BlockPos(this.mc.thePlayer.posX + 0.3D, this.mc.thePlayer.posY + 1.0D, this.mc.thePlayer.posZ - 0.3D);
      BlockPos posu4 = new BlockPos(this.mc.thePlayer.posX - 0.3D, this.mc.thePlayer.posY + 1.0D, this.mc.thePlayer.posZ - 0.3D);
      BlockPos posuu1 = new BlockPos(this.mc.thePlayer.posX + 0.3D, this.mc.thePlayer.posY + 1.8D, this.mc.thePlayer.posZ + 0.3D);
      BlockPos posuu2 = new BlockPos(this.mc.thePlayer.posX - 0.3D, this.mc.thePlayer.posY + 1.8D, this.mc.thePlayer.posZ + 0.3D);
      BlockPos posuu3 = new BlockPos(this.mc.thePlayer.posX + 0.3D, this.mc.thePlayer.posY + 1.8D, this.mc.thePlayer.posZ - 0.3D);
      BlockPos posuu4 = new BlockPos(this.mc.thePlayer.posX - 0.3D, this.mc.thePlayer.posY + 1.8D, this.mc.thePlayer.posZ - 0.3D);
      if(this.mc.theWorld.getBlockState(pos1).getBlock() != Blocks.web && this.mc.theWorld.getBlockState(pos2).getBlock() != Blocks.web && this.mc.theWorld.getBlockState(pos3).getBlock() != Blocks.web && this.mc.theWorld.getBlockState(pos4).getBlock() != Blocks.web && this.mc.theWorld.getBlockState(posu1).getBlock() != Blocks.web && this.mc.theWorld.getBlockState(posu2).getBlock() != Blocks.web && this.mc.theWorld.getBlockState(posu3).getBlock() != Blocks.web && this.mc.theWorld.getBlockState(posu4).getBlock() != Blocks.web && this.mc.theWorld.getBlockState(posuu1).getBlock() != Blocks.web && this.mc.theWorld.getBlockState(posuu2).getBlock() != Blocks.web && this.mc.theWorld.getBlockState(posuu3).getBlock() != Blocks.web && this.mc.theWorld.getBlockState(posuu4).getBlock() != Blocks.web) {
         this.speed = 0.0D;
      } else if(!this.mc.gameSettings.keyBindForward.isKeyDown()) {
         this.speed = 0.0D;
         this.mc.thePlayer.motionY = -1.0D;
      } else {
         this.speed = 1.1799999475479126D;
         if(this.mc.thePlayer.onGround) {
            this.portMove(0.0F, 0.0F, 0.1F);
            this.speed = 0.0D;
         } else {
            this.move(this.mc.thePlayer.rotationYaw, (float)this.speed);
            this.mc.thePlayer.motionY = 0.0D;
         }
      }
   }
   public void portMove(float yaw, float multiplyer, float up) {
      double moveX = -Math.sin(Math.toRadians((double)yaw)) * (double)multiplyer;
      double moveZ = Math.cos(Math.toRadians((double)yaw)) * (double)multiplyer;
      double moveY = (double)up;
      this.mc.thePlayer.setPosition(moveX + this.mc.thePlayer.posX, moveY + this.mc.thePlayer.posY, moveZ + this.mc.thePlayer.posZ);
   }
   public void move(float yaw, float multiplyer) {
      double moveX = -Math.sin(Math.toRadians((double)yaw)) * (double)multiplyer;
      double moveZ = Math.cos(Math.toRadians((double)yaw)) * (double)multiplyer;
      this.mc.thePlayer.motionX = moveX;
      this.mc.thePlayer.motionZ = moveZ;
   }
}
