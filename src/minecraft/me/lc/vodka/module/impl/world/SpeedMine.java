package me.lc.vodka.module.impl.world;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.utils.Colors;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

public class SpeedMine
  extends Module
{
  public static ArrayList<String> options = new ArrayList<>();
  public static Setting mode;
  public static Setting speed;
  
  public SpeedMine()
  {
    super("SpeedMine",0,Category.WORLD);
            Vodka.INSTANCE.SETTING_MANAGER.addSetting(mode = (new Setting(this,"MOD","Potion",options)));
            Vodka.INSTANCE.SETTING_MANAGER.addSetting(speed = (new Setting(this,"Speed",3.0D,1.0D,5.0D,true)));

    options.add(0,"Normal");
    options.add(1,"Potion");
  }
  
  @EventTarget
  public void onUpdate(EventUpdate event)
  {
    if (mode.getCurrentOptionIndex() == 1)
    {
      this.mc.playerController.blockHitDelay = 0;
      boolean item = this.mc.thePlayer.getCurrentEquippedItem() == null;
      this.mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 0, item ? 1 : 0));
    }
  }
  
  public void onDisable()
  {
    if (mode.getCurrentOptionIndex() == 1) {
      this.mc.thePlayer.removePotionEffect(Potion.digSpeed.getId());
    }
  }
}