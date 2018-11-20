package me.lc.vodka.module.impl.combat;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.helper.TimeHelper;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class FastBow extends Module
{
    private TimeHelper timer;
    Runnable r2 = ()-> System.out.println("233333333");
    public FastBow() {
        super("FastBow", 0,Category.COMBAT);
        this.timer = new TimeHelper();
    }
    @EventTarget
    public void onPre(final EventUpdate event) {
        if (this.mc.thePlayer.getItemInUse() != null && this.mc.thePlayer.getItemInUse().getItem() instanceof ItemBow && this.mc.thePlayer.getItemInUseDuration() == 5) {
            this.mc.playerController.onStoppedUsingItem((EntityPlayer)this.mc.thePlayer);
        }
        if (this.mc.thePlayer.getItemInUse() != null && this.mc.thePlayer.getItemInUse().getItem() instanceof ItemBow) {
            for (int i = 0; i < 25; ++i) {
                this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer());
            }
        }
    }
}
