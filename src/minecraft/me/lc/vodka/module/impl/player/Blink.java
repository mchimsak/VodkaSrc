
package me.lc.vodka.module.impl.player;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventPacketSend;
import net.minecraft.network.Packet;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.helper.TimeHelper;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C03PacketPlayer;

import net.minecraft.network.Packet;
import java.util.ArrayList;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class Blink extends Module
{
    private long deltaTime;
    private long startTime;
    TimeHelper timer;
    private EntityOtherPlayerMP player;
    private ArrayList<Packet> packets;
    
    public Blink() {
        super("Blink", 0,Category.PLAYER);
        this.packets = new ArrayList<Packet>();
    }
    Runnable r2 = ()-> System.out.println("233333333");

    @Override
    public void onEnable() {
        this.deltaTime = 0L;
        this.timer = new TimeHelper();
        (this.player = new EntityOtherPlayerMP(this.mc.theWorld, this.mc.thePlayer.getGameProfile())).clonePlayer(this.mc.thePlayer, true);
        this.player.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
        this.player.rotationYawHead = this.mc.thePlayer.rotationYaw;
        this.player.rotationPitch = this.mc.thePlayer.rotationPitch;
        this.player.setSneaking(this.mc.thePlayer.isSneaking());
        this.mc.theWorld.addEntityToWorld(-1337, this.player);
        this.startTime = this.timer.getLastMs();
        super.onEnable();
    }

    @EventTarget
    public void onPacketSend(EventPacketSend event) {
        if (event.packet instanceof C03PacketPlayer || event.packet instanceof C0BPacketEntityAction || event.packet instanceof C0APacketAnimation || event.packet instanceof C02PacketUseEntity || event.packet instanceof C09PacketHeldItemChange || event.packet instanceof C08PacketPlayerBlockPlacement || event.packet instanceof C07PacketPlayerDigging) {
            event.setCancelled(true);
            this.packets.add(event.packet);
            this.packets.trimToSize();
            this.deltaTime = this.timer.getLastMs() - this.startTime;
        }
    }
    
    @Override
    public void onDisable() {
        this.mc.theWorld.removeEntity(this.player);
        for (final Packet packet : this.packets) {
            this.mc.getNetHandler().getNetworkManager().dispatchPacket(packet, null);
            super.onDisable();
        }
    }
}
