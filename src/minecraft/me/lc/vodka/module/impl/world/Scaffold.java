package me.lc.vodka.module.impl.world;

import java.util.ArrayList;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventRender3D;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.vui.BlickWinkel3D;
import me.lc.vodka.vui.Location3D;
import me.lc.vodka.utils.RotationHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class Scaffold extends Module {
   public Setting Mod;
   public static ArrayList<String> options = new ArrayList<>();
   private Setting bSilent;
   private Setting bPushback;
   private Setting sPushback;
   private float lastYaw;
   private BlockPos lastPos;
   int look = 0;
   boolean wasSprinting = false;
   boolean sneakWasSetted = false;
   int lastRot = 0;
   private int a = 0;
   Runnable r2 = ()-> System.out.println("233333333");

   public Scaffold() {
      super("Scaffold", 0,Category.WORLD);
      Vodka.INSTANCE.SETTING_MANAGER.addSetting(Mod = (new Setting(this,"MOD","AAC",options)));
      options.add(0,"AAC");
      options.add(1,"AACFast");
      options.add(2,"Sneak");
      Vodka.INSTANCE.SETTING_MANAGER.addSetting(bSilent = new Setting(this,"Silent",false));
      Vodka.INSTANCE.SETTING_MANAGER.addSetting(bPushback = new Setting(this,"Push back",false));
      Vodka.INSTANCE.SETTING_MANAGER.addSetting(sPushback = new Setting(this,"Push back",0.1D,0.1D, 0.5D,false));

   }

   public void checkSprint() {
      this.mc.thePlayer.setSprinting(false);
      this.mc.gameSettings.keyBindSprint.pressed = false;
      this.wasSprinting = false;
   }

   private int findBlocks() {
      for(int i = 0; i < 9; ++i) {
         if(this.mc.thePlayer.inventoryContainer.getSlot(36 + i).getHasStack() && this.mc.thePlayer.inventoryContainer.getSlot(36 + i).getStack().getItem() instanceof ItemBlock) {
            return 36 + i;
         }
      }

      return -1;
   }

   public void onDisable() {
      this.checkSprint();
      this.lastPos = null;
      if(this.bSilent.getBooleanValue()){
         this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
      }

      if(this.sneakWasSetted) {
         this.sneakWasSetted = false;
         this.mc.gameSettings.keyBindSneak.pressed = false;
      }
      super.onDisable();
   }


   @EventTarget
   public void onFrameRender(EventRender3D e) {
      double var10000 = this.mc.thePlayer.posX;
      Minecraft.getMinecraft().getRenderManager();
      double x = var10000 - RenderManager.renderPosX;
      var10000 = this.mc.thePlayer.posY - 0.5D;
      Minecraft.getMinecraft().getRenderManager();
      double y = var10000 - RenderManager.renderPosY;
      var10000 = this.mc.thePlayer.posZ;
      Minecraft.getMinecraft().getRenderManager();
      double z = var10000 - RenderManager.renderPosZ;
      GL11.glEnable(3042);
      GL11.glLineWidth(1.0F);
      GL11.glColor4f(0.2F, 0.3F, 1.0F, 1.0F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      RenderGlobal.func_181561_a(new AxisAlignedBB(x - 1.0D, y - 0.5D, z - 1.0D, x + 1.0D, y + 0.5D, z + 1.0D));
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
   }

   @EventTarget
      public void onUpdate(EventUpdate e) {
      if(a == 0) {
         this.lastYaw = this.mc.thePlayer.rotationYaw;
         a = a+1;
      }
      if(Vodka.INSTANCE.MODULE_MANAGER.getModule("KillAura").isToggled() && this.bSilent.getBooleanValue()) {
         this.mc.thePlayer.sendQueue.addToSendQueue( new C09PacketHeldItemChange( this.mc.thePlayer.inventory.currentItem ) );
      }
         if(this.bSilent.getBooleanValue()) {
         this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
      }

      if(Math.abs(this.mc.thePlayer.rotationYaw - this.lastYaw) > 15.0F) {
         this.lastYaw = this.mc.thePlayer.rotationYaw;
         this.mc.thePlayer.motionX = 0.0D;
         this.mc.thePlayer.motionZ = 0.0D;
      } else {
         ItemStack itemStack = this.mc.thePlayer.getCurrentEquippedItem();
         if(this.bSilent.getBooleanValue() && this.findBlocks() != -1) {
            itemStack = this.mc.thePlayer.inventoryContainer.getSlot(this.findBlocks()).getStack();
         }

         try {
            if(itemStack == null) {
               return;
            }

            if(!(itemStack.getItem() instanceof ItemBlock)) {
               return;
            }

            BlockPos place = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.5D, this.mc.thePlayer.posZ);
            BlockPos at = this.at(place);
            this.checkSprint();
            int faceing = this.lastRot;
            boolean shouldLook = this.shouldLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
            float nextYaw = this.mc.thePlayer.rotationYaw;
            if(this.sneakWasSetted) {
               this.sneakWasSetted = false;
               this.mc.gameSettings.keyBindSneak.pressed = false;
            }

            this.mc.thePlayer.setSprinting(false);
            if(Mod.getCurrentOptionIndex() == 0 || Mod.getCurrentOptionIndex() == 1 || Mod.getCurrentOptionIndex() == 2) {
               this.mc.gameSettings.keyBindSprint.pressed = false;
               this.mc.thePlayer.setSprinting(false);
               this.wasSprinting = true;
            }

            Location3D end = null;
            Location3D start = new Location3D(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.6D, this.mc.thePlayer.posZ);
            if(faceing == 4) {
               start = new Location3D(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.6D, (double)at.getZ() + 0.5D);
               end = new Location3D((double)at.getX(), (double)at.getY() + 0.5D, (double)at.getZ() + 0.5D);
            } else if(faceing == 5) {
               start = new Location3D(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.6D, (double)at.getZ() + 0.5D);
               end = new Location3D((double)(at.getX() + 1), (double)at.getY() + 0.5D, (double)at.getZ() + 0.5D);
            } else if(faceing == 2) {
               start = new Location3D((double)at.getX() + 0.5D, this.mc.thePlayer.posY + 1.6D, this.mc.thePlayer.posZ);
               end = new Location3D((double)at.getX() + 0.5D, (double)at.getY() + 0.5D, (double)at.getZ());
            } else if(faceing == 3) {
               start = new Location3D((double)at.getX() + 0.5D, this.mc.thePlayer.posY + 1.6D, this.mc.thePlayer.posZ);
               end = new Location3D((double)at.getX() + 0.5D, (double)at.getY() + 0.5D, (double)(at.getZ() + 1));
            }

            BlickWinkel3D v = new BlickWinkel3D(start, end);
            RotationHandler.server_pitch = (float)(81.3D + (Math.random() < 0.1D?Math.random() / 5.0D:0.0D));
            RotationHandler.server_yaw = (float)v.getYaw();
            nextYaw = (float)v.getYaw();
            this.look = 5;
            if(Mod.getCurrentOptionIndex() == 1) {
               this.mc.thePlayer.motionX *= 1.0099999904632568D;
               this.mc.thePlayer.motionZ *= 1.0099999904632568D;
            } else if(Mod.getCurrentOptionIndex() == 0) {
               this.mc.thePlayer.motionX /= 1.15D;
               this.mc.thePlayer.motionZ /= 1.15D;
            }

            if(this.look > 0) {
               RotationHandler.server_pitch = 81.4F;
               RotationHandler.server_yaw = nextYaw;
               --this.look;
            }

            if(this.shouldPlace(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ)) {
               new Vec3((double)place.getX(), (double)place.getY(), (double)place.getZ());
               if(this.mc.thePlayer.onGround) {
                  this.mc.thePlayer.motionY = Math.random() / 25.0D;
               }

               this.portMove(this.mc.thePlayer.rotationYaw, 0.1F, 0.0F);
               if(this.bSilent.getBooleanValue() && this.findBlocks() != -1) {
                  this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.findBlocks() - 36));
               }

               this.mc.thePlayer.swingItem();
               IBlockState ibl = ((ItemBlock)itemStack.getItem()).getBlock().getDefaultState();
               this.mc.theWorld.setBlockState(place, ibl);
               C08PacketPlayerBlockPlacement bl = new C08PacketPlayerBlockPlacement(at, faceing, itemStack, 0.0F, 0.0F, 0.0F);
               if(this.bPushback.getBooleanValue()) {
                  this.move(this.mc.thePlayer.rotationYaw, (float)(-this.sPushback.getCurrentValue()));
               }

               this.mc.thePlayer.sendQueue.addToSendQueue(bl);
               if(Mod.getCurrentOptionIndex() == 2) {
                  this.sneakWasSetted = true;
                  this.mc.gameSettings.keyBindSneak.pressed = true;
               }
            }
         } catch (Exception var12) {
            ;
         }

         this.lastYaw = this.mc.thePlayer.rotationYaw;
         this.lastPos = this.mc.thePlayer.getPosition();
      }
   }

   public BlockPos at(BlockPos want) {
      BlockPos o1 = new BlockPos(want.getX() + 1, want.getY(), want.getZ());
      BlockPos o2 = new BlockPos(want.getX() - 1, want.getY(), want.getZ());
      BlockPos o3 = new BlockPos(want.getX(), want.getY(), want.getZ() + 1);
      BlockPos o4 = new BlockPos(want.getX(), want.getY(), want.getZ() - 1);
      if(this.mc.theWorld.getBlockState(o1).getBlock() != Blocks.air) {
         this.lastRot = 4;
         return o1;
      } else if(this.mc.theWorld.getBlockState(o2).getBlock() != Blocks.air) {
         this.lastRot = 5;
         return o2;
      } else if(this.mc.theWorld.getBlockState(o3).getBlock() != Blocks.air) {
         this.lastRot = 2;
         return o3;
      } else if(this.mc.theWorld.getBlockState(o4).getBlock() != Blocks.air) {
         this.lastRot = 3;
         return o4;
      } else {
         return null;
      }
   }

   public boolean shouldPlace(double x, double y, double z) {
      BlockPos p1 = new BlockPos(x - 0.23999999463558197D, y - 0.5D, z - 0.23999999463558197D);
      BlockPos p2 = new BlockPos(x - 0.23999999463558197D, y - 0.5D, z + 0.23999999463558197D);
      BlockPos p3 = new BlockPos(x + 0.23999999463558197D, y - 0.5D, z + 0.23999999463558197D);
      BlockPos p4 = new BlockPos(x + 0.23999999463558197D, y - 0.5D, z - 0.23999999463558197D);
      return this.mc.thePlayer.worldObj.getBlockState(p1).getBlock() == Blocks.air && this.mc.thePlayer.worldObj.getBlockState(p2).getBlock() == Blocks.air && this.mc.thePlayer.worldObj.getBlockState(p3).getBlock() == Blocks.air && this.mc.thePlayer.worldObj.getBlockState(p4).getBlock() == Blocks.air;
   }

   public boolean shouldLook(double x, double y, double z) {
      BlockPos p1 = new BlockPos(x - 0.029999999329447746D, y - 0.5D, z - 0.029999999329447746D);
      BlockPos p2 = new BlockPos(x - 0.029999999329447746D, y - 0.5D, z + 0.029999999329447746D);
      BlockPos p3 = new BlockPos(x + 0.029999999329447746D, y - 0.5D, z + 0.029999999329447746D);
      BlockPos p4 = new BlockPos(x + 0.029999999329447746D, y - 0.5D, z - 0.029999999329447746D);
      return this.mc.thePlayer.worldObj.getBlockState(p1).getBlock() == Blocks.air && this.mc.thePlayer.worldObj.getBlockState(p2).getBlock() == Blocks.air && this.mc.thePlayer.worldObj.getBlockState(p3).getBlock() == Blocks.air && this.mc.thePlayer.worldObj.getBlockState(p4).getBlock() == Blocks.air;
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
