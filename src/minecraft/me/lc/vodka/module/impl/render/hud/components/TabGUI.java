package me.lc.vodka.module.impl.render.hud.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventPriority;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventKeyboard;
import me.lc.vodka.event.events.EventRender2D;
import me.lc.vodka.font.Fonts;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.module.impl.render.hud.HUD;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.utils.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class TabGUI {

    private FontRenderer fr;
    Runnable r2 = ()-> System.out.println("233333333");
    private ArrayList<Category> categoryValues;
    private int currentCategoryIndex, currentModIndex, currentSettingIndex;
    private boolean editMode;

    private int screen;
    private Minecraft mc = Minecraft.getMinecraft();
    private boolean logotf = true;
    private int logo = 0;
    public TabGUI() {
        this.fr = Minecraft.getMinecraft().fontRendererObj;
        this.categoryValues = new ArrayList<Category>();
        this.currentCategoryIndex = 0;
        this.currentModIndex = 0;
        this.currentSettingIndex = 0;
        this.editMode = false;
        this.screen = 0;
        this.categoryValues.addAll( Arrays.asList( Category.values() ) );
    }

    @EventTarget
    public void onRender(EventRender2D e) {
        if (logotf) {
            Gui.drawRect(1,1,82 + Fonts.clickgui.getStringHeight("A") + 1,Fonts.clickgui.getStringHeight("V") + 6,-1610612735);
            Fonts.clickgui.drawString("V",2,2,rainbowModuleList(5000, 15 * 2));
            Fonts.clickgui.drawString("O",22,2,rainbowModuleList(5000, 15 * 13));
            Fonts.clickgui.drawString("D",42,2,rainbowModuleList(5000, 15 * 24));
            Fonts.clickgui.drawString("K",62,2,rainbowModuleList(5000, 15 * 35));
            Fonts.clickgui.drawString("A",82,2,rainbowModuleList(5000, 15 * 46));
        }
    }

    @EventTarget(priority = EventPriority.HIGH)
    public void onRedner(EventRender2D e) {

        if (!HUD.tabGui.getBooleanValue())
            return;
        if (logotf)
            return;
        this.renderTopString( 5, 5 );
        int startX = 5;
        int startY = (5 + 9) + 2;
        Gui.drawRect( startX, startY, startX + this.getWidestCategory() + 5,
                startY + this.categoryValues.size() * (9 + 2), rainbowModuleList( 5000, 15 ) );
        for (Category c : this.categoryValues) {
            if (this.getCurrentCategorry().equals( c )) {
                Gui.drawRect( startX + 1, startY, startX + this.getWidestCategory() + 5 - 1, startY + 9 + 2,
                        new Color(38, 50, 56, 190 ).getRGB() );
            }

            String name = c.name();
            Fonts.clickgui.drawString( name.substring( 0, 1 ).toUpperCase() + name.substring( 1, name.length() ).toLowerCase(),
                    startX + 2 + (this.getCurrentCategorry().equals( c ) ? 2 : 0), startY, -1 );
            startY += 9 + 2;
        }

        if (screen == 1 || screen == 2) {
            int startModsX = startX + this.getWidestCategory() + 6;
            int startModsY = ((5 + 9) + 2) + currentCategoryIndex * (9 + 2);
            Gui.drawRect( startModsX, startModsY, startModsX + this.getWidestMod() + 5,
                    startModsY + this.getModsForCurrentCategory().size() * (9 + 2), rainbowModuleList( 5000, 15 ) );
            for (Module m : getModsForCurrentCategory()) {
                if (this.getCurrentModule().equals( m )) {
                    Gui.drawRect( startModsX + 1, startModsY, startModsX + this.getWidestMod() + 5 - 1,
                            startModsY + 9 + 2, new Color(38, 50, 56, 190 ).getRGB() );
                }
                Fonts.clickgui.drawString( m.getName() + (Vodka.INSTANCE.SETTING_MANAGER.getSettingsForModule( m ) != null ? ">" : ""), startModsX + 2 + (this.getCurrentModule().equals( m ) ? 2 : 0),
                        startModsY, m.isToggled() ? -1 : Color.GRAY.getRGB() );
                startModsY += 9 + 2;
            }
        }
        if (screen == 2) {
            int startSettingX = (startX + this.getWidestCategory() + 6) + this.getWidestCategory() + 8;
            int startSettingY = ((5 + 9) + 2) + (currentCategoryIndex * (9 + 2)) + currentModIndex * (9 + 2);

            Gui.drawRect( startSettingX, startSettingY, startSettingX + this.getWidestSetting() + 5,
                    startSettingY + this.getSettingForCurrentMod().size() * (9 + 2), rainbowModuleList( 5000, 15 ) );
            for (Setting s : this.getSettingForCurrentMod()) {

                if (this.getCurrentSetting().equals( s )) {
                    Gui.drawRect( startSettingX + 1, startSettingY, startSettingX + this.getWidestSetting() + 5 - 1,
                            startSettingY + 9 + 2, new Color(38, 50, 56, 190 ).getRGB() );
                }
                if (s.isBoolean()) {
                    Fonts.clickgui.drawString( s.getName() + ": " + s.getBooleanValue(),
                            startSettingX + 2 + (this.getCurrentSetting().equals( s ) ? 2 : 0), startSettingY,
                            editMode && this.getCurrentSetting().equals( s ) ? -1 : Color.GRAY.getRGB() );
                } else if (s.isDigit()) {
                    Fonts.clickgui.drawString( s.getName() + ": " + s.getCurrentValue(),
                            startSettingX + 2 + (this.getCurrentSetting().equals( s ) ? 2 : 0), startSettingY,
                            editMode && this.getCurrentSetting().equals( s ) ? -1 : Color.GRAY.getRGB() );
                } else {
                    Fonts.clickgui.drawString( s.getName() + ": " + s.getCurrentOption(),
                            startSettingX + 2 + (this.getCurrentSetting().equals( s ) ? 2 : 0), startSettingY,
                            editMode && this.getCurrentSetting().equals( s ) ? -1 : Color.GRAY.getRGB() );
                }
                startSettingY += 9 + 2;
            }
        }

    }

    private int rainbowModuleList(int speed, int offset) {
        float color = (System.currentTimeMillis() + offset) % speed;
        color /= speed;
        return Color.getHSBColor( color, 1f, 1f ).getRGB();
    }

    private void renderTopString(int x, int y) {
        Fonts.clickgui.drawString( Vodka.INSTANCE.NAME
                + Vodka.INSTANCE.VERSION, x, y, rainbowModuleList( 5000, 15 ) );
    }

    private void up() {
        if (this.logotf == true)
            return;
        if (this.currentCategoryIndex > 0 && this.screen == 0) {
            this.currentCategoryIndex--;
        } else if (this.currentCategoryIndex == 0 && this.screen == 0) {
            this.currentCategoryIndex = this.categoryValues.size() - 1;
        } else if (this.currentModIndex > 0 && this.screen == 1) {
            this.currentModIndex--;
        } else if (this.currentModIndex == 0 && this.screen == 1) {
            this.currentModIndex = this.getModsForCurrentCategory().size() - 1;
        } else if (this.currentSettingIndex > 0 && this.screen == 2 && !this.editMode) {
            this.currentSettingIndex--;
        } else if (this.currentSettingIndex == 0 && this.screen == 2 && !this.editMode) {
            this.currentSettingIndex = this.getSettingForCurrentMod().size() - 1;
        }
        DecimalFormat df = new DecimalFormat( "#.0" );
        if (editMode) {
            Setting s = this.getCurrentSetting();
            if (s.isBoolean()) {
                s.setBooleanValue( !s.getBooleanValue() );
            } else if (s.isDigit()) {
                if (s.getCurrentValue() >= s.getMaxValue())
                    return;
                if (s.isOnlyInt()) {
                    s.setCurrentValue( s.getCurrentValue() + 1 );
                } else {
                    double a = s.getCurrentValue() + 0.1;
                    String b = df.format( a );
                    s.setCurrentValue( Double.parseDouble( b ) );
                }

            } else {
                if (s.getCurrentValue() <= s.getMaxValue())
                    return;
                try {
                    if (s.getCurrentOptionIndex() <= 0)
                        return;
                    s.setCurrentOption( s.getOptions().get( s.getCurrentOptionIndex() - 1 ) );
                } catch (Exception e) {
                    s.setCurrentOption( s.getOptions().get( s.getOptions().size() - 1 ) );
                }

            }
        }

    }

    private void down() {
        if (this.logotf == true)
            return;
        if (this.currentCategoryIndex < this.categoryValues.size() - 1 && this.screen == 0) {
            this.currentCategoryIndex++;
        } else if (this.currentCategoryIndex == this.categoryValues.size() - 1 && this.screen == 0) {
            this.currentCategoryIndex = 0;
        } else if (this.currentModIndex < this.getModsForCurrentCategory().size() - 1 && this.screen == 1) {
            this.currentModIndex++;
        } else if (this.currentModIndex == this.getModsForCurrentCategory().size() - 1 && this.screen == 1) {
            this.currentModIndex = 0;
        } else if (this.currentSettingIndex < this.getSettingForCurrentMod().size() - 1 && this.screen == 2
                && !this.editMode) {
            this.currentSettingIndex++;
        } else if (this.currentSettingIndex == this.getSettingForCurrentMod().size() - 1 && this.screen == 2
                && !this.editMode) {
            this.currentSettingIndex = 0;
        }
        DecimalFormat df = new DecimalFormat( "#.0" );
        if (editMode) {
            Setting s = this.getCurrentSetting();
            if (s.isBoolean()) {
                s.setBooleanValue( !s.getBooleanValue() );
            } else if (s.isDigit()) {
                if (s.getCurrentValue() <= s.getMinValue())
                    return;
                if (s.isOnlyInt()) {
                    s.setCurrentValue( s.getCurrentValue() - 1 );
                } else {
                    double a = s.getCurrentValue() - 0.1;
                    String b = df.format( a );
                    s.setCurrentValue( Double.parseDouble( b ) );
                }

            } else {
                try {
                    s.setCurrentOption( s.getOptions().get( s.getCurrentOptionIndex() + 1 ) );
                } catch (Exception e) {
                    s.setCurrentOption( s.getOptions().get( 0 ) );
                }

            }
        }
    }

    private void right(int key) {
        if (this.logotf == true || logotf == false) {
            this.logotf = false;
            if (logo < 2)
                this.logo = logo + 1;
        }
        if (this.screen == 0 && this.logo == 2) {
            this.screen = 1;
        } else if (this.screen == 1 && this.getCurrentModule() != null && this.getSettingForCurrentMod() == null) {
            this.getCurrentModule().toggle();
        } else if (this.screen == 1 && this.getSettingForCurrentMod() != null && this.getCurrentModule() != null && key == Keyboard.KEY_RETURN) {
            this.getCurrentModule().toggle();
        } else if (this.screen == 1 && this.getSettingForCurrentMod() != null && this.getCurrentModule() != null) {
            this.screen = 2;
        } else if (this.screen == 2) {
            this.editMode = !this.editMode;
        }

    }

    private void left() {
        if (this.screen == 0) {
            this.logotf = true;
            this.logo = 0;
        }
        if (this.screen == 1) {
            this.screen = 0;
            this.currentModIndex = 0;
        } else if (this.screen == 2) {
            this.screen = 1;
            this.editMode = false;
            this.currentSettingIndex = 0;
        }

    }

    @EventTarget
    public void onKey(EventKeyboard e) {
        switch (e.getKey()) {
            case Keyboard.KEY_UP:
                this.up();
                break;
            case Keyboard.KEY_DOWN:
                this.down();
                break;
            case Keyboard.KEY_RIGHT:
                this.right( Keyboard.KEY_RIGHT );
                break;
            case Keyboard.KEY_LEFT:
                this.left();
                break;
            case Keyboard.KEY_RETURN:
                this.right( Keyboard.KEY_RETURN );
                break;
        }
    }

    private Setting getCurrentSetting() {
        return getSettingForCurrentMod().get( currentSettingIndex );

    }

    private ArrayList<Setting> getSettingForCurrentMod() {
        return Vodka.INSTANCE.SETTING_MANAGER.getSettingsForModule( getCurrentModule() );
    }

    private Category getCurrentCategorry() {
        return this.categoryValues.get( this.currentCategoryIndex );
    }

    private Module getCurrentModule() {
        return getModsForCurrentCategory().get( currentModIndex );
    }

    private ArrayList<Module> getModsForCurrentCategory() {
        ArrayList<Module> mods = new ArrayList<Module>();
        Category c = getCurrentCategorry();
        for (Module m : Vodka.INSTANCE.MODULE_MANAGER.getModules()) {
            if (m.getCategory().equals( c )) {
                mods.add( m );
            }
        }
        return mods;
    }

    private int getWidestSetting() {
        int width = 0;
        for (Setting s : getSettingForCurrentMod()) {
            String name;
            if (s.isBoolean()) {
                name = s.getName() + ": " + s.getBooleanValue();

            } else if (s.isDigit()) {
                name = s.getName() + ": " + s.getCurrentValue();
            } else {
                name = s.getName() + ": " + s.getCurrentOption();
            }
            if (fr.getStringWidth( name ) > width) {
                width = fr.getStringWidth( name );
            }
        }
        return width;
    }

    private int getWidestMod() {
        int width = 0;
        for (Module m : Vodka.INSTANCE.MODULE_MANAGER.getModules()) {
            int cWidth = fr.getStringWidth( m.getName() );
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }

    private int getWidestCategory() {
        int width = 0;
        for (Category c : this.categoryValues) {
            String name = c.name();
            int cWidth = fr.getStringWidth(
                    name.substring( 0, 1 ).toUpperCase() + name.substring( 1, name.length() ).toLowerCase() );
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }

}
