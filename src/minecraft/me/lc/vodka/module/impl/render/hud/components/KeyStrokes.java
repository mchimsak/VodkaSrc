package me.lc.vodka.module.impl.render.hud.components;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventRender2D;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.font.Fonts;
import me.lc.vodka.module.impl.render.hud.HUD;
import me.lc.vodka.utils.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import java.util.List;

import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class KeyStrokes {
    Runnable r2 = ()-> System.out.println("233333333");

        ScaledResolution s1 = new ScaledResolution(Minecraft.getMinecraft());
        ResourceLocation image;
        private List<Long> clicks = new ArrayList();
        private boolean wasPressed = true;
        private long lastPress = 0L;
        private int button;
        
        @EventTarget
        public void Render(EventRender2D e) {
                if(!HUD.keystrokes.getBooleanValue())
                        return;
                this.image = new ResourceLocation("Vodka/icon/keystrokes/tx.jpg" );

                
                
                RenderUtil.drawImage(image, 45, s1.getScaledHeight() -140, 60, 60);//image

                if(Minecraft.getMinecraft().gameSettings.keyBindForward.pressed) {
                	Gui.drawRect(65, s1.getScaledHeight() -140, 85, s1.getScaledHeight() -120, -1610612735);//W
                }
                
                if(Minecraft.getMinecraft().gameSettings.keyBindLeft.pressed) {
                	Gui.drawRect(45, s1.getScaledHeight() -120, 65, s1.getScaledHeight() -100, -1610612735);//A
                }

                if(Minecraft.getMinecraft().gameSettings.keyBindBack.pressed) {
                	Gui.drawRect(65, s1.getScaledHeight() -120, 85, s1.getScaledHeight() -100, -1610612735);//S
                }

                if(Minecraft.getMinecraft().gameSettings.keyBindRight.pressed) {
                	Gui.drawRect(85, s1.getScaledHeight() -120, 105, s1.getScaledHeight() -100, -1610612735);//D
                }
                
                if(Minecraft.getMinecraft().gameSettings.keyBindJump.pressed) {
                	Gui.drawRect(65, s1.getScaledHeight() -100, 85, s1.getScaledHeight() -80, -1610612735);//Space
                }
                
                if(Minecraft.getMinecraft().gameSettings.keyBindAttack.pressed) {
                	Gui.drawRect(45, s1.getScaledHeight() -140, 65, s1.getScaledHeight() -120, -1610612735);//LMB
                }
                
                if(Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed) {
                	Gui.drawRect( 85, s1.getScaledHeight() -140, 105, s1.getScaledHeight() -120, -1610612735);//RMB
                }

                Fonts.clickgui.drawString("LMB", 45, s1.getScaledHeight() -140, rainbowModuleList(5000, 15));
                Fonts.clickgui.drawString("RMB", 85, s1.getScaledHeight() -140,  rainbowModuleList(5000, 15));
                Fonts.clickgui.drawString("W", 65, s1.getScaledHeight() -140,  rainbowModuleList(5000, 15));
                Fonts.clickgui.drawString("A", 45, s1.getScaledHeight() -120,  rainbowModuleList(5000, 15));
                Fonts.clickgui.drawString("S", 65, s1.getScaledHeight() -120, rainbowModuleList(5000, 15));
                Fonts.clickgui.drawString("D", 85, s1.getScaledHeight() -120, rainbowModuleList(5000, 15));
                Fonts.clickgui.drawString("SPACE", 65, s1.getScaledHeight() -100,rainbowModuleList(5000, 15));
                Fonts.clickgui.drawString("CPS"+getCPS(), 45, s1.getScaledHeight() -100, rainbowModuleList(5000, 15));
        }
        
        @EventTarget
        public void onUpdate (EventUpdate e) {
        	boolean pressed = Mouse.isButtonDown(this.button);
        	this.button = button;
            if (pressed != this.wasPressed)
            {
              this.wasPressed = pressed;
              this.lastPress = System.currentTimeMillis();
              if (pressed) {
                this.clicks.add(Long.valueOf(this.lastPress));
              }
            }
        }
        
        private int getCPS()
        {
          long time = System.currentTimeMillis();
          Iterator<Long> iterator = this.clicks.iterator();
          while (iterator.hasNext()) {
            if (((Long)iterator.next()).longValue() + 1000L < time) {
              iterator.remove();
            }
          }
          return this.clicks.size();
        }
        
        private int rainbowModuleList(int speed, int offset)
        {
                float color = (System.currentTimeMillis() + offset) % speed;
                color /= speed;
                return Color.getHSBColor(color, 1f, 1f).getRGB();
        }

}
