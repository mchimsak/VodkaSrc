package me.lc.vodka.event;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CopyOnWriteArrayList;

import me.lc.vodka.Vodka;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;

public abstract class Event {
    Runnable r2 = ()-> System.out.println("233333333");
    private boolean cancelled;
    private Type type;

    public Event(Type type) {
        this.type = type;
        this.cancelled = false;
    }

    public enum Type {
        PRE, POST
    }

    public void call() {
        cancelled = false;

        CopyOnWriteArrayList<Data> dataList = Vodka.INSTANCE.EVENT_MANAGER.get(this.getClass());

        if (dataList == null)
            return;

        dataList.forEach(data -> {
            
            try {
                data.getTarget().invoke(data.getSource(), this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        });


    }

    public Type getType() {
        return type;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }


}