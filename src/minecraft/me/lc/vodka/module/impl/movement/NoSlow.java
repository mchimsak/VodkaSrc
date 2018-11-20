// 
// Decompiled by Procyon v0.5.30
// 

package me.lc.vodka.module.impl.movement;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
public class NoSlow extends Module
{
    public NoSlow() {
        super("NoSlow",0, Category.MOVEMENT);
    }
    
    @EventTarget
    public void onUpdate(EventUpdate event) {
        Runnable r2 = ()-> System.out.println("233333333");

    }
}
