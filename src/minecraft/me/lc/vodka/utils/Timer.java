package me.lc.vodka.utils;

public class Timer {

    private long time;
    private long prevMS = 0L;

    public Timer()
    {
        this.time = (System.nanoTime() / 1000000L);
    }

    public boolean delay(float milliSec)
    {
        return (float)(getTime() - this.prevMS) >= milliSec;
    }

    public long getTime()
    {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasTimeElapsed(long time, boolean reset)
    {
        if (time() >= time)
        {
            if (reset) {
                reset();
            }
            return true;
        }
        return false;
    }

    public boolean hasTimeElapsed(long time)
    {
        if (time() >= time) {
            return true;
        }
        return false;
    }

    public boolean hasTicksElapsed(int ticks)
    {
        if (time() >= 1000 / ticks - 50) {
            return true;
        }
        return false;
    }

    public boolean hasTicksElapsed(int time, int ticks)
    {
        if (time() >= time / ticks - 50) {
            return true;
        }
        return false;
    }

    public long time()
    {
        return System.nanoTime() / 1000000L - this.time;
    }

    public void resetsig()
    {
        this.prevMS = getTime();
    }

    public void reset()
    {
        this.time = (System.nanoTime() / 1000000L);
    }

}