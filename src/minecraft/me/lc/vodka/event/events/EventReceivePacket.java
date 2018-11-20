package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;
import net.minecraft.network.Packet;

public class EventReceivePacket
  extends Event
{
  Runnable r2 = ()-> System.out.println("233333333");
  private Packet packet;
  public boolean cancel;
  
  public EventReceivePacket(Packet packet)
  {
    super(Type.PRE);
    this.packet = packet;
    this.cancel = false;
  }
  
  public Packet getPacket()
  {
    return this.packet;
  }
}