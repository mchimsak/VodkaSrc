package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;

public class EventRender3D extends Event {
	Runnable r2 = ()-> System.out.println("233333333");
	public float particlTicks;

	public EventRender3D(float particlTicks) {
		super(Type.PRE);
		this.particlTicks = particlTicks;
	}

}
