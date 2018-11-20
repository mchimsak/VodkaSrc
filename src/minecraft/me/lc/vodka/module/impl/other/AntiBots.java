package me.lc.vodka.module.impl.other;

import java.util.ArrayList;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventMotion;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;

public class AntiBots
  extends Module
{
  private static ArrayList<Player> playerList = new ArrayList();
  public static Setting movedOnce;
  public static Setting touchedGround ;
  public static Setting wasInvisible;
  public static Setting enableTicksExsitedCheck;
  public static Setting ticksExisted ;
  public static Setting enableIDCheck;
  public static Setting maximumID;
  Runnable r2 = ()-> System.out.println("233333333");

  public AntiBots()
  {
    super("AntiBots", 0,Category.WORLD);
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(movedOnce = (new Setting(this,"MovedOnce",true)));
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(touchedGround = (new Setting(this,"TouchedGround",true)));
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(wasInvisible = (new Setting(this,"WasInvisible",true)));
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(enableTicksExsitedCheck = (new Setting(this,"TicksExsitedCheck",true)));
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(enableIDCheck = (new Setting(this,"EnableIDCheck",true)));
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(ticksExisted = (new Setting(this,"TicksExisted",0.0D,0.0D,10000.0D,true)));
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(maximumID = (new Setting(this,"MaximumID",1500.0D,1000.0D,1.0E7D,true)));

  }
  
  public void onDisable()
  {
    playerList.clear();
    super.onDisable();
  }
  
  @EventTarget
  public void onUpdate(EventMotion event)
  {
    removeOld();
    for (EntityPlayer p : this.mc.theWorld.playerEntities)
    {
      boolean exist = false;
      for (Player player : playerList) {
        if (player.player.getName().equalsIgnoreCase(p.getName()))
        {
          player.update();
          exist = true;
        }
      }
      if (!exist) {
        playerList.add(new Player(p));
      }
    }
  }
  
  private void removeOld()
  {
    for (int i = 0; i < playerList.size(); i++)
    {
      Player player = (Player)playerList.get(i);
      boolean exist = false;
      for (EntityPlayer p : this.mc.theWorld.playerEntities) {
        if (p.getName().equalsIgnoreCase(player.player.getName())) {
          exist = true;
        }
      }
      if (!exist) {
        playerList.remove(i);
      }
    }
  }
  
  public static boolean isBot(EntityPlayer player)
  {
    for (Player p : playerList) {
      if (p.player.getName().equalsIgnoreCase(player.getName()))
      {
        if (((movedOnce.getBooleanValue()) && 
          (!p.moved()))) {
          return true;
        }
        if (((touchedGround.getBooleanValue()) && 
          (!p.touchedGround))) {
          return true;
        }
        if (((wasInvisible.getBooleanValue()) && 
          (p.wasInvisible))) {
          return true;
        }
        if (((enableTicksExsitedCheck.getBooleanValue()) && (player.ticksExisted <= ((Double)ticksExisted.getCurrentValue())))) {
          return true;
        }
        if (((enableIDCheck.getBooleanValue()) && (player.getEntityId() >= (maximumID.getCurrentValue() * 1000)))) {
          return true;
        }
      }
    }
    return false;
  }
  
  class Player
  {
    EntityPlayer player;
    int firstX;
    int firstY;
    int firstZ;
    boolean touchedGround;
    boolean wasInvisible;
    
    public Player(EntityPlayer player)
    {
      this.player = player;
      this.firstX = ((int)player.posX);
      this.firstY = ((int)player.posY);
      this.firstZ = ((int)player.posZ);
      this.touchedGround = false;
      this.wasInvisible = false;
    }
    
    void update()
    {
      if (this.player.onGround) {
        this.touchedGround = true;
      }
      if (this.player.isInvisible()) {
        this.wasInvisible = true;
      }
    }
    
    boolean moved()
    {
      return (this.firstX != (int)this.player.posX) || (this.firstY != (int)this.player.posY) || (this.firstZ != (int)this.player.posZ);
    }
  }
}
