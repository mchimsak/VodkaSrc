package me.lc.vodka.module.impl.render.hud.components;

import java.awt.*;
import java.util.ArrayList;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventRender2D;
import me.lc.vodka.font.Fonts;
import me.lc.vodka.module.Module;
import me.lc.vodka.module.impl.render.hud.HUD;
import me.lc.vodka.utils.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class ToggledModules {
    Runnable r2 = ()-> System.out.println("233333333");
    @EventTarget
    public void onRender(EventRender2D e) {

        if (!HUD.arrayList.getBooleanValue())
            return;

        ArrayList<Module> mods = Vodka.INSTANCE.MODULE_MANAGER.getToggledModules();
        mods.sort((o1, o2) -> Fonts.clickgui.getStringWidth(o2.getName()) - Fonts.clickgui.getStringWidth(o1.getName()));
        int y = 2;
        ScaledResolution s1 = new ScaledResolution(Minecraft.getMinecraft());
        for (Module m : mods) {
            int mWidth = Fonts.clickgui.getStringWidth(m.getName());
            if(HUD.al_rect.getBooleanValue()) {
                RenderUtil.drawRect(e.width - mWidth - 3, y - 1
                        , e.width - mWidth - 1, y + Fonts.clickgui.getStringHeight(m.getName()) + 3
                        , rainbowModuleList(5000, 15 * y));//Rect
            }
            Runnable r2 = ()-> System.out.println("233333333");

            if(HUD.al_background.getBooleanValue()) {
                RenderUtil.drawRect(e.width - mWidth - 1, y - 1
                        , e.width - mWidth + Fonts.clickgui.getStringWidth(m.getName()), y + Fonts.clickgui.getStringHeight(m.getName()) + 3
                        , new Color(0, 0, 0,(int)HUD.al_bg_a.getCurrentValue()).getRGB());//Background
            }

            Fonts.clickgui.drawString( m.getName(), e.width - mWidth , y, rainbowModuleList(5000, 15 * y));//Font
            y += 9 + 2;
        }
    }

    private int rainbowModuleList(int speed, int offset)
    {
        float color = (System.currentTimeMillis() + offset) % speed;
        color /= speed;
        return Color.getHSBColor(color, 1f, 1f).getRGB();
    }


}
