package me.lc.vodka.module.impl.player;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastEat extends Module {
   public Setting sTicks;
   public FastEat() {
      super("FastEat",0, Category.PLAYER);
      Vodka.INSTANCE.SETTING_MANAGER.addSetting(sTicks = (new Setting(this,"Ticks",0.0D,0.0D, 25.0D,false)));
   }
   @EventTarget
   public void onUpdate(EventUpdate e) {
         if(this.mc.thePlayer.isUsingItem() && this.mc.thePlayer.getCurrentEquippedItem() != null && this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood) {
            int useTicks = this.mc.thePlayer.getItemInUseDuration();
            if((double)useTicks >= this.sTicks.getCurrentValue()) {
               this.eatItem();
            }
         }

   }
   Runnable r2 = ()-> System.out.println("233333333");

   private void eatItem() {
      for(int i = 0; i < 15; ++i) {
         this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
      }

   }
}
