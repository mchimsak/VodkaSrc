package me.lc.vodka.module.impl.player;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventMotion;
import me.lc.vodka.event.events.EventSendPacket;
import me.lc.vodka.event.events.EventTick;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Crasher extends Module
{
    private boolean speedTick;
    private double yVal;
    double health;
    boolean hasDamaged;
    boolean hasJumped;
    double posY;
    
    public Crasher() {
        super("Crasher", 0,Category.PLAYER);
        this.hasDamaged = false;
        this.hasJumped = false;
        this.posY = 0.0;
    }
    Runnable r2 = ()-> System.out.println("233333333");

    @EventTarget
    public void onUpdatePreMotion(final EventUpdate event) {
        if (this.hasJumped) {
            this.mc.thePlayer.motionY = 0.0;
        }
        for (int i = 0; i < 50; ++i) {
            if (!(this.hasDamaged = ((int)this.mc.thePlayer.getHealth() < this.health))) {
                this.damage();
            }
            this.health = this.mc.thePlayer.getHealth();
            if (!this.hasJumped) {
                this.hasJumped = true;
                this.mc.thePlayer.motionY = 0.3;
            }
        }
    }
    
    @EventTarget
    public void onTick(final EventTick event) {
        if (this.mc.thePlayer.onGround) {
            this.speedTick = false;
        }
    }
    
    @EventTarget
    public void onSendPacket(final EventSendPacket event) {
    }
    
    @EventTarget
    public void onPreMovePlayer(final EventMotion event) {
        if (!this.speedTick) {
            return;
        }
        event.y *= 1.0E-13;
        final double movementMultiplier = this.mc.thePlayer.capabilities.getFlySpeed() * 20.0;
        this.mc.thePlayer.fallDistance = 0.0f;
        this.mc.thePlayer.onGround = true;
    }
    
    private void damage() {
        for (int i = 0; i < 70; ++i) {
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.06, this.mc.thePlayer.posZ, false));
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
        }
        this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.1, this.mc.thePlayer.posZ, false));
    }
    
    public void onEnable() {
        this.posY = this.mc.thePlayer.posY;
        for (int i = 0; i < 70; ++i) {
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.06, this.mc.thePlayer.posZ, false));
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
        }
        this.mc.thePlayer.motionY = 0.4;
        this.hasJumped = false;
    }
    
    public void onDisable() {
        this.mc.thePlayer.capabilities.allowFlying = false;
    }
}
