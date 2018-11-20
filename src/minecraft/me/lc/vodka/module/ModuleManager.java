package me.lc.vodka.module;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventKeyboard;
import me.lc.vodka.module.impl.combat.*;
import me.lc.vodka.module.impl.movement.*;
import me.lc.vodka.module.impl.other.*;
import me.lc.vodka.module.impl.player.*;
import me.lc.vodka.module.impl.render.*;
import me.lc.vodka.module.impl.render.hud.HUD;
import me.lc.vodka.module.impl.render.NoFov;
import me.lc.vodka.module.impl.world.*;

import java.util.ArrayList;

public class ModuleManager {
     Runnable r2 = ()-> System.out.println("233333333");
    /**
     * The ArrayList that holds all the modules.
     **/
    private ArrayList<Module> modules;

    public ModuleManager() {
        modules = new ArrayList<Module>();
        Vodka.INSTANCE.EVENT_MANAGER.register(this);
    }

    /**
     * Loads all the modules.
     **/
    public void loadMods() {
        addModule(new HUD());
        addModule(new FullBright());
        addModule(new Sprint());
        addModule(new KillAura());
        addModule(new AutoClicker());
        addModule(new SlimeJump());
        addModule(new BHop());
        addModule(new Eagle());
        addModule(new Step());
        addModule(new Reach());
        addModule(new AutoArmor());
        addModule(new Fly());
        addModule(new Timer());
        addModule(new Velocity());
        addModule(new TNTBlock());
        addModule(new Glide());
        addModule(new NoSlow());
        addModule(new ChestStealer());
        addModule(new AirJump());
        addModule(new NoWeb());
        addModule(new Speed());
        addModule(new IceSpeed());
        addModule(new AntiBots());
        addModule(new FastEat());
        addModule(new FastBow());
        addModule(new NoHurtTime());
        addModule(new WallSpeed());
        addModule(new Spammer());
        addModule(new LongJump());
        addModule(new Teams());
        addModule(new Jesus());
        addModule(new WaterSpeed());
        addModule(new InventoryWalk());
        addModule(new Scaffold());
        addModule(new NoFall());
        addModule(new NoBob());
        addModule(new NoFov());
        addModule(new NoScoreboard());
        addModule(new Tower());
        addModule(new ChestAura());
        addModule(new Chams());
        addModule(new FovChanger());
        addModule(new Triggerbot());
        addModule(new Fucker());
        addModule(new GodMode());
        addModule(new AutoSoup());
        addModule(new AutoPot());
        addModule(new TPAura());
        addModule(new Criticals());
        addModule(new ESP());
        addModule(new ChestESP());
        addModule(new ScaffoldWalk());
        addModule(new ClickGui());
        addModule(new ItemPhysics());
        addModule(new NameProtect());
        addModule(new HitAnimation());
        addModule(new Blink());
        addModule(new Freecam());
        addModule(new Zoot());
        addModule(new NoRotate());
        addModule(new BlockESP());
        addModule(new NoClip());
        addModule(new BlockHighlight());
        addModule(new Tracers());
        addModule(new Crasher());
        addModule(new Trajectories());
        addModule(new Regen());
        addModule(new AutoWalk());
        addModule(new SafeWalk());
        addModule(new TestAura());
        addModule(new SpeedMine());
        addModule(new FastPlace());
        addModule(new Xray());
    }

    /**
     * A methods that allows you to add a module to the array of all the modules.
     **/
    private void addModule(Module m) {
        modules.add(m);
    }

    /**
     * Returns all the modules.
     **/
    public ArrayList<Module> getModules() {
        return modules;
    }

    @EventTarget
    public void onKey(EventKeyboard eventKeyBoard) {
        for (Module mod : modules) {
            if (mod.getKeyCode() == eventKeyBoard.getKey())
                mod.toggle();
        }
    }

    /**
     * Goes through all the modules and returns an array of all the toggled modules.
     **/
    public ArrayList<Module> getToggledModules() {
        ArrayList<Module> mods = new ArrayList<Module>();
        for (Module m : modules) {
            if (m.isToggled())
                mods.add(m);
        }
        return mods;
    }

    /**
     * Tries to get the module by name, if none found it returns null.
     **/
    public Module getModule(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name))
                return m;
        }
        return null;
    }

    /**
     * Tries to get the module by class, in none found it returns null.
     **/
    public Module getModule(Class<? extends Module> clazz) {
        for (Module m : modules) {
            if (m.getClass() == clazz)
                return m;
        }
        return null;
    }

}
