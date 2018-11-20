package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;

public class EventPlayerVelocity extends Event {

    public double mx, my;

    public EventPlayerVelocity(double x, double y) {
        super(Type.PRE);
        this.mx = x;
        this.my = y;
    }

}
