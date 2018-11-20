package me.lc.vodka.utils;

import java.awt.Color;

public enum Colors
{
    BLACK("BLACK", 0, -16711423),
    BLUE("BLUE", 1, -12028161),
    DARKBLUE("DARKBLUE", 2, -12621684),
    GREEN("GREEN", 3, -9830551),
    DARKGREEN("DARKGREEN", 4, -9320847),
    WHITE("WHITE", 5, -65794),
    AQUA("AQUA", 6, -7820064),
    DARKAQUA("DARKAQUA", 7, -12621684),
    GREY("GREY", 8, -9868951),
    DARKGREY("DARKGREY", 9, -14342875),
    RED("RED", 10, -65536),
    DARKRED("DARKRED", 11, -8388608),
    ORANGE("ORANGE", 12, -29696),
    DARKORANGE("DARKORANGE", 13, -2263808),
    YELLOW("YELLOW", 14, -256),
    DARKYELLOW("DARKYELLOW", 15, -2702025),
    MAGENTA("MAGENTA", 16, -18751),
    DARKMAGENTA("DARKMAGENTA", 17, -2252579);

    public int c;

    private Colors(final String s, final int n, final int co) {
        this.c = co;
    }
}
