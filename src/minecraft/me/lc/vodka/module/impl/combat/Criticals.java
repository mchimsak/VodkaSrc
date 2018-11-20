package me.lc.vodka.module.impl.combat;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventAttackEntity;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import net.minecraft.entity.Entity;

public class Criticals extends Module
{
    Runnable r2 = ()-> System.out.println("233333333");
    private  Entity target;
    public Criticals() {
        super("Criticals", 0,Category.COMBAT);
    }

    @EventTarget
    public void onUpdate(EventAttackEntity event) {
        this.mc.thePlayer.onCriticalHit(event.getTarget());
    }
}
