package me.lc.vodka.module.impl.render;

import java.awt.Color;
import java.util.ArrayList;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventRender3D;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.utils.Colors;
import me.lc.vodka.utils.util.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class ChestESP
  extends Module
{
  private Setting mode;
  private Setting rainbowcol;
  public static ArrayList<String> options = new ArrayList<>();
  Runnable r2 = ()-> System.out.println("233333333");
  public ChestESP()
  {
    super("ChestESP", 0,Category.RENDER);
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(rainbowcol = (new Setting(this,"Rainbow",true)));
    Vodka.INSTANCE.SETTING_MANAGER.addSetting(mode = new Setting(this,"MOD","Box",options));
    options.add(0,"Box");
    options.add(1,"CSGO");
  }
  
  @EventTarget
  public void onRender(EventRender3D event)
  {
    if (this.mode.getCurrentOptionIndex() == 0) {
      for (TileEntity ent : this.mc.theWorld.loadedTileEntityList) {
        if (((ent instanceof TileEntityChest)) || ((ent instanceof TileEntityEnderChest)))
        {
          this.mc.getRenderManager();
          double x = ent.getPos().getX() - RenderManager.renderPosX;
          this.mc.getRenderManager();
          double y = ent.getPos().getY() - RenderManager.renderPosY;
          this.mc.getRenderManager();
          double z = ent.getPos().getZ() - RenderManager.renderPosZ;
          
          GL11.glPushMatrix();
          GL11.glEnable(3042);
          GL11.glBlendFunc(770, 771);
          
          GL11.glDisable(3553);
          GL11.glEnable(2848);
          GL11.glDisable(2929);
          GL11.glDepthMask(false);
          Color rainbow = Gui.rainbow(System.nanoTime(), 1.0F, 1.0F);
          
          GL11.glColor4f((this.rainbowcol.getBooleanValue()) ? rainbow.getRed() / 255.0F : 255, (this.rainbowcol.getBooleanValue()) ? rainbow.getGreen() / 255.0F : 100, (this.rainbowcol.getBooleanValue()) ? rainbow.getBlue() / 255.0F : 97F, 0.25F);
          RenderUtil.drawBoundingBox(new AxisAlignedBB(x + ent.getBlockType().getBlockBoundsMinX(), y + ent.getBlockType().getBlockBoundsMinY(), z + ent.getBlockType().getBlockBoundsMinZ(),
            x + ent.getBlockType().getBlockBoundsMaxX(), y + ent.getBlockType().getBlockBoundsMaxY(), z + ent.getBlockType().getBlockBoundsMaxZ()));
          GL11.glDisable(2848);
          GL11.glEnable(3553);
          
          GL11.glEnable(2929);
          GL11.glDepthMask(true);
          GL11.glDisable(3042);
          GL11.glPopMatrix();
        }
      }
    } else if (this.mode.getCurrentOptionIndex() == 1) {
      for (Object o : this.mc.theWorld.loadedTileEntityList) {
        if ((o instanceof TileEntityChest))
        {
          TileEntityChest chest = (TileEntityChest)o;
          GlStateManager.pushMatrix();
          GlStateManager.translate(chest.getPos().getX() - RenderManager.renderPosX + 0.5D, chest.getPos().getY() - RenderManager.renderPosY + 0.5D, chest.getPos().getZ() - RenderManager.renderPosZ + 0.5D);
          GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
          float SCALE = 0.03F;
          GL11.glScalef(-SCALE, -SCALE, -SCALE);
          GlStateManager.disableDepth();
          GlStateManager.disableLighting();
          Gui.drawRect(-23, -23, 23, -18, Colors.BLACK.c);
          Gui.drawRect(-23, 21, 23, 26, Colors.BLACK.c);
          Gui.drawRect(18, 25, 23, -22, Colors.BLACK.c);
          Gui.drawRect(-18, 25, -23, -22, Colors.BLACK.c);
          Gui.drawRect(-22, -22, 22, -19, Colors.RED.c);
          Gui.drawRect(-22, 22, 22, 25, Colors.RED.c);
          Gui.drawRect(19, 22, 22, -19, Colors.RED.c);
          Gui.drawRect(-19, 22, -22, -19, Colors.RED.c);
          GlStateManager.enableDepth();
          GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
          GlStateManager.popMatrix();
        }
      }
    }
  }
}
