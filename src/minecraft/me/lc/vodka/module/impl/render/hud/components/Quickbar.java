package me.lc.vodka.module.impl.render.hud.components;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventRender2D;
import me.lc.vodka.font.Fonts;
import me.lc.vodka.module.impl.render.NameProtect;
import me.lc.vodka.module.impl.render.hud.HUD;
import me.lc.vodka.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class Quickbar {
    Runnable r2 = ()-> System.out.println("233333333");

    public static Minecraft mc = Minecraft.getMinecraft();
        @EventTarget
        public void onUpdate(EventRender2D e) {
            if(!HUD.quickbar.getBooleanValue())
                    return;
            Minecraft mc = Minecraft.getMinecraft();
            FontRenderer fr = mc.fontRendererObj;
            ScaledResolution sr = new ScaledResolution(this.mc);
            String name = mc.thePlayer.getName();
            sr.getScaledHeight();
            sr.getScaledWidth();
            int right = sr.getScaledWidth();
            int ping;
            int index = 0;
            long x = 0;
            if (mc.isSingleplayer()) {
                ping = 0;
            } else {
                ping = (int) mc.getCurrentServerData().pingToServer;
            }
            if(Vodka.INSTANCE.MODULE_MANAGER.getModule("NameProtect").isToggled()) {
                    name = NameProtect.name;
            }
            Gui.drawRect(0, sr.getScaledHeight() - 23, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0, 0, 0, 100).getRGB());

            int i = 8;

            if (mc.thePlayer.inventory.currentItem == 0) {
                Gui.drawRect((sr.getScaledWidth() / 2) - 91 + mc.thePlayer.inventory.currentItem * 20,
                        sr.getScaledHeight() - 23, (sr.getScaledWidth() / 2) + 91 - 20 * i, sr.getScaledHeight(),
                        Integer.MAX_VALUE);
            } else {
                Gui.drawRect((sr.getScaledWidth() / 2) - 91 + mc.thePlayer.inventory.currentItem * 20,
                        sr.getScaledHeight() - 23,
                        (sr.getScaledWidth() / 2) + 91 - 20 * (8 - mc.thePlayer.inventory.currentItem),
                        sr.getScaledHeight(), Integer.MAX_VALUE);
            }

            Fonts.clickgui.drawString("FPS:", 4, sr.getScaledHeight() - 20,
                    ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
            Fonts.clickgui.drawString("" + Minecraft.debugFPS, 28, sr.getScaledHeight() - 20, 0xFFFAFA);
            Fonts.clickgui.drawString("User: " + name, sr.getScaledWidth() - 120,
                    sr.getScaledHeight() - 20, ColorUtils.rainbowEffekt(index + x * 20000000L, 1.0F).getRGB());
            Fonts.clickgui.drawString("XYZ:", 4, sr.getScaledHeight() - 10,
                    ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
            Fonts.clickgui.drawString(
                    "" + String.valueOf(Math.round(mc.thePlayer.posX)) + ", "
                            + ("" + String.valueOf(Math.round(mc.thePlayer.posY) + ", "))
                            + ("" + String.valueOf(Math.round(mc.thePlayer.posZ) + " ")),
                    28, sr.getScaledHeight() - 10, 0xFFFAFA);
            String var1 = "IP: ";
            Fonts.clickgui.drawString(var1, right - 120, sr.getScaledHeight() - 10,
                    ColorUtils.rainbowEffekt(index + x * 20000000L, 1.0F).getRGB());
            Fonts.clickgui.drawString(
                    mc.isSingleplayer() ? " NONE" : mc.getCurrentServerData().serverIP.toLowerCase(), right - 105,
                    sr.getScaledHeight() - 10, 0xFFFAFA);

            Fonts.clickgui.drawString("Ping", 50, sr.getScaledHeight() - 20,
					ColorUtils.rainbowEffekt(index + x * 20000000L, 1.0F).getRGB());
            Fonts.clickgui.drawString(ping+"ms", 80, sr.getScaledHeight() - 20, 0xFFFAFA);
			
        }

}