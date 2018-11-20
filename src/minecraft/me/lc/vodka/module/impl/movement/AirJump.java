package me.lc.vodka.module.impl.movement;


import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;

public class AirJump extends Module {
   public Setting Keypress;
   private boolean hasJumped;
   Runnable r2 = ()-> System.out.println("233333333");

   public AirJump() {
      super("AirJump", 0,Category.MOVEMENT);
      Vodka.INSTANCE.SETTING_MANAGER.addSetting(Keypress = (new Setting(this,"Keypress",false)));
   }
   @EventTarget
   public void onUpdate(EventUpdate e) {
      if(this.mc.gameSettings.keyBindJump.pressed != this.hasJumped || !this.Keypress.getBooleanValue()) {
         this.mc.thePlayer.onGround = true;
      }

      this.hasJumped = this.mc.gameSettings.keyBindJump.pressed;
   }
}
