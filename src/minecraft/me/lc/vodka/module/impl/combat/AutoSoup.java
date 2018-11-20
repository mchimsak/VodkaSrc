package me.lc.vodka.module.impl.combat;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.helper.TimeHelper;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.item.*;

public class AutoSoup extends Module
{
    private Setting delay;
    private Setting health;
    private Setting throwSoup;
    private Setting throwInInv;
    private TimeHelper timer;
    private TimeHelper useTimer;
    private TimeHelper throwTimer;
    Runnable r2 = ()-> System.out.println("233333333");
    public AutoSoup() {
        super("AutoSoup", 0,Category.COMBAT);
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(delay = (new Setting(this,"Delay",50.0D,0.0D,1000.0D,true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(health = (new Setting(this,"Health",6.0D,1.0D,10.0D,true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(throwSoup = (new Setting(this,"Throw",true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(throwInInv = (new Setting(this,"OnlyInv",true)));
        this.timer = new TimeHelper();
        this.useTimer = new TimeHelper();
        this.throwTimer = new TimeHelper();
    }
    
    @EventTarget
    public void onPre(EventUpdate event) {
        if (this.throwSoup.getBooleanValue()) {
            this.throwSoups();
        }
        if (this.mc.thePlayer.getHealth() / 2.0f <= (double)this.health.getCurrentValue()) {
            this.useSoups();
        }
        this.getSoups();
    }
    
    private void useSoups() {
        final int soupId = this.getNextSoup();
        if (soupId != -1 && this.useTimer.isDelayComplete(((Double)this.delay.getCurrentValue()).longValue())) {
            final int old = this.mc.thePlayer.inventory.currentItem;
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(soupId - 36));
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), -1, this.mc.thePlayer.inventoryContainer.getSlot(soupId).getStack(), 0.0f, 0.0f, 0.0f));
            this.mc.thePlayer.inventory.currentItem = soupId - 36;
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(old));
            this.mc.thePlayer.inventory.currentItem = old;
            this.useTimer.reset();
        }
    }
    
    private void getSoups() {
        int slotId = this.getFreeSlot();
        if (slotId != -1) {
            for (int id = 9; id <= 35; ++id) {
                final Slot currentSlot = this.mc.thePlayer.inventoryContainer.getSlot(id);
                if (currentSlot.getHasStack()) {
                    final ItemStack currentItem = currentSlot.getStack();
                    if (currentItem.getItem() instanceof ItemSoup && this.timer.isDelayComplete((long)((Double)this.delay.getCurrentValue()).intValue())) {
                        this.mc.playerController.windowClick(0, id, 0, 1, (EntityPlayer)this.mc.thePlayer);
                        slotId = this.getFreeSlot();
                        this.timer.reset();
                    }
                }
            }
        }
    }
    
    private void throwSoups() {
        if (this.mc.currentScreen instanceof GuiInventory || !(boolean)this.throwInInv.getBooleanValue()) {
            for (int id = 9; id < 45; ++id) {
                final Slot currentSlot = this.mc.thePlayer.inventoryContainer.getSlot(id);
                if (currentSlot.getHasStack()) {
                    final ItemStack currentItem = currentSlot.getStack();
                    if (Item.getIdFromItem(currentItem.getItem()) == 281 && this.throwTimer.isDelayComplete((long)((Double)this.delay.getCurrentValue()).intValue())) {
                        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, id, this.mc.thePlayer.inventory.currentItem, 4, (EntityPlayer)this.mc.thePlayer);
                        this.throwTimer.reset();
                    }
                }
            }
        }
    }
    
    private int getNextSoup() {
        for (int id = 36; id < 45; ++id) {
            final Slot currentSlot = this.mc.thePlayer.inventoryContainer.getSlot(id);
            if (currentSlot.getHasStack() && currentSlot.getStack().getItem() instanceof ItemSoup) {
                return id;
            }
        }
        return -1;
    }
    
    private int getFreeSlot() {
        for (int id = 36; id < 45; ++id) {
            final Slot currentSlot = this.mc.thePlayer.inventoryContainer.getSlot(id);
            if (!currentSlot.getHasStack()) {
                return id;
            }
        }
        return -1;
    }
}
