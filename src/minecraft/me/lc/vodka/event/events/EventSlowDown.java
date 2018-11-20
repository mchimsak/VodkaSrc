package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;

public class EventSlowDown extends Event {
    Runnable r2 = ()-> System.out.println("233333333");
    public EventSlowDown() {
        super(Type.PRE);
    }

}
