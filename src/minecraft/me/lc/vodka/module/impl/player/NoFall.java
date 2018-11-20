package me.lc.vodka.module.impl.player;

import java.util.ArrayList;
import java.util.Iterator;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.vui.Line3D;
import me.lc.vodka.vui.Location3D;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class NoFall extends Module {
   double startY = 0.0D;
   public Setting Mod;
   public static ArrayList<String> options = new ArrayList<>();

   public NoFall() {
         super("NoFall", 0,Category.PLAYER);
         Vodka.INSTANCE.SETTING_MANAGER.addSetting(Mod = (new Setting(this,"MOD","AAC1",options)));
         options.add(0,"Vanilla");
         options.add(1,"AAC1");
         options.add(2,"AAC2");
         options.add(3,"AAC3");
         options.add(4,"AAC4");

   }
   Runnable r2 = ()-> System.out.println("233333333");

   @EventTarget
   public void onUpdate(EventUpdate e) {

         switch (Mod.getCurrentOptionIndex()) {

            case 0:
               if (this.mc.thePlayer.fallDistance > 2.0F)
               {
                  this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                  this.mc.thePlayer.fallDistance = 0.0F;
               }
               break;

            case 1://AAC1
               if(this.mc.thePlayer.isInLava() || this.mc.thePlayer.isInWater()) {
                  return;
               }

               if(this.mc.thePlayer.onGround && this.mc.thePlayer.motionY < 0.0D) {
                  this.mc.thePlayer.motionY = -9.899999618530273D;
                  return;
               }
               break;

            case 2://AAC2
               this.onAAC321();
               break;
            case 3://AAC3
               if (this.mc.thePlayer.onGround) {
                  this.mc.thePlayer.motionY = -9.0D;
               }
               break;
            case 4://AAC4
               if (this.mc.thePlayer.fallDistance > 2.0F)
               {
                  this.mc.thePlayer.motionX = (this.mc.thePlayer.motionZ = 0.0D);
                  this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.001D, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
                  this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
               }
               break;
         }

      if(this.mc.thePlayer.fallDistance > 2.0F) {
         this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
      }

   }

   public void onAAC321() {
      if(this.mc.thePlayer.fallDistance > 2.0F) {
         this.startY += -this.mc.thePlayer.motionY;
         if(this.startY > 2.5D) {
            Line3D line = new Line3D(new Location3D(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ), 0.0D, -90.0D, 99.0D);
            Iterator var3 = line.getPointsOn(0.5D).iterator();

            while(var3.hasNext()) {
               Location3D point = (Location3D)var3.next();
               BlockPos pos = new BlockPos(point.getX(), point.getY(), point.getZ());
               if(this.mc.theWorld.getBlockState(pos).getBlock() != Blocks.air) {
                  double top = this.mc.theWorld.getBlockState(pos).getBlock().getBlockBoundsMaxY() + (double)pos.getY();
                  if(this.mc.thePlayer.posY - top < 11.0D) {
                     this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, top, this.mc.thePlayer.posZ, true));
                  }
                  break;
               }
            }

            this.startY = 0.0D;
         }
      } else {
         this.startY = 99.0D;
      }

   }
}
