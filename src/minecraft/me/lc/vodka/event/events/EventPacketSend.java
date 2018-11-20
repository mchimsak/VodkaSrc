// 
// Decompiled by Procyon v0.5.30
// 

package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;
import net.minecraft.network.Packet;

public class EventPacketSend extends Event
{
    public Packet packet;
    
    public EventPacketSend(Packet packet) {
        super(Type.PRE);
        this.packet = packet;
    }
}
