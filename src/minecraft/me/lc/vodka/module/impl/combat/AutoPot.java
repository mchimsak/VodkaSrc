package me.lc.vodka.module.impl.combat;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventMotion;
import me.lc.vodka.helper.TimeHelper;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;

public class AutoPot extends Module
{
    private Setting delay;
    private Setting health;
    private Setting throwInInv;
    private TimeHelper timer;
    private TimeHelper throwTimer;
    private boolean nextTick;
    Runnable r2 = ()-> System.out.println("233333333");
    public AutoPot() {
        super("AutoPot", 0,Category.COMBAT);
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(delay = (new Setting(this,"Delay",50.0D,0.0D,1000.0D,true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(health = (new Setting(this,"Health",6.0D,1.0D,10.0D,true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(throwInInv = (new Setting(this,"OnlyInv",true)));
        this.timer = new TimeHelper();
        this.throwTimer = new TimeHelper();
        this.nextTick = false;
    }
    
    @EventTarget
    public void onPre(EventMotion event) {
        if (this.mc.thePlayer.getHealth() / 2.0f <= (double)this.health.getCurrentValue()) {
            event.pitch = 90.0f;
            this.getPotion();
            this.nextTick = true;
        }
        if (this.nextTick) {
            event.pitch = 90.0f;
            this.throwPotion();
            this.nextTick = false;
        }
    }
    
    private void getPotion() {
        int slotId = this.getFreeSlot();
        if (slotId != -1) {
            for (int id = 9; id <= 35; ++id) {
                final Slot currentSlot = this.mc.thePlayer.inventoryContainer.getSlot(id);
                if (currentSlot.getHasStack()) {
                    final ItemStack currentItem = currentSlot.getStack();
                    if (currentItem.getItem() instanceof ItemPotion && this.isSplashPotion(currentItem) && this.timer.isDelayComplete((long)((Double)this.delay.getCurrentValue()).intValue())) {
                        this.mc.playerController.windowClick(0, id, 0, 1, (EntityPlayer)this.mc.thePlayer);
                        slotId = this.getFreeSlot();
                        this.timer.reset();
                    }
                }
            }
        }
    }
    
    private void throwPotion() {
        final int slotId = this.getFreeSlot();
        if (slotId != -1) {
            for (int id = 36; id <= 44; ++id) {
                final Slot currentSlot = this.mc.thePlayer.inventoryContainer.getSlot(id);
                if (currentSlot.getHasStack()) {
                    final ItemStack currentItem = currentSlot.getStack();
                    if (currentItem.getItem() instanceof ItemPotion && this.isSplashPotion(currentItem) && this.throwTimer.isDelayComplete((long)((Double)this.delay.getCurrentValue()).intValue())) {
                        final int old = this.mc.thePlayer.inventory.currentItem;
                        this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(id - 36));
                        this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), -1, this.mc.thePlayer.inventoryContainer.getSlot(id).getStack(), 0.0f, 0.0f, 0.0f));
                        this.mc.thePlayer.inventory.currentItem = id - 36;
                        this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
                        this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(old));
                        this.mc.thePlayer.inventory.currentItem = old;
                        this.throwTimer.reset();
                    }
                }
            }
        }
    }
    
    private boolean isSplashPotion(final ItemStack itemStack) {
        return ItemPotion.isSplash(itemStack.getMetadata());
    }
    
    private int getFreeSlot() {
        for (int id = 36; id < 45; ++id) {
            final Slot currentSlot = this.mc.thePlayer.inventoryContainer.getSlot(id);
            if (!currentSlot.getHasStack()) {
                return 1;
            }
        }
        return -1;
    }
}
