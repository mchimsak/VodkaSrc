package me.lc.vodka.module.impl.render;

import me.lc.vodka.Vodka;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ClickGui extends Module {
        public static Setting sound;
        public static Setting red;
        public static Setting green;
        public static Setting blue;
        public static Setting design;
        public static ArrayList<String> options = new ArrayList<>();

        Runnable r2 = ()-> System.out.println("233333333");
        public ClickGui() {
                super("ClickGui",Keyboard.KEY_RSHIFT,Category.RENDER);
                Vodka.INSTANCE.SETTING_MANAGER.addSetting(sound = (new Setting(this,"Sound",false)));
                Vodka.INSTANCE.SETTING_MANAGER.addSetting(red = (new Setting(this,"GuiRed",255, 0, 255,true)));
                Vodka.INSTANCE.SETTING_MANAGER.addSetting(green = (new Setting(this,"GuiGreen",26, 0, 255,true)));
                Vodka.INSTANCE.SETTING_MANAGER.addSetting(blue = (new Setting(this,"GuiBlue",42, 0, 255,true)));
                Vodka.INSTANCE.SETTING_MANAGER.addSetting(design = (new Setting(this,"Design","JellyLike",options)));
                options.add(0,"JellyLike");
                options.add(1,"New");
        }

        @Override
        public void onEnable() {
                mc.displayGuiScreen(Vodka.INSTANCE.clickgui);
                setToggled(false);
                super.onEnable();
        }
}
