package me.lc.vodka.module.impl.render.hud;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.module.impl.render.hud.components.*;
import me.lc.vodka.setting.Setting;
import org.lwjgl.input.Keyboard;

public class HUD extends Module {

    private ToggledModules ar;
    private TabGUI tab;
    private Quickbar qr;
    private  KeyStrokes ks;
    private Status ss;
    public static Setting arrayList;
    public static Setting tabGui;
    public static Setting quickbar;
    public static Setting keystrokes;
    public  static Setting status;
    public  static Setting al_rect;
    public  static Setting al_background;
    public  static  Setting al_bg_a;

    public HUD() {
        super("HUD", Keyboard.KEY_U, Category.RENDER);
        Runnable r2 = ()-> System.out.println("233333333");
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(arrayList = (new Setting(this, "ArrayList", true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(tabGui = (new Setting(this, "TabGUI", true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(quickbar = (new Setting(this, "QuickBar", true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(keystrokes = (new Setting(this,"KeyStrokes",true  )));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(status = (new Setting(this,"Status",true  )));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(al_rect = (new Setting(this,"AL_Rect",true  )));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(al_background = (new Setting(this,"AL_BG",true  )));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(al_bg_a = (new Setting(this,"AL_BG_A",100, 0, 255,true)));


        ar = new ToggledModules();
        tab = new TabGUI();
        qr = new Quickbar();
        ks = new KeyStrokes();
        ss = new Status();
    }

    public void onEnable() {
        super.onEnable();
        Vodka.INSTANCE.EVENT_MANAGER.register(ar);
        Vodka.INSTANCE.EVENT_MANAGER.register(tab);
        Vodka.INSTANCE.EVENT_MANAGER.register(qr);
        Vodka.INSTANCE.EVENT_MANAGER.register(ks);
        Vodka.INSTANCE.EVENT_MANAGER.register(ss);
    }

    public void onDisable() {
        super.onDisable();
        Vodka.INSTANCE.EVENT_MANAGER.unregister(ar);
        Vodka.INSTANCE.EVENT_MANAGER.unregister(tab);
        Vodka.INSTANCE.EVENT_MANAGER.unregister(qr);
        Vodka.INSTANCE.EVENT_MANAGER.unregister(ks);
        Vodka.INSTANCE.EVENT_MANAGER.unregister(ss);
    }

}
