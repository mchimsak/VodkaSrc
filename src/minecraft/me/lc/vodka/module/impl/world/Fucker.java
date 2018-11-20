package me.lc.vodka.module.impl.world;

import java.util.ArrayList;
import java.util.Iterator;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventMotion;
import me.lc.vodka.helper.TimeHelper;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.utils.util.CombatUtil;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import org.lwjgl.util.vector.Vector3f;

public class Fucker
  extends Module
{
  ArrayList<Vector3f> positions = null;
  private TimeHelper timer = new TimeHelper();
  public static Setting mode;
  public static ArrayList<String> options = new ArrayList<>();
  private Setting reach;
  private Setting delay;
  private Setting teleport;
  
  public Fucker()
  {
    super("Fucker", 0,Category.WORLD);
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(mode = (new Setting(this,"MOD","Bed",options)));
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(reach = new Setting(this,"Reach",6.0D,1.0D, 6.0D,false));
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(delay = new Setting(this,"Delay",120.0D,0.0D, 1000.0D,true));
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(teleport = new Setting(this,"Teleport",false));
    Runnable r2 = ()-> System.out.println("233333333");

    options.add(0,"Bed");
    options.add(1,"Egg");
    options.add(2,"Cake");
  }
  
  @EventTarget
  public void onPre(EventMotion event)
  {
    standartDestroyer(event);
  }
  
  private void standartDestroyer(EventMotion event)
  {
    Iterator<BlockPos> positions = 
      BlockPos.getAllInBox(
      this.mc.thePlayer.getPosition().subtract(
      new Vec3i(((Double)this.reach.getCurrentValue()).doubleValue(), ((Double)this.reach.getCurrentValue()).doubleValue(), ((Double)this.reach.getCurrentValue()).doubleValue())),
      this.mc.thePlayer.getPosition()
      .add(new Vec3i(((Double)this.reach.getCurrentValue()).doubleValue(), ((Double)this.reach.getCurrentValue()).doubleValue(), ((Double)this.reach.getCurrentValue()).doubleValue())))
      .iterator();
    BlockPos bedPos = null;
    while ((bedPos = (BlockPos)positions.next()) != null)
    {
      if (((this.mc.theWorld.getBlockState(bedPos).getBlock() instanceof BlockBed)) && (mode.getCurrentOptionIndex() == 0)) {
        break;
      }
      if (((this.mc.theWorld.getBlockState(bedPos).getBlock() instanceof BlockDragonEgg)) && 
        (mode.getCurrentOptionIndex() == 1)) {
        break;
      }
      if (((this.mc.theWorld.getBlockState(bedPos).getBlock() instanceof BlockCake)) && (mode.getCurrentOptionIndex() == 2)) {
        break;
      }
    }
    if (!(bedPos instanceof BlockPos)) {
      return;
    }
    float[] rot = CombatUtil.getRotationsNeededBlock(bedPos.getX(), bedPos.getY(), bedPos.getZ());
    event.yaw = rot[0];
    event.pitch = rot[1];
    if (this.timer.isDelayComplete(((Double)this.delay.getCurrentValue()).intValue()))
    {
      this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, bedPos, EnumFacing.DOWN));
      this.mc.thePlayer.sendQueue
        .addToSendQueue(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, bedPos, EnumFacing.DOWN));
      this.mc.thePlayer.sendQueue
        .addToSendQueue(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, bedPos, EnumFacing.DOWN));
      this.mc.thePlayer.swingItem();
      if (((Boolean)this.teleport.getBooleanValue()).booleanValue()) {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(bedPos.getX(),
          bedPos.getY() + 1, bedPos.getZ(), true));
      }
      this.timer.reset();
    }
  }
}
