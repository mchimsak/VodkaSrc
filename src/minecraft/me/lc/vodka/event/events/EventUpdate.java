package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;

public class EventUpdate extends Event {
    Runnable r2 = ()-> System.out.println("233333333");
    public EventUpdate(Type type) {
        super(type);
    }

}
