package me.lc.vodka.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;

public class Fonts {
    public static UnicodeFontRenderer menuKrypton;
    public static UnicodeFontRenderer arraylist;
    public static UnicodeFontRenderer tabgui;
    public static UnicodeFontRenderer clickgui;
    public static UnicodeFontRenderer quick;
    public static UnicodeFontRenderer maingui;
    public static UnicodeFontRenderer button;
    public static UnicodeFontRenderer nametags;

    public Fonts() {
        try {
            Fonts.loadFonts();
        }
        catch (FontFormatException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFonts() throws FontFormatException, IOException {
        InputStream is = Fonts.class.getResourceAsStream("fonts/mainmenu/B.otf");
        InputStream is1 = Fonts.class.getResourceAsStream("fonts/Arciform.otf");
        InputStream is2 = Fonts.class.getResourceAsStream("fonts/Vox.ttf");
        InputStream is3 = Fonts.class.getResourceAsStream("fonts/OnePlus.otf");
        InputStream is4 = Fonts.class.getResourceAsStream("fonts/Nairi.otf");
        InputStream is5 = Fonts.class.getResourceAsStream("fonts/Marvin.otf");
        InputStream is6 = Fonts.class.getResourceAsStream("fonts/Aleppo-Heavy.ttf");
        InputStream is7 = Fonts.class.getResourceAsStream("fonts/Nairi.otf");
        Font font = null;
        Font font1 = null;
        Font font2 = null;
        Font font3 = null;
        Font font4 = null;
        Font font5 = null;
        Font font6 = null;
        Font font7 = null;
        font = Font.createFont(0, is);
        font1 = Font.createFont(0, is1);
        font2 = Font.createFont(0, is2);
        font3 = Font.createFont(0, is3);
        font4 = Font.createFont(0, is4);
        font5 = Font.createFont(0, is5);
        font6 = Font.createFont(0, is6);
        font7 = Font.createFont(0, is7);
        menuKrypton = new UnicodeFontRenderer(font.deriveFont(150.0f));
        arraylist = new UnicodeFontRenderer(font1.deriveFont(20.0f));
        tabgui = new UnicodeFontRenderer(font2.deriveFont(20.0f));
        clickgui = new UnicodeFontRenderer(font3.deriveFont(8.0f));
        quick = new UnicodeFontRenderer(font4.deriveFont(18.0f));
        maingui = new UnicodeFontRenderer(font5.deriveFont(30.0f));
        button = new UnicodeFontRenderer(font6.deriveFont(20.0f));
        nametags = new UnicodeFontRenderer(font7.deriveFont(10.0f));
        if (Minecraft.getMinecraft().gameSettings.language != null) {
            menuKrypton.setUnicodeFlag(true);
            arraylist.setUnicodeFlag(true);
            tabgui.setUnicodeFlag(true);
            clickgui.setUnicodeFlag(true);
            quick.setUnicodeFlag(true);
            maingui.setUnicodeFlag(true);
            button.setUnicodeFlag(true);
            nametags.setUnicodeFlag(true);
            menuKrypton.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
            arraylist.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
            tabgui.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
            clickgui.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
            quick.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
            maingui.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
            button.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
            nametags.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
        }
    }

    public static enum FontType {
        EMBOSS_BOTTOM,
        EMBOSS_TOP,
        NORMAL,
        OUTLINE_THIN,
        SHADOW_THICK,
        SHADOW_THIN;
        private FontType() {
        }
    }

}

