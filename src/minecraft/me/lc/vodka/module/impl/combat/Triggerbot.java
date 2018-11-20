package me.lc.vodka.module.impl.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.vui.Timings;
import net.minecraft.entity.Entity;

public class Triggerbot extends Module {
   private Setting bRaycast;
   private Setting sCPS ;
   private Timings delayTimings = new Timings();
   Runnable r2 = ()-> System.out.println("233333333");
   public Triggerbot() {
      super("Triggerbot", 0,Category.COMBAT);
      Vodka.INSTANCE.SETTING_MANAGER.addSetting(sCPS = (new Setting(this,"CPS",10.0D,1.0D,20.0D,true)));
      Vodka.INSTANCE.SETTING_MANAGER.addSetting(bRaycast = (new Setting(this,"Raycast",false)));
   }

   @EventTarget
   public void onUpdate(EventUpdate e) {
      if(this.mc.objectMouseOver != null) {
         Entity onPoint = this.mc.objectMouseOver.entityHit;
         if(onPoint != null || onPoint != null && !onPoint.canAttackWithItem()) {
            boolean ray = false;
            if(this.bRaycast.getBooleanValue() && !this.findRaycast(onPoint).isEmpty()) {
               onPoint = (Entity)this.findRaycast(onPoint).get(0);
               ray = true;
            }

            if(!ray && this.delayTimings.hasReached(this.CPStoDelay())) {
               this.hitEntity(onPoint);
            }
         }
      }
   }

   private void hitEntity(Entity e) {
      this.mc.clickMouse();
      this.delayTimings.resetTimings();
   }

   private List findRaycast(Entity e) {
      ArrayList arrayList = new ArrayList();
      Iterator var4 = this.mc.theWorld.loadedEntityList.iterator();

      while(var4.hasNext()) {
         Entity rs = (Entity)var4.next();
         if((double)rs.getDistanceToEntity(e) <= 0.5D && rs.isInvisible()) {
            arrayList.add(rs);
         }
      }

      return arrayList;
   }

   private long CPStoDelay() {
      return (long)(this.sCPS.getMaxValue() / this.sCPS.getCurrentValue() * 40.0D);
   }
}
