package de.Hero.clickgui.util;

import me.lc.vodka.module.impl.render.ClickGui;

import java.awt.Color;

//Deine Imports

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ColorUtil {
	
	public static Color getClickGUIColor(){
		return new Color((int)ClickGui.red.getCurrentValue(), (int)ClickGui.green.getCurrentValue(), (int)ClickGui.blue.getCurrentValue());
	}
}
