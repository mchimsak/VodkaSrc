package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;
import net.minecraft.network.Packet;

public class EventPacketTake extends Event
{
    public Packet packet;
    
    public EventPacketTake(final Packet packet) {
        super(Type.PRE);
        this.packet = packet;
    }
}
