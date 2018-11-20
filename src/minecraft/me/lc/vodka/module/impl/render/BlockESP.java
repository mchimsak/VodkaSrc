package me.lc.vodka.module.impl.render;

import java.util.ArrayList;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventRender;
import me.lc.vodka.event.events.EventRenderBlock;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.utils.util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.BlockPos;

public class BlockESP
  extends Module
{
  private static ArrayList<Integer> blockIds = new ArrayList();
  private ArrayList<BlockPos> toRender = new ArrayList();
  private Setting blockLimit;
  Runnable r2 = ()-> System.out.println("233333333");
  public BlockESP()
  {
    super("BlockESP", 0,Category.RENDER);
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(blockLimit = (new Setting(this,"Block",250.0D,10.0D,1000.0D,true)));
  }
  
  public void onEnable()
  {
    this.mc.renderGlobal.loadRenderers();
    this.toRender.clear();
    super.onEnable();
  }
  
  @EventTarget
  public void onRenderBlock(EventRenderBlock event)
  {
    BlockPos pos = new BlockPos(event.getX(), event.getY(), event.getZ());
    if ((this.toRender.size() < ((Double)this.blockLimit.getCurrentValue()).doubleValue()) && (!this.toRender.contains(pos)) && (blockIds.contains(new Integer(Block.getIdFromBlock(event.getBlock()))))) {
      this.toRender.add(pos);
    }
    for (int i = 0; i < this.toRender.size(); i++)
    {
      BlockPos pos_1 = (BlockPos)this.toRender.get(i);
      int id = Block.getIdFromBlock(this.mc.theWorld.getBlockState(pos_1).getBlock());
      if (!blockIds.contains(Integer.valueOf(id))) {
        this.toRender.remove(i);
      }
    }
  }
  
  @EventTarget
  public void onRender(EventRender event)
  {
    for (BlockPos pos : this.toRender) {
      renderBlock(pos);
    }
  }
  
  private void renderBlock(BlockPos pos)
  {
    this.mc.getRenderManager();
    double x = pos.getX() - RenderManager.renderPosX;
    this.mc.getRenderManager();
    double y = pos.getY() - RenderManager.renderPosY;
    this.mc.getRenderManager();
    double z = pos.getZ() - RenderManager.renderPosZ;
    RenderUtil.drawSolidBlockESP(x, y, z, 0.0F, 0.5F, 1.0F, 0.25F);
  }
  
  public static ArrayList<Integer> getBlockIds()
  {
    return blockIds;
  }
}
