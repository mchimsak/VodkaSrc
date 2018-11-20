package me.lc.vodka.module.impl.render;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventMotion;
import me.lc.vodka.event.events.EventRender;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.utils.util.ClientUtil;
import me.lc.vodka.utils.util.FlatColors;
import me.lc.vodka.utils.util.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.*;
import net.minecraft.client.renderer.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;

public class Trajectories extends Module
{
    public static float yaw;
    public static float pitch;
    private boolean bow;
    private boolean pot;
    
    public Trajectories() {
        super("Trajectories", 0,Category.RENDER);
        this.bow = false;
        this.pot = false;
    }
    
    @EventTarget
    public void onUpdate(final EventMotion event) {
        Trajectories.yaw = event.yaw;
        Trajectories.pitch = event.pitch;
    }
    Runnable r2 = ()-> System.out.println("233333333");

    @EventTarget
    public void onRender(final EventRender event) {
        if (this.mc.thePlayer.getHeldItem() == null) {
            return;
        }
        if (!(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) && !(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSnowball) && !(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemEnderPearl) && !(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemEgg) && (!(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion) || !ItemPotion.isSplash(this.mc.thePlayer.getHeldItem().getItemDamage()))) {
            return;
        }
        this.bow = (this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow);
        this.pot = (this.mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion);
        final float throwingYaw = Trajectories.yaw;
        final float throwingPitch = Trajectories.pitch;
        double posX = RenderManager.renderPosX - MathHelper.cos(throwingYaw / 180.0f * 3.1415927f) * 0.16f;
        double posY = RenderManager.renderPosY + this.mc.thePlayer.getEyeHeight() - 0.1000000014901161;
        double posZ = RenderManager.renderPosZ - MathHelper.sin(throwingYaw / 180.0f * 3.1415927f) * 0.16f;
        double motionX = -MathHelper.sin(throwingYaw / 180.0f * 3.1415927f) * MathHelper.cos(throwingPitch / 180.0f * 3.1415927f) * (this.bow ? 1.0 : 0.4);
        double motionY = -MathHelper.sin((throwingPitch - (this.pot ? 20 : 0)) / 180.0f * 3.1415927f) * (this.bow ? 1.0 : 0.4);
        double motionZ = MathHelper.cos(throwingYaw / 180.0f * 3.1415927f) * MathHelper.cos(throwingPitch / 180.0f * 3.1415927f) * (this.bow ? 1.0 : 0.4);
        final int var = 72000 - this.mc.thePlayer.getItemInUseCount();
        float power = var / 20.0f;
        power = (power * power + power * 2.0f) / 3.0f;
        if (power < 0.1) {
            return;
        }
        if (power > 1.0f) {
            power = 1.0f;
        }
        final float distance = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= distance;
        motionY /= distance;
        motionZ /= distance;
        motionX *= (this.bow ? (power * 2.0f) : 1.0f) * (this.pot ? 0.5 : 1.5);
        motionY *= (this.bow ? (power * 2.0f) : 1.0f) * (this.pot ? 0.5 : 1.5);
        motionZ *= (this.bow ? (power * 2.0f) : 1.0f) * (this.pot ? 0.5 : 1.5);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200.0f, 0.0f);
        GL11.glDisable(2896);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glPushMatrix();
        GL11.glColor4f(0.20392157f, 0.59607846f, 0.85882354f, 0.5f);
        GL11.glDisable(3553);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(3.0f);
        GL11.glBegin(3);
        boolean hasLanded = false;
        final Entity hitEntity = null;
        MovingObjectPosition landingPosition = null;
        while (!hasLanded && posY > 0.0) {
            final Vec3 present = new Vec3(posX, posY, posZ);
            final Vec3 future = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
            final MovingObjectPosition possibleLandingStrip = this.mc.theWorld.rayTraceBlocks(present, future, false, true, false);
            if (possibleLandingStrip != null) {
                if (possibleLandingStrip.typeOfHit != MovingObjectPosition.MovingObjectType.MISS) {
                    landingPosition = possibleLandingStrip;
                    hasLanded = true;
                }
            }
            else {
                final Entity entityHit = this.getEntityHit(this.bow, present, future);
                if (entityHit != null) {
                    landingPosition = new MovingObjectPosition(entityHit);
                    hasLanded = true;
                }
            }
            posX += motionX;
            posY += motionY;
            posZ += motionZ;
            final float motionAdjustment = 0.99f;
            motionX *= motionAdjustment;
            motionY *= motionAdjustment;
            motionZ *= motionAdjustment;
            motionY -= (this.pot ? 0.05 : (this.bow ? 0.05 : 0.03));
            this.mc.getRenderManager();
            this.mc.getRenderManager();
            this.mc.getRenderManager();
            GL11.glVertex3d(posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ);
        }
        GL11.glEnd();
        GL11.glPushMatrix();
        this.mc.getRenderManager();
        this.mc.getRenderManager();
        this.mc.getRenderManager();
        GL11.glTranslated(posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ);
        if (landingPosition != null && landingPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final int index = landingPosition.sideHit.getIndex();
            if (index == 1) {
                GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
            }
            else if (index == 2) {
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            }
            else if (index == 3) {
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
            }
            else if (index == 4) {
                GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
            }
            else if (index == 5) {
                GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glRotatef(this.mc.thePlayer.rotationYaw, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
            Gui.drawBorderedRect(-0.4f, -0.4f, 0.4f, 0.4f, 0.5f, FlatColors.DARK_BLUE.c, ClientUtil.reAlpha(FlatColors.BLUE.c, 0.5f));
        }
        GL11.glPopMatrix();
        if (landingPosition != null && landingPosition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            GL11.glTranslated(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ);
            GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.8f);
            final AxisAlignedBB bb = landingPosition.entityHit.getEntityBoundingBox();
            RenderUtil.drawBoundingBox(new AxisAlignedBB(bb.minX, bb.maxY, bb.minZ, bb.maxX, bb.maxY + 0.1, bb.maxZ));
            GL11.glColor4f(0.20392157f, 0.59607846f, 0.85882354f, 1.0f);
            GL11.glLineWidth(0.5f);
            GL11.glTranslated(RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ);
        }
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glEnable(3553);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    private ArrayList getEntities() {
        final ArrayList ret = new ArrayList();
        for (final Object e : this.mc.theWorld.loadedEntityList) {
            if (e != this.mc.thePlayer && e instanceof EntityLivingBase) {
                ret.add(e);
            }
        }
        return ret;
    }
    
    private Entity getEntityHit(final boolean bow, final Vec3 vecOrig, final Vec3 vecNew) {
        for (final Object o : this.getEntities()) {
            final EntityLivingBase entity = (EntityLivingBase)o;
            if (entity != this.mc.thePlayer) {
                final float expander = 0.2f;
                final AxisAlignedBB bounding2 = entity.getEntityBoundingBox().expand((double)expander, (double)expander, (double)expander);
                final MovingObjectPosition possibleEntityLanding = bounding2.calculateIntercept(vecOrig, vecNew);
                if (possibleEntityLanding != null) {
                    return (Entity)entity;
                }
                continue;
            }
        }
        return null;
    }
}
