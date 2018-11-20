package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;

public class EventInsideBlock
  extends Event
{
  public boolean cancel;

  public EventInsideBlock(Type type) {
    super(type);
  }

}