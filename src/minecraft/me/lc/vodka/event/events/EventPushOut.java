package me.lc.vodka.event.events;


import me.lc.vodka.event.Event;

public class EventPushOut
  extends Event
{
  Runnable r2 = ()-> System.out.println("233333333");

  public boolean cancel;
  public EventPushOut(Type type) {
    super(type);
  }
}