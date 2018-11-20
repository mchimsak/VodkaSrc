package me.lc.vodka.utils.util;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.vector.Vector3f;

public class PlayerUtil
{
  private static Minecraft mc =Minecraft.getMinecraft() ;
  Runnable r2 = ()-> System.out.println("233333333");


  public static float getDirection()
  {
    float yaw = mc.thePlayer.rotationYaw;
    if (mc.thePlayer.moveForward < 0.0F) {
      yaw += 180.0F;
    }
    float forward = 1.0F;
    if (mc.thePlayer.moveForward < 0.0F) {
      forward = -0.5F;
    } else if (mc.thePlayer.moveForward > 0.0F) {
      forward = 0.5F;
    }
    if (mc.thePlayer.moveStrafing > 0.0F) {
      yaw -= 90.0F * forward;
    }
    if (mc.thePlayer.moveStrafing < 0.0F) {
      yaw += 90.0F * forward;
    }
    yaw *= 0.017453292F;
    return yaw;
  }
  
  public static boolean isInWater()
  {
    return mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)).getBlock().getMaterial() == Material.water;
  }
  
  public static void toFwd(double speed)
  {
    float yaw = mc.thePlayer.rotationYaw * 0.017453292F;
    mc.thePlayer.motionX -= MathHelper.sin(yaw) * speed;
    mc.thePlayer.motionZ += MathHelper.cos(yaw) * speed;
  }
  
  public static void setSpeed(double speed)
  {
    mc.thePlayer.motionX = (-(Math.sin(getDirection()) * speed));
    mc.thePlayer.motionZ = (Math.cos(getDirection()) * speed);
  }
  
  public static double getSpeed()
  {
    return Math.sqrt(Minecraft.getMinecraft().thePlayer.motionX * Minecraft.getMinecraft().thePlayer.motionX + Minecraft.getMinecraft().thePlayer.motionZ * Minecraft.getMinecraft().thePlayer.motionZ);
  }
  
  public static Block getBlockUnderPlayer(EntityPlayer inPlayer)
  {
    return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - 1.0D, inPlayer.posZ));
  }
  
  public static Block getBlock(BlockPos pos)
  {
    return Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
  }
  
  public static Block getBlockAtPosC(EntityPlayer inPlayer, double x, double y, double z)
  {
    return getBlock(new BlockPos(inPlayer.posX - x, inPlayer.posY - y, inPlayer.posZ - z));
  }
  
  public static ArrayList<Vector3f> vanillaTeleportPositions(double tpX, double tpY, double tpZ, double speed)
  {
    ArrayList<Vector3f> positions = new ArrayList();
    Minecraft mc = Minecraft.getMinecraft();
    double posX = tpX - mc.thePlayer.posX;
    double posY = tpY - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight() + 1.1D);
    double posZ = tpZ - mc.thePlayer.posZ;
    float yaw = (float)(Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D - 90.0D);
    float pitch = (float)(-Math.atan2(posY, Math.sqrt(posX * posX + posZ * posZ)) * 180.0D / 3.141592653589793D);
    double tmpX = mc.thePlayer.posX;
    double tmpY = mc.thePlayer.posY;
    double tmpZ = mc.thePlayer.posZ;
    double steps = 1.0D;
    for (double d = speed; d < getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, tpX, tpY, tpZ); d += speed) {
      steps += 1.0D;
    }
    for (double d = speed; d < getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, tpX, tpY, tpZ); d += speed)
    {
      tmpX = mc.thePlayer.posX - Math.sin(getDirection(yaw)) * d;
      tmpZ = mc.thePlayer.posZ + Math.cos(getDirection(yaw)) * d;
      tmpY -= (mc.thePlayer.posY - tpY) / steps;
      positions.add(new Vector3f((float)tmpX, (float)tmpY, (float)tmpZ));
    }
    positions.add(new Vector3f((float)tpX, (float)tpY, (float)tpZ));
    
    return positions;
  }
  
  public static float getDirection(float yaw)
  {
    if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0F) {
      yaw += 180.0F;
    }
    float forward = 1.0F;
    if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0F) {
      forward = -0.5F;
    } else if (Minecraft.getMinecraft().thePlayer.moveForward > 0.0F) {
      forward = 0.5F;
    }
    if (Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0F) {
      yaw -= 90.0F * forward;
    }
    if (Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0F) {
      yaw += 90.0F * forward;
    }
    yaw *= 0.017453292F;
    
    return yaw;
  }
  
  public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2)
  {
    double d0 = x1 - x2;
    double d1 = y1 - y2;
    double d2 = z1 - z2;
    return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
  }
  
  public static boolean MovementInput()
  {
    return (mc.gameSettings.keyBindForward.pressed) || (mc.gameSettings.keyBindLeft.pressed) || (mc.gameSettings.keyBindRight.pressed) || (mc.gameSettings.keyBindBack.pressed);
  }
}
