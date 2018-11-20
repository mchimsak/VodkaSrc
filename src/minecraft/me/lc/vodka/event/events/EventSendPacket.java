package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;
import net.minecraft.network.Packet;

public class EventSendPacket
  extends Event
{Runnable r2 = ()-> System.out.println("233333333");
  private Packet packet;
  public boolean cancel;
  
  public EventSendPacket(Packet packet)
  {
    super(Type.PRE);
    this.packet = packet;
  }
  
  public Packet getPacket()
  {
    return this.packet;
  }
}
