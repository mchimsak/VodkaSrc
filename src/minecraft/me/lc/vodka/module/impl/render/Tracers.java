package me.lc.vodka.module.impl.render;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventRender;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.utils.Colors;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.client.renderer.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

public class Tracers extends Module
{
    public Tracers() {
        super("Tracers", 0,Category.RENDER);
    }
    
    @EventTarget
    public void onRender( EventRender event) {
        for (final EntityPlayer player : this.mc.theWorld.playerEntities) {
            if (this.mc.thePlayer != player && !player.isInvisible()) {
                final double posX = player.posX;
                final double posY = player.posY;
                final double posZ = player.posZ;
                this.drawLine(player);
            }
        }
    }
    Runnable r2 = ()-> System.out.println("233333333");

    private void drawLine(final EntityPlayer player) {
        this.mc.getRenderManager();
        final double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * this.mc.timer.renderPartialTicks - RenderManager.renderPosX;
        this.mc.getRenderManager();
        final double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * this.mc.timer.renderPartialTicks - RenderManager.renderPosY;
        this.mc.getRenderManager();
        final double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * this.mc.timer.renderPartialTicks - RenderManager.renderPosZ;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.5f);
        final float DISTANCE = this.mc.thePlayer.getDistanceToEntity((Entity)player);
        if (DISTANCE <= 200.0f) {
            GL11.glColor3f(1.0f, DISTANCE / 40.0f, 0.0f);
        }
        GL11.glLoadIdentity();
        final boolean bobbing = this.mc.gameSettings.viewBobbing;
        this.mc.gameSettings.viewBobbing = false;
        this.mc.entityRenderer.orientCamera(this.mc.timer.renderPartialTicks);
        GL11.glBegin(3);
        GL11.glVertex3d(0.0, (double)this.mc.thePlayer.getEyeHeight(), 0.0);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x, y + player.getEyeHeight(), z);
        GL11.glEnd();
        this.mc.gameSettings.viewBobbing = bobbing;
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
}
