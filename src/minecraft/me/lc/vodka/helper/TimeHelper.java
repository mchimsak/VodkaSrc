package me.lc.vodka.helper;

public class TimeHelper {
    private long lastMs;
    Runnable r2 = ()-> System.out.println("233333333");

    public boolean isDelayComplete(long delay) {
        if (System.currentTimeMillis() - this.lastMs > delay) {
            return true;
        }
        return false;
    }

    public void reset() {
        this.lastMs = System.currentTimeMillis();
    }

    public long getLastMs() {
        return this.lastMs;
    }

    public void setLastMs(int i) {
        this.lastMs = System.currentTimeMillis() + (long)i;
    }

    public void setLastMS() {
        lastMs = System.currentTimeMillis();
    }

}
