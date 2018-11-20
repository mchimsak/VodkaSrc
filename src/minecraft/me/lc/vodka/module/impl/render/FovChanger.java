package me.lc.vodka.module.impl.render;

import me.lc.vodka.Vodka;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;

public class FovChanger extends Module {
        public static Setting value;
        Runnable r2 = ()-> System.out.println("233333333");
        public FovChanger() {
                super("FovChanger",0,Category.RENDER);
                Vodka.INSTANCE.SETTING_MANAGER.addSetting(value = (new Setting(this,"Value",0.1D,0.1D,1.0D,false)));
        }

}
