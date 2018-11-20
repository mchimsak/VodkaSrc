/*  1:   */ package me.lc.vodka.module.impl.world;
/*  2:   */

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;

/*  9:   */ public class FastPlace
/* 10:   */   extends Module
/* 11:   */ {
        public Setting value;
/* 12:   */   public FastPlace()
/* 13:   */   {
/* 14:14 */     super("FastPlace", 0,Category.WORLD);
            Vodka.INSTANCE.SETTING_MANAGER.addSetting(value = (new Setting(this,"Value",1.0D,0.D,5.0D,true)));
/* 15:   */   }
/* 16:   */
/* 17:   */   @EventTarget
/* 18:   */   public void onUpdate(EventUpdate event)
/* 19:   */   {
/* 20:18 */     this.mc.rightClickDelayTimer = (int)value.getCurrentValue();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void onDisable()
/* 24:   */   {
/* 25:22 */     super.onDisable();
/* 26:23 */     this.mc.rightClickDelayTimer = 4;
/* 27:   */   }
/* 28:   */ }