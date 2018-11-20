package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;

public class EventTick extends Event {
    Runnable r2 = ()-> System.out.println("233333333");
    public EventTick() {
        super(Type.PRE);
    }
    

}
