package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;

public class EventRender2D extends Event {
	Runnable r2 = ()-> System.out.println("233333333");
	public int width, height;

	public EventRender2D(int width, int height) {
		super(Type.PRE);
		this.width = width;
		this.height = height;
	}
}
