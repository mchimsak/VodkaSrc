package me.lc.vodka.module.impl.combat;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;

import java.util.*;

import me.lc.vodka.setting.Setting;
import org.lwjgl.input.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class AutoClicker extends Module
{
    private Setting delay;
    private Setting random;
    private Random rand;
    private long delayCount;
    Runnable r2 = ()-> System.out.println("233333333");
    public AutoClicker() {
        super("AutoClicker", 0,Category.COMBAT);
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(delay = (new Setting(this,"Delay",100.0D,0.0D,1000.0D,true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(random = (new Setting(this,"Random",true)));
        this.rand = new Random();
}
    
    @EventTarget
    public void onUpdate(EventUpdate event) {
        int delayNeed = ((int)this.delay.getCurrentValue());
        if (this.random.getBooleanValue()) {
            delayNeed += this.rand.nextInt(80) - 40;
        }
        if (System.currentTimeMillis() - this.delayCount >= delayNeed) {
            if (Mouse.isButtonDown(0) && this.mc.currentScreen == null) {
                if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.entityHit != null) {
                    this.mc.thePlayer.swingItem();
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity(this.mc.objectMouseOver.entityHit, C02PacketUseEntity.Action.ATTACK));
                }
                else {
                    this.mc.thePlayer.swingItem();
                }
                this.delayCount = System.currentTimeMillis();
            }
            if (Mouse.isButtonDown(1) && this.mc.currentScreen == null) {
                this.mc.rightClickMouse();
                this.delayCount = System.currentTimeMillis();
            }
        }
    }
}
