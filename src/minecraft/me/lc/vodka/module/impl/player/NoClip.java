package me.lc.vodka.module.impl.player;


import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventMotion;
import me.lc.vodka.event.events.EventPacketSend;
import me.lc.vodka.event.events.EventSendPacket;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.utils.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

public class NoClip
  extends Module
{
  public NoClip()
  {
    super("NoClip", 0,Category.PLAYER);
  }
  Runnable r2 = ()-> System.out.println("233333333");

  @EventTarget
  public void onUpdate(EventMotion event)
  {
    double movementMultiplier = this.mc.thePlayer.capabilities.getFlySpeed() * 50.0D;
    movementMultiplier *= 0.25D;
    this.mc.thePlayer.noClip = true;
    this.mc.gameSettings.thirdPersonView = 0;
    float pitch = 0.0F;
    float yaw = this.mc.thePlayer.rotationYaw;
    float var3 = MathHelper.cos((float)(-yaw * 0.01745329F - 3.141592653589793D));
    float var4 = MathHelper.sin((float)(-yaw * 0.01745329F - 3.141592653589793D));
    float var5 = -MathHelper.cos(-pitch * 0.01745329F);
    float var6 = MathHelper.sin(-pitch * 0.01745329F);
    Vec3 look = new Vec3(var4 * var5, var6, var3 * var5).normalize();
    this.mc.thePlayer.setVelocity(0.0D, 0.0D, 0.0D);
    double xMotion = 0.0D;
    double yMotion = 0.0D;
    double zMotion = 0.0D;
    if ((this.mc.currentScreen == null) || ((Vodka.INSTANCE.MODULE_MANAGER.getModule("InventoryWalk").isToggled()) && ((this.mc.currentScreen instanceof GuiContainer))))
    {
      if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode()))
      {
        xMotion += look.xCoord;
        zMotion += look.zCoord;
      }
      if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode()))
      {
        xMotion += -look.xCoord;
        zMotion += -look.zCoord;
      }
      if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode()))
      {
        pitch = 0.0F;
        yaw = this.mc.thePlayer.rotationYaw + 90.0F;
        var3 = MathHelper.cos((float)(-yaw * 0.01745329F - 3.141592653589793D));
        var4 = MathHelper.sin((float)(-yaw * 0.01745329F - 3.141592653589793D));
        var5 = -MathHelper.cos(-pitch * 0.01745329F);
        var6 = MathHelper.sin(-pitch * 0.01745329F);
        look = new Vec3(var4 * var5, var6, var3 * var5).normalize();
        xMotion += look.xCoord;
        zMotion += look.zCoord;
      }
      if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode()))
      {
        pitch = 0.0F;
        yaw = this.mc.thePlayer.rotationYaw - 90.0F;
        var3 = MathHelper.cos((float)(-yaw * 0.01745329F - 3.141592653589793D));
        var4 = MathHelper.sin((float)(-yaw * 0.01745329F - 3.141592653589793D));
        var5 = -MathHelper.cos(-pitch * 0.01745329F);
        var6 = MathHelper.sin(-pitch * 0.01745329F);
        look = new Vec3(var4 * var5, var6, var3 * var5).normalize();
        xMotion += look.xCoord;
        zMotion += look.zCoord;
      }
      if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode())) {
        yMotion += 1.0D;
      }
      if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindSneak.getKeyCode())) {
        yMotion += -1.0D;
      }
    }
    xMotion *= movementMultiplier;
    yMotion *= movementMultiplier;
    zMotion *= movementMultiplier;
    this.mc.thePlayer.setEntityBoundingBox(this.mc.thePlayer.getEntityBoundingBox().offset(xMotion, yMotion, zMotion));
  }
  
  @EventTarget
  public void onSendPacket(EventSendPacket event)
  {
    if ((event.getPacket() instanceof C03PacketPlayer))
    {
      C03PacketPlayer packet = (C03PacketPlayer)event.getPacket();
      packet.y -= 0.1D;
    }
  }
}