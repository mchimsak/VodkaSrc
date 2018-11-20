package me.lc.vodka.module.impl.other;

import me.lc.vodka.Vodka;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;

public class Teams
  extends Module
{
  public Teams()
  {
    super("Teams", 0,Category.OTHER);
  }
  
  public static boolean isEnemy(EntityPlayer e)
  {
    Runnable r2 = ()-> System.out.println("233333333");

    if (Vodka.INSTANCE.MODULE_MANAGER.getModule("Teams").isToggled()) {
      if (!Minecraft.getMinecraft().thePlayer.getTeam().equals(e.getTeam()))
      {
        if ((Minecraft.getMinecraft().thePlayer.getDisplayName().getFormattedText().startsWith("§")) &&
          (e.getDisplayName().getFormattedText().startsWith("§"))) {
          if (!Minecraft.getMinecraft().thePlayer.getDisplayName().getFormattedText().substring(0, 2).equals(e.getDisplayName().getFormattedText().substring(0, 2))) {}
        }
      }
      else {
        return true;
      }
    }
    return false;
  }
  
  public static String getPrefix(EntityPlayer p)
  {
    String tname = p.getDisplayName().getFormattedText();
    String name = p.getName();
    if (tname.length() > 1 + name.length()) {
      return tname.substring(0, tname.indexOf(name));
    }
    return "";
  }
}
