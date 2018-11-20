package me.lc.vodka.module.impl.render;

import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import net.minecraft.block.Block;

import java.util.ArrayList;

public class Xray extends Module {
        public static ArrayList<Block> xrayBlocks = new ArrayList();
        Runnable r2 = ()-> System.out.println("233333333");
        public Xray() {
                super("Xray",0,Category.RENDER);
        }
}
