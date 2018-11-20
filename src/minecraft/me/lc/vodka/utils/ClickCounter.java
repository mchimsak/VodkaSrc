package me.lc.vodka.utils;

public class ClickCounter
{
    static long time = 0L;
    public static double clicks = 0.0D;
    static double result = 0.0D;

    public static double getClickResult()
    {
        return result;
    }

    public static void click()
    {

            ++clicks;

    }

    public static void tick()
    {

            result = clicks;
            time = System.currentTimeMillis() + 1000L;
            clicks = 0.0D;

    }
}
