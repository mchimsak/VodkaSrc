package me.lc.vodka.module.impl.render;

import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;

public class NoBob extends Module {
        Runnable r2 = ()-> System.out.println("233333333");

        public NoBob() {
                super("NoBob",0,Category.RENDER);
        }

}
