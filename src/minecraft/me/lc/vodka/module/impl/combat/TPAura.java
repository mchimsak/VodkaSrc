package me.lc.vodka.module.impl.combat;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventMotion;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.utils.util.CombatUtil;
import me.lc.vodka.utils.util.PlayerUtil;
import net.minecraft.entity.player.*;
import org.lwjgl.input.*;
import net.minecraft.entity.*;
import org.lwjgl.util.vector.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;

import java.util.*;

public class TPAura extends Module
{
    private EntityLivingBase entity;
    private boolean hit;
    
    public TPAura() {
        super("TPAura", 0,Category.COMBAT);
    }
    Runnable r2 = ()-> System.out.println("233333333");
    @EventTarget
    public void onPreMotion(EventMotion event) {
        if (!this.hit) {
            this.entity = null;
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.entityHit != null && this.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
                this.entity = (EntityLivingBase)this.mc.objectMouseOver.entityHit;
            }
            else {
                this.setTarget();
            }
        }
        if (this.entity != null && Mouse.isButtonDown(0)) {
            if (!this.hit) {
                this.hit = true;
                return;
            }
            if (this.hit && this.mc.thePlayer.onGround) {
                this.mc.thePlayer.jump();
            }
            if (this.mc.thePlayer.fallDistance > 0.0f) {
                event.yaw = CombatUtil.getRotations((Entity)this.entity)[0];
                event.pitch = CombatUtil.getRotations((Entity)this.entity)[1];
                if (this.mc.thePlayer.getDistanceToEntity((Entity)this.entity) > 3.5) {
                    final Vec3 vec = this.mc.thePlayer.getVectorForRotation(0.0f, this.mc.thePlayer.rotationYaw);
                    final double x = this.mc.thePlayer.posX + vec.xCoord * (this.mc.thePlayer.getDistanceToEntity((Entity)this.entity) - 1.0f);
                    final double z = this.mc.thePlayer.posZ + vec.zCoord * (this.mc.thePlayer.getDistanceToEntity((Entity)this.entity) - 1.0f);
                    final double y = this.entity.getPosition().getY() + 0.25;
                    final ArrayList<Vector3f> positions = (ArrayList<Vector3f>)PlayerUtil.vanillaTeleportPositions(x, y + 1.0, z, 4.0);
                    for (int j = 0; j < 1; ++j) {
                        for (int i = 0; i < positions.size(); ++i) {
                            final Vector3f pos = positions.get(i);
                            final Vector3f oldPos = (i == 0) ? new Vector3f((float)this.mc.thePlayer.posX, (float)this.mc.thePlayer.posY, (float)this.mc.thePlayer.posZ) : positions.get(i - 1);
                            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), false));
                        }
                    }
                    this.mc.thePlayer.onCriticalHit((Entity)this.entity);
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity((Entity)this.entity, C02PacketUseEntity.Action.ATTACK));
                    this.hit = false;
                }
            }
        }
    }
    
    private void setTarget() {
        double closest = 2.147483647E9;
        EntityLivingBase target = null;
        for (final Object o : this.mc.theWorld.loadedEntityList) {
            if (o instanceof EntityLivingBase) {
                final EntityLivingBase e = (EntityLivingBase)o;
                if (!this.isValidTarget(e)) {
                    continue;
                }
                final double dist = this.mc.thePlayer.getDistanceToEntity((Entity)e);
                if (dist >= closest) {
                    continue;
                }
                closest = dist;
                target = e;
            }
        }
        this.entity = target;
    }
    
    private boolean isValidTarget(final EntityLivingBase entity) {
        return entity != this.mc.thePlayer && !entity.isInvisible() && entity instanceof EntityPlayer;
    }
}
