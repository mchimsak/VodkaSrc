package me.lc.vodka.module.impl.render;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.helper.TimeHelper;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.module.impl.render.hud.components.Quickbar;
import org.lwjgl.input.Keyboard;
import java.util.HashMap;


public class NameProtect extends Module {
	Runnable r2 = ()-> System.out.println("233333333");
//	public Setting M;
//	public static ArrayList<String> replacements = new ArrayList<>();

	public static String name = "Vodka";
	public NameProtect() {
		super("NameProtect", Keyboard.KEY_K, Category.RENDER);
//		Vodka.INSTANCE.SETTING_MANAGER.addSetting(M = (new Setting(this,"Name","§6Vodka",replacements)));
	}
}
