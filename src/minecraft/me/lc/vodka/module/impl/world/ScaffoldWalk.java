package me.lc.vodka.module.impl.world;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventMotion;
import me.lc.vodka.event.events.EventRender3D;
import me.lc.vodka.helper.TimeHelper;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.utils.util.CombatUtil;
import me.lc.vodka.utils.util.PlayerUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class ScaffoldWalk
  extends Module
{
  private BlockData blockData;
  private TimeHelper time = new TimeHelper();
  private TimeHelper delay = new TimeHelper();
  private Setting noSwing;
  private Setting silent;
  private Setting delayValue;
  public static Setting mode;
  Runnable r2 = ()-> System.out.println("233333333");

  private double olddelay;
  public static ArrayList<String> options = new ArrayList<>();
  public ScaffoldWalk()
  {

    super("ScaffoldWalk", 0,Category.WORLD);
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(noSwing = (new Setting(this,"NoSwing",false)));
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(silent = (new Setting(this,"Silent",false)));
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(delayValue = (new Setting(this,"Delay",250.0D,40.0D,1000.0D,true)));
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(mode = (new Setting(this,"MOD","Normal",options)));

    options.add(0,"Normal");
    options.add(1,"AAC");
    options.add(2,"CubeCraft");
    options.add(3,"Unlegit");
  }
  @EventTarget
  public void onPre(EventMotion event)
  {
    if (this.mc.thePlayer == null) {
      return;
    }
    this.blockData = getBlockData(new BlockPos(this.mc.thePlayer).add(0.0D, -0.75D, 0.0D), 1);
    int block = getBlockItem();
    Item item = this.mc.thePlayer.inventory.getStackInSlot(block).getItem();
    if ((block != -1) && (item != null) && ((item instanceof ItemBlock)))
    {
      if (this.silent.getBooleanValue()) {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(block));
      }
      if ((mode.getCurrentOptionIndex() == 1))
      {
        if ((mode.getCurrentOptionIndex() == 1) && (this.mc.gameSettings.keyBindSprint.pressed) && (PlayerUtil.MovementInput())) {
          PlayerUtil.setSpeed(0.002D);
        }
      }
      if (mode.getCurrentOptionIndex() == 1) {
        event.pitch = 81.0F;
      }
      if ((mode.getCurrentOptionIndex() == 2) && (PlayerUtil.MovementInput())) {
        PlayerUtil.setSpeed(0.04D);
      }
    }
    Random r = new Random();
    if ((this.blockData != null) && (block != -1) && (item != null) && ((item instanceof ItemBlock)))
    {
      Vec3 pos = getBlockSide(this.blockData.position, this.blockData.face);
      float[] rot = CombatUtil.getRotationsNeededBlock(pos.xCoord, pos.yCoord, pos.zCoord);
      float[] rots = CombatUtil.getDirectionToBlock(pos.xCoord, pos.yCoord, pos.zCoord, this.blockData.face);
      if (mode.getCurrentOptionIndex() == 3) {
        event.pitch = rots[1];
      } else {
        event.pitch = rot[1];
      }
      if (mode.getCurrentOptionIndex() == 2)
      {
        if (this.mc.gameSettings.keyBindForward.pressed) {
          event.yaw = (this.mc.thePlayer.rotationYaw >= 180.0F ? this.mc.thePlayer.rotationYaw - 180.0F : this.mc.thePlayer.rotationYaw + 180.0F);
        } else if (this.mc.gameSettings.keyBindBack.pressed) {
          event.yaw = this.mc.thePlayer.rotationYaw;
        } else if (this.mc.gameSettings.keyBindLeft.pressed) {
          event.yaw = (this.mc.thePlayer.rotationYaw + 90.0F);
        } else if (this.mc.gameSettings.keyBindRight.pressed) {
          event.yaw = (this.mc.thePlayer.rotationYaw - 90.0F);
        }
        this.mc.thePlayer.rotationYawHead = event.yaw;
      }
      else if (mode.getCurrentOptionIndex() == 3)
      {
        event.yaw = rots[0];
      }
      else
      {
        event.yaw = rot[0];
      }
      this.mc.thePlayer.rotationYawHead = event.yaw;
    }
  }
  
  private double getDoubleRandom(double min, double max)
  {
    return ThreadLocalRandom.current().nextDouble(min, max);
  }

  
  @EventTarget
  public void onSafe(EventMotion event)
  {
//    event.setSafe(true);
    if (this.mc.thePlayer == null) {
      return;
    }
    if (this.blockData != null)
    {
      int block = getBlockItem();
      Random rand = new Random();
      Item item = this.mc.thePlayer.inventory.getStackInSlot(block).getItem();
      if ((block != -1) && (item != null) && ((item instanceof ItemBlock)))
      {
        Vec3 hitVec = new Vec3(this.blockData.position).addVector(0.5D, 0.5D, 0.5D).add(new Vec3(this.blockData.face.getDirectionVec()).scale(0.5D));
        if (this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getStackInSlot(block), this.blockData.position, this.blockData.face, hitVec))
        {
          this.delay.reset();
          this.blockData = null;
          this.time.reset();
          if (this.noSwing.getBooleanValue())
          {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation()); return;
          }
          this.mc.thePlayer.swingItem();
          
          return;
        }
        if (mode.getCurrentOptionIndex() == 2)
        {
          if (this.delay.isDelayComplete(((Double)this.delayValue.getCurrentValue()).intValue() + rand.nextInt(50))) {
            if (this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getStackInSlot(block), this.blockData.position, this.blockData.face, hitVec))
            {
              this.delay.reset();
              this.blockData = null;
              this.time.reset();
              if (this.noSwing.getBooleanValue())
              {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation()); return;
              }
              this.mc.thePlayer.swingItem();
              return;
            }
          }
          if ((this.delay.isDelayComplete(((Double)this.delayValue.getCurrentValue()).longValue())) &&
            (mode.getCurrentOptionIndex() == 0))
          {
            if (this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getStackInSlot(block), this.blockData.position, this.blockData.face, hitVec))
            {
              this.delay.reset();
              this.blockData = null;
              if (this.noSwing.getBooleanValue()) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
              } else {
                this.mc.thePlayer.swingItem();
              }
            }
            this.delay.reset();
          }
        }
      }
    }
  }
  
  private boolean canPlace(EntityPlayerSP player, WorldClient worldIn, ItemStack heldStack, BlockPos hitPos, EnumFacing side, Vec3 vec3)
  {
    if ((heldStack.getItem() instanceof ItemBlock)) {
      return ((ItemBlock)heldStack.getItem()).canPlaceBlockOnSide(worldIn, hitPos, side, player, heldStack);
    }
    return false;
  }
  
  private void sendCurrentItem()
  {
    this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
  }
  
  private int getBlockItem()
  {
    int block = -1;
    for (int i = 8; i >= 0; i--) {
      if ((this.mc.thePlayer.inventory.getStackInSlot(i) != null) && 
        ((this.mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock)) && (
        (this.mc.thePlayer.getHeldItem() == this.mc.thePlayer.inventory.getStackInSlot(i)) || 
        (this.noSwing.getBooleanValue()))) {
        block = i;
      }
    }
    return block;
  }
  
  public BlockData getBlockData(BlockPos pos, int i)
  {
    return 
    
      this.mc.theWorld.getBlockState(pos.add(0, -i, 0)).getBlock() != Blocks.air ? 
      new BlockData(pos.add(0, -i, 0), EnumFacing.UP) : this.mc.theWorld.getBlockState(pos.add(-i, 0, 0)).getBlock() != Blocks.air ? new BlockData(pos.add(-i, 0, 0), EnumFacing.EAST) : this.mc.theWorld.getBlockState(pos.add(i, 0, 0)).getBlock() != Blocks.air ? new BlockData(pos.add(i, 0, 0), EnumFacing.WEST) : this.mc.theWorld.getBlockState(pos.add(0, 0, -i)).getBlock() != Blocks.air ? new BlockData(pos.add(0, 0, -i), EnumFacing.SOUTH) : this.mc.theWorld.getBlockState(pos.add(0, 0, i)).getBlock() != Blocks.air ? new BlockData(pos.add(0, 0, i), EnumFacing.NORTH) : 
      null;
  }
  
  public Vec3 getBlockSide(BlockPos pos, EnumFacing face)
  {
    if (face == EnumFacing.NORTH) {
      return new Vec3(pos.getX(), pos.getY(), pos.getZ() - 0.5D);
    }
    if (face == EnumFacing.EAST) {
      return new Vec3(pos.getX() + 0.5D, pos.getY(), pos.getZ());
    }
    if (face == EnumFacing.SOUTH) {
      return new Vec3(pos.getX(), pos.getY(), pos.getZ() + 0.5D);
    }
    if (face == EnumFacing.WEST) {
      return new Vec3(pos.getX() - 0.5D, pos.getY(), pos.getZ());
    }
    return new Vec3(pos.getX(), pos.getY(), pos.getZ());
  }
  
  public class BlockData
  {
    public BlockPos position;
    public EnumFacing face;
    
    public BlockData(BlockPos position, EnumFacing face)
    {
      this.position = position;
      this.face = face;
    }
  }
  
  public void onEnable()
  {
    super.onEnable();
  }
  
  public void onDisable()
  {
    super.onDisable();
    sendCurrentItem();
    this.mc.gameSettings.keyBindSneak.pressed = false;
    this.mc.timer.timerSpeed = 1.0F;
  }
}