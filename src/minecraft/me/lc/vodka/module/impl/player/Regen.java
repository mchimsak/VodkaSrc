package me.lc.vodka.module.impl.player;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import net.minecraft.network.play.client.C03PacketPlayer;
public class Regen
extends Module {

    public Setting packet;

    public Regen() {
        super("Regen", 0,Category.PLAYER);
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(packet = (new Setting(this,"Packets",10.0D,1.0D,20.0D,true)));
    }

    @EventTarget
    public void onMotion(EventUpdate event) {
        if (this.mc.thePlayer.getHealth() < 20.0f && this.mc.thePlayer.getFoodStats().getFoodLevel() >= 19) {
            int i = 0;
            while ((double)i < this.packet.getCurrentValue()) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                ++i;
            }
        }
    }
}

