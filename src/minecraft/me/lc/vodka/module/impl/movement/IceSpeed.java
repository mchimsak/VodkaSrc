package me.lc.vodka.module.impl.movement;

import java.util.ArrayList;
import java.util.Arrays;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.Event;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;

public class IceSpeed extends Module {
   private Setting Mode;
   private boolean moved;
   private double speed;
   Runnable r2 = ()-> System.out.println("233333333");

   public static ArrayList<String> options = new ArrayList<>();
   public IceSpeed() {
      super("IceSpeed", 0,Category.MOVEMENT);
      Vodka.INSTANCE.SETTING_MANAGER.addSetting(Mode = (new Setting(this,"MOD","Vanilla",options)));
      options.add(0,"Vanilla");
      options.add(0,"AAC");

   }

   @EventTarget
   public void onUpdate(EventUpdate e) {
      if(!(this.mc.theWorld.getBlockState(this.mc.thePlayer.getPosition().add(0.0D, -0.5D, 0.0D)).getBlock() instanceof BlockIce) && !(this.mc.theWorld.getBlockState(this.mc.thePlayer.getPosition().add(0.0D, -0.5D, 0.0D)).getBlock() instanceof BlockPackedIce)) {
         if(this.moved) {
            this.mc.thePlayer.capabilities.isFlying = false;
            this.moved = false;
         }
      } else {
         String mode = this.Mode.getCurrentOption();
         if(this.mc.gameSettings.keyBindJump.pressed) {
            this.mc.thePlayer.capabilities.isFlying = false;
            this.moved = false;
            return;
         }

         this.moved = true;
         if(mode.equalsIgnoreCase("Vanilla")) {
            this.vanillaSpeed();
         } else if(mode.equalsIgnoreCase("AAC 3.3.7")) {
            this.aac337Speed();
         }
      }

   }

   private void vanillaSpeed() {
      this.mc.thePlayer.motionX *= 1.1D;
      this.mc.thePlayer.motionZ *= 1.1D;
   }

   private void aac337Speed() {
      this.mc.thePlayer.capabilities.isFlying = true;
   }
}
