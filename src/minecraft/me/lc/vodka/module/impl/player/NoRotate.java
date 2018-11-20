package me.lc.vodka.module.impl.player;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventMotion;
import me.lc.vodka.event.events.EventPacketTake;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate extends Module
{
    public NoRotate() {
        super("NoRotate", 0,Category.PLAYER);
    }

    @EventTarget
    public void onPacketTake(EventPacketTake event) {
        if (event.packet instanceof S08PacketPlayerPosLook) {
            final S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)event.packet;
        }
    }
    Runnable r2 = ()-> System.out.println("233333333");

    @EventTarget void o(EventMotion e){
        e.yaw = this.mc.thePlayer.rotationYaw;
        e.pitch = this.mc.thePlayer.rotationPitch;
    }
}
