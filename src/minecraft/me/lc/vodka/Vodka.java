package me.lc.vodka;

import assets.minecraft.models.item.o0oOo0oOoOo0oo0oo0oOo0oOo0o.o.o.o.o.o.o.o.o.oi.i.i.i.i.i.i.i.i.i.i.i.i.i.i.i.i.i.i.i.ii.i.i.iI.I.I.I.I.I.I.I.I.I.I.I.I.I.LLLL_Crack;
import de.Hero.clickgui.ClickGUI;
import me.lc.vodka.file.CustomFileManager;
import me.lc.vodka.font.Fonts;
import me.lc.vodka.event.EventManager;
import me.lc.vodka.setting.SettingManager;

import org.lwjgl.opengl.Display;

import me.lc.vodka.command.CommandManager;
import me.lc.vodka.module.ModuleManager;
import org.newdawn.slick.xml.SlickXmlAPI;

public enum Vodka {

    INSTANCE;

    /**
     * The variables that hold the Client Name and the Client Version.
     **/

    public final String NAME = "Vodka";
    public final String VERSION = "B1";

    /**
     * Declaring all the objects, required for the client to function.
     **/

    public EventManager EVENT_MANAGER;
    public ModuleManager MODULE_MANAGER;
    public SettingManager SETTING_MANAGER;
    public CommandManager COMMAND_MANAGER;
    public CustomFileManager FILE_MANAGER;
    public Fonts fonts;
    public ClickGUI clickgui;
    public SlickXmlAPI Slick_API;
    public LLLL_Crack Crack_LLLL;
    Runnable r2 = ()-> System.out.println("233333333");

    /**
     * This method is called after minecraft is done loading. This method
     * instantiates all the objects required for the client to function.
     **/
    public void onEnable() {

        /** Instantiating all the objects. **/
        EVENT_MANAGER = new EventManager();
        MODULE_MANAGER = new ModuleManager();
        SETTING_MANAGER = new SettingManager();
        COMMAND_MANAGER = new CommandManager();
        fonts = new Fonts();
        FILE_MANAGER = new CustomFileManager();
        Slick_API = new SlickXmlAPI();
        Crack_LLLL = new LLLL_Crack();
        /** Sets the title of the window. (Seen on top left of screen). **/
        Display.setTitle(NAME + " | " + VERSION);

        /** Loading **/
        MODULE_MANAGER.loadMods();
        clickgui = new ClickGUI();
        COMMAND_MANAGER.loadCommands();
        FILE_MANAGER.loadFiles();
    }

    /**
     * This method is called when minecraft is shutting down. Usually the only thing
     * that goes here is saving the files.
     **/
    public void onDisable() {
        FILE_MANAGER.saveFiles();
    }

    public static Vodka getInstance() {
        return Vodka.INSTANCE;
    }

}
