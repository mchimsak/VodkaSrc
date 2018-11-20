package me.lc.vodka.event.events;


import me.lc.vodka.event.Event;

public class EventRender
  extends Event
{
  private float partialTicks;
  Runnable r2 = ()-> System.out.println("233333333");
  public EventRender(float partialTicks)
  {
    super(Type.PRE);
    this.partialTicks = partialTicks;
  }
  
  public float getPartialTicks()
  {
    return this.partialTicks;
  }
}