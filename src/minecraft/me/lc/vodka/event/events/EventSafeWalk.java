package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;

public class EventSafeWalk
extends Event {
    public boolean safe;

    public EventSafeWalk(boolean safe)
    {
        super(Type.PRE);
        this.safe = safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }
}

