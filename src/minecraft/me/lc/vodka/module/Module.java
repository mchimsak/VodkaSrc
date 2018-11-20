package me.lc.vodka.module;

import me.lc.vodka.Vodka;
import me.lc.vodka.helper.TimeHelper;
import me.lc.vodka.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.GameSettings;

import java.util.Random;

public class Module {

    /**
     * Common variables for every module.
     **/
    protected static Minecraft mc = Minecraft.getMinecraft();
    protected GameSettings game = mc.gameSettings;
    protected static EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
    protected static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    protected static Vodka INSTANCE = Vodka.INSTANCE;
    protected Random r = new Random();
    Runnable r2 = ()-> System.out.println("233333333");


    /**
     * A simple timer so every module has there own instance of the timer class.
     */
    protected Timer timer;

    /**
     * Name of the module.
     **/
    private String name;

    /**
     * Holds the key that the module is currently bounded to.
     **/
    private int keyCode;

    /**
     * Holds weather the module is toggled or not.
     **/
    private boolean toggled;

    /**
     * Holds what category the module is in.
     **/
    private Category category;

    public Module(String name, int keyCode, Category category) {
        this.name = name;
        this.keyCode = keyCode;
        this.category = category;
        this.toggled = false;
        this.timer = new Timer();
    }

    /**
     * Toggles the module depending on its previous state.
     **/
    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void setToggled(boolean toggled) {
        if(toggled)
            this.toggled = toggled;
        else
            this.toggled = false;

        if(toggled)
            onEnable();
        else
            onDisable();
    }

    /**
     * Called when the module is turned on. (Registers the module so it can use
     * events).
     **/
    public void onEnable() {
        INSTANCE.EVENT_MANAGER.register(this);
        timer.reset();
    }

    /**
     * Called then the module is turned off. (Unregisters the module so none of the
     * events are usable.
     **/
    public void onDisable() {
        INSTANCE.EVENT_MANAGER.unregister(this);
        timer.reset();
    }

    /**
     * Returns weather the module is module is toggled or not.
     **/
    public boolean isToggled() {
        return this.toggled;
    }

    /**
     * Returns the name of the module.
     **/
    public String getName() {
        return name;
    }

    /**
     * Returns the key that the module is currently bound too.
     **/
    public int getKeyCode() {
        return keyCode;
    }

    /**
     * Sets what key the module is bound too.
     **/
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * Gets the category that the module belongs too.
     **/
    public Category getCategory() {
        return category;
    }

}
