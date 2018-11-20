package me.lc.vodka.module.impl.world;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.helper.TimeHelper;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import org.lwjgl.input.Keyboard;

import net.minecraft.inventory.ContainerChest;

public class ChestStealer extends Module {

    public Setting delay;
    public Setting insant;
    TimeHelper time = new TimeHelper();

    public ChestStealer() {
        super("ChestStealer",Keyboard.KEY_K,Category.WORLD);
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(delay = (new Setting(this,"Delay",30.0D,0.0D,1000.0D,true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(insant = (new Setting(this,"Insant",false)));
    }
    Runnable r2 = ()-> System.out.println("233333333");

@EventTarget
    public void onPlayer(EventUpdate e) {
      if(this.isToggled()){
        if (this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest c = (ContainerChest)this.mc.thePlayer.openContainer;
            boolean empty = true;
            int i = 0;
            while (i < c.getLowerChestInventory().getSizeInventory()) {
                if (c.getLowerChestInventory().getStackInSlot(i) != null) {
                    empty = false;
                    if (this.insant.getBooleanValue() || this.time.isDelayComplete((long)this.delay.getCurrentValue())) {
                        this.mc.playerController.windowClick(c.windowId, i, 0, 1, this.mc.thePlayer);
                        this.time.reset();
                    }
                }
                ++i;
            }
            if (empty) {
                this.mc.thePlayer.closeScreen();
            }
        }
    }
    }
}
