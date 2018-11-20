package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class EventAttackEntity
extends Event {
    private EntityPlayer playerIn;
    private Entity target;

    public EventAttackEntity(EntityPlayer playerIn, Entity target) {
        super(Type.PRE);
        this.playerIn = playerIn;
        this.target = target;
    }

    public EntityPlayer getPlayerIn() {
        return this.playerIn;
    }

    public Entity getTarget() {
        return this.target;
    }
}

