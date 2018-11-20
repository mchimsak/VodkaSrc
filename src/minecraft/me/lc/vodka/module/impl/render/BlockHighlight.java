package me.lc.vodka.module.impl.render;

import java.awt.Color;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventRender;
import me.lc.vodka.event.events.EventRender2D;
import me.lc.vodka.font.Fonts;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.utils.Colors;
import me.lc.vodka.utils.util.ClientUtil;
import me.lc.vodka.utils.util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import org.lwjgl.opengl.GL11;

public class BlockHighlight
  extends Module
{
  private Setting renderString;
  private Setting rb;
  Runnable r2 = ()-> System.out.println("233333333");
  public BlockHighlight()
  {
    super("BlockHighlight", 0,Category.RENDER);
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(renderString = (new Setting(this,"RenderString",true)));
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(rb = (new Setting(this,"Rainbow",false)));


  }
  
  @EventTarget
  public void onRender(EventRender2D event)
  {
    BlockPos pos = this.mc.objectMouseOver.getBlockPos();
    Block block = this.mc.theWorld.getBlockState(pos).getBlock();
    int id = Block.getIdFromBlock(block);
    String s = block.getLocalizedName() + " ID: " + id;
    String s1 = block.getLocalizedName();
    String s2 = " ID: " + id;
    if ((this.mc.objectMouseOver != null) && (this.mc.objectMouseOver.typeOfHit == MovingObjectType.BLOCK) && ((this.renderString.getBooleanValue())))
    {
      ScaledResolution res = new ScaledResolution(this.mc);
      int x = res.getScaledWidth() / 2 + 7;
      int y = res.getScaledHeight() / 2;
      RenderUtil.drawRoundedRect(x, y, x + Fonts.clickgui.getStringWidth(s) + 3, y + Fonts.clickgui.FONT_HEIGHT + 0.5F, 1.0F, ClientUtil.reAlpha(Colors.BLACK.c, 1.0F));
      Fonts.clickgui.drawString(s1, x + 1, y, -1);
      Fonts.clickgui.drawString(s2, x + Fonts.clickgui.getStringWidth(s1) + 1, y, Colors.GREY.c);
    }
  }
  
  @EventTarget
  public void render(EventRender event)
  {
    Color rainbow = Gui.rainbow(System.nanoTime(), 1.0F, 1.0F);
    Color rbc = new Color(rainbow.getRed(), rainbow.getGreen(), rainbow.getBlue(), 1);
    if ((this.mc.objectMouseOver != null) && (this.mc.objectMouseOver.typeOfHit == MovingObjectType.BLOCK))
    {
      BlockPos pos = this.mc.objectMouseOver.getBlockPos();
      Block block = this.mc.theWorld.getBlockState(pos).getBlock();
      String s = block.getLocalizedName();
      this.mc.getRenderManager();
      double x = pos.getX() - RenderManager.renderPosX;
      this.mc.getRenderManager();
      double y = pos.getY() - RenderManager.renderPosY;
      this.mc.getRenderManager();
      double z = pos.getZ() - RenderManager.renderPosZ;
      
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      if ((this.rb.getBooleanValue())) {
        GL11.glColor4f(rbc.getRed(), rbc.getGreen(), rbc.getBlue(), 0.25F);
      } else {
        GL11.glColor4f(0.0F, 0.5F, 1.0F, 0.25F);
      }
      double minX = ((block instanceof BlockStairs)) || (Block.getIdFromBlock(block) == 134) ? 0.0D : block.getBlockBoundsMinX();
      double minY = ((block instanceof BlockStairs)) || (Block.getIdFromBlock(block) == 134) ? 0.0D : block.getBlockBoundsMinY();
      double minZ = ((block instanceof BlockStairs)) || (Block.getIdFromBlock(block) == 134) ? 0.0D : block.getBlockBoundsMinZ();
      RenderUtil.drawBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
      GL11.glColor4f(0.0F, 0.5F, 1.0F, 1.0F);
      GL11.glLineWidth(0.5F);
      RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
    }
  }
}