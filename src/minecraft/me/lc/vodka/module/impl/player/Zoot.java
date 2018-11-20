package me.lc.vodka.module.impl.player;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.utils.BlockUtils;
import net.minecraft.potion.PotionEffect;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;


public class Zoot extends Module
{
    public Zoot() {
        super("Zoot", 0,Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (!BlockUtils.isOnLiquid()) {
            if (this.mc.thePlayer.isPotionActive(Potion.blindness.getId())) {
                this.mc.thePlayer.removePotionEffect(Potion.blindness.getId());
            }
            if (this.mc.thePlayer.isPotionActive(Potion.confusion.getId())) {
                this.mc.thePlayer.removePotionEffect(Potion.confusion.getId());
            }
            if (this.mc.thePlayer.isPotionActive(Potion.digSlowdown.getId())) {
                this.mc.thePlayer.removePotionEffect(Potion.digSlowdown.getId());
            }
            if (this.mc.thePlayer.isBurning()) {
                for (int x = 0; x < 100; ++x) {
                    this.mc.getNetHandler().getNetworkManager().dispatchPacket(new C03PacketPlayer(), null);
                }
            }
            Runnable r2 = ()-> System.out.println("233333333");

            Potion[] potionTypes;
            for (int length = (potionTypes = Potion.potionTypes).length, i = 0; i < length; ++i) {
                final Potion potion = potionTypes[i];
                if (potion != null && potion.isBadEffect() && this.mc.thePlayer.isPotionActive(potion)) {
                    final PotionEffect effect = this.mc.thePlayer.getActivePotionEffect(potion);
                    for (int x2 = 0; x2 < effect.getDuration() / 20; ++x2) {
                        this.mc.getNetHandler().getNetworkManager().dispatchPacket(new C03PacketPlayer(), null);
                    }
                }
            }
        }
    }
}
