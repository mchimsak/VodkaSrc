package me.lc.vodka.module.impl.world;

import java.util.ArrayList;
import java.util.Iterator;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.vui.BlickWinkel3D;
import me.lc.vodka.vui.Line3D;
import me.lc.vodka.vui.Location3D;
import me.lc.vodka.utils.RotationHandler;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

public class ChestAura extends Module {
   BlockPos breaking;
   int state = 0;
   int delayc = 0;
   public Setting range;
   public Setting delay;
   public Setting Look;
   public Setting throughBlocksButton;
   public static int chest;
   public BlockPos openNextTick = null;
   public static ArrayList opened;

   static {
      chest = Block.getIdFromBlock(Blocks.chest);
      opened = new ArrayList();
   }
   Runnable r2 = ()-> System.out.println("233333333");

   public ChestAura() {
      super("ChestAura",0, Category.WORLD);
      Vodka.INSTANCE.SETTING_MANAGER.addSetting( range = (new Setting( this, "Range",2.0D,0.0D, 6.0D,true )));
      Vodka.INSTANCE.SETTING_MANAGER.addSetting( delay = (new Setting( this, "Delay",2.0D,0.0D, 10.0D,true )));
      Vodka.INSTANCE.SETTING_MANAGER.addSetting( Look = (new Setting( this, "Look",true )));
      Vodka.INSTANCE.SETTING_MANAGER.addSetting( throughBlocksButton = (new Setting( this, "ThroughBlocks",false )));

   }

   @EventTarget
   public void onUpdate(EventUpdate e) {
      if(!this.mc.thePlayer.isUsingItem()) {
            if(!(this.mc.currentScreen instanceof GuiInventory)) {
               try {
                  if(this.mc.currentScreen instanceof GuiChest) {
                     this.mc.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode());
                     this.mc.gameSettings.keyBindBack.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode());
                     this.mc.gameSettings.keyBindRight.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode());
                     this.mc.gameSettings.keyBindLeft.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode());
                     this.mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode());
                     this.mc.gameSettings.keyBindSneak.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindSneak.getKeyCode());
                     return;
                  }

                  if(this.mc.currentScreen == null) {
                     this.mc.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode());
                     this.mc.gameSettings.keyBindBack.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode());
                     this.mc.gameSettings.keyBindRight.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode());
                     this.mc.gameSettings.keyBindLeft.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode());
                     this.mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode());
                     this.mc.gameSettings.keyBindSneak.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindSneak.getKeyCode());
                  }
               } catch (Exception var11) {
                  ;
               }

               if(this.openNextTick != null) {
                  this.sendClick(this.openNextTick);
                  this.openNextTick = null;
               }

               if((double)this.delayc < this.delay.getCurrentValue()) {
                  ++this.delayc;
               } else {
                  this.delayc = (int)((double)this.delayc - this.delay.getCurrentValue());

                  for(double x = -this.range.getCurrentValue(); x < this.range.getCurrentValue(); ++x) {
                     for(double y = -this.range.getCurrentValue(); y < this.range.getCurrentValue(); ++y) {
                        for(double z = -this.range.getCurrentValue(); z < this.range.getCurrentValue(); ++z) {
                           final BlockPos pos = new BlockPos(this.mc.thePlayer.posX + x, this.mc.thePlayer.posY + y, this.mc.thePlayer.posZ + z);
                           if(!opened.contains(pos) && Block.getIdFromBlock(this.mc.theWorld.getBlockState(pos).getBlock()) == chest && Math.sqrt(this.mc.thePlayer.getDistanceSqToCenter(pos)) < this.range.getCurrentValue() && (this.throughBlocksButton.getBooleanValue() || !this.isBlockBetween(pos))) {
                              this.mc.thePlayer.swingItem();
                              if(this.Look.getBooleanValue()) {
                                 Location3D player = new Location3D(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.7000000476837158D, this.mc.thePlayer.posZ);
                                 Location3D block = new Location3D((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D);
                                 BlickWinkel3D look = new BlickWinkel3D(player, block);
                                 RotationHandler.server_pitch = (float)look.getPitch();
                                 RotationHandler.server_yaw = (float)look.getYaw();
                              }

                              this.openNextTick = pos;
                              opened.add(pos);
                              (new Thread(new Runnable() {
                                 public void run() {
                                    try {
                                       Thread.sleep(30000L);
                                    } catch (Exception var5) {
                                       ;
                                    } finally {
                                       ChestAura.opened.remove(pos);
                                    }

                                 }
                              })).start();
                              return;
                           }
                        }
                     }
                  }
               }
            }
      }
   }

   public boolean isBlockBetween(BlockPos pos) {
      Location3D chestL = new Location3D((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D);
      Location3D player = new Location3D(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.7D, this.mc.thePlayer.posZ);
      if(player.distance(chestL) < 0.5D) {
         return true;
      } else {
         Line3D line = new Line3D(player, chestL);
         Iterator var6 = line.getPointsOn(0.5D).iterator();

         while(var6.hasNext()) {
            Location3D point = (Location3D)var6.next();
            if(this.mc.theWorld.getBlockState(new BlockPos(point.getX(), point.getY(), point.getZ())).getBlock() == this.mc.theWorld.getBlockState(pos)) {
               return false;
            }
         }

         return false;
      }
   }

   public void onEnable() {
      opened.clear();
      super.onEnable();
   }

   public void sendClick(BlockPos pos) {
      C08PacketPlayerBlockPlacement packet = new C08PacketPlayerBlockPlacement(pos, (double)pos.getY() + 0.5D < this.mc.thePlayer.posY + 1.7D?1:0, this.mc.thePlayer.getCurrentEquippedItem(), 0.0F, 0.0F, 0.0F);
      this.mc.thePlayer.sendQueue.addToSendQueue(packet);
   }
}
