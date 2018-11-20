package me.lc.vodka.module.impl.render;

import me.lc.vodka.event.EventManager;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.*;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.utils.Colors;
import me.lc.vodka.utils.util.PlayerUtil;
import me.lc.vodka.utils.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.command.BasicCommand;

import java.awt.*;

public class Freecam
  extends Module
{
  private double posX;
  private double posY;
  private double posZ;
  private float rotYaw;
  private float rotPitch;

  public Freecam()
  {
    super("Freecam", 0,Category.RENDER);
  }

  public void onEnable()
  {
    if (this.mc.theWorld != null)
    {
      this.posX = this.mc.thePlayer.posX;
      this.posY = this.mc.thePlayer.posY;
      this.posZ = this.mc.thePlayer.posZ;
      this.rotYaw = this.mc.thePlayer.rotationYaw;
      this.rotPitch = this.mc.thePlayer.rotationPitch;
      this.mc.thePlayer.noClip = true;
    }
    super.onEnable();
  }

  @EventTarget
  public void a(EventRender3D e) {
    double var10000 = this.mc.thePlayer.posX;
    Minecraft.getMinecraft().getRenderManager();
    double x = var10000 - this.mc.thePlayer.posX;
    var10000 = this.mc.thePlayer.posY - 0.5D;
    Minecraft.getMinecraft().getRenderManager();
    double y = var10000 - this.mc.thePlayer.posY;
    var10000 = this.mc.thePlayer.posZ;
    Minecraft.getMinecraft().getRenderManager();
    double z = var10000 - this.mc.thePlayer.posZ;
      Color color = new Color(Colors.BLUE.c);
      x -= 0.5;
      z -= 0.5;
      final double mid = 0.5;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glTranslated(x + mid, y + mid, z + mid);
      GL11.glTranslated(-(x + mid), -(y + mid), -(z + mid));
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
      GL11.glLineWidth(2.0f);
      RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 0.05, z + 1.0));
      GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.5f);
      RenderUtil.drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 0.05, z + 1.0));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();

  }
  
  public void onDisable()
  {
    this.mc.thePlayer.motionX = (this.mc.thePlayer.motionZ = 0.0D);
    this.mc.thePlayer.noClip = false;
    this.mc.thePlayer.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotYaw, this.rotPitch);
    super.onDisable();
  }
  
  @EventTarget
  public void onMove(EventMove event)
  {
    this.mc.thePlayer.noClip = true;
  }
  
  @EventTarget
  public void onUpdate(EventMotion event)
  {
    this.mc.thePlayer.motionY = 0.0D;
    if ((this.mc.gameSettings.keyBindForward.isKeyDown()) || (this.mc.gameSettings.keyBindLeft.isKeyDown()) || 
      (this.mc.gameSettings.keyBindRight.isKeyDown()) || (this.mc.gameSettings.keyBindBack.isKeyDown())) {
      PlayerUtil.setSpeed(1.0D);
    } else {
      this.mc.thePlayer.motionX = (this.mc.thePlayer.motionZ = 0.0D);
    }
    if (this.mc.gameSettings.keyBindSneak.pressed) {
      this.mc.thePlayer.motionY -= 0.5D;
    } else if (this.mc.gameSettings.keyBindJump.pressed) {
      this.mc.thePlayer.motionY += 0.5D;
    }
  }
  
  @EventTarget
  public void pushOut(EventPushOut event)
  {
    event.cancel = true;
  }
  
  @EventTarget
  public void insideBlock(EventInsideBlock event)
  {
    event.cancel = true;
  }
  
  @EventTarget
  public void onReceive(EventReceivePacket event)
  {
    if (((event.getPacket() instanceof S08PacketPlayerPosLook)) && 
      (Minecraft.canCancle)) {
      event.cancel = true;
    }
  }
  
  @EventTarget
  public void onPacketSend(EventSendPacket event)
  {
    if ((event.getPacket() instanceof C03PacketPlayer))
    {
      C03PacketPlayer packet = (C03PacketPlayer)event.getPacket();
      packet.x = this.posX;
      packet.y = this.posY;
      packet.z = this.posZ;
      packet.yaw = this.rotYaw;
      packet.pitch = this.rotPitch;
    }
  }
}