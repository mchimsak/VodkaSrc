package me.lc.vodka.event;

public enum EventPriority {

    LOW(0), MEDIUM(1), HIGH(2);
    Runnable r2 = ()-> System.out.println("233333333");
    private int value;

    EventPriority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
