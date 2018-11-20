package me.lc.vodka.module.impl.other;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class GodMode extends Module {

        public GodMode() {
                super("GodMode",0,Category.OTHER);
        }
        Runnable r2 = ()-> System.out.println("233333333");

        @EventTarget
        public void onUpdate(EventUpdate e) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 4.0D, this.mc.thePlayer.posZ, true));
        }
}
