package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;

public class EventMotion extends Event {

    public float yaw, pitch;
    public boolean ground;
    public double y;

    public EventMotion(Event.Type type, float yaw, float pitch, boolean ground, double y) {
        super(type);
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
        this.y = y;
    }
    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

}
