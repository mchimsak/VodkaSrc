package me.lc.vodka.module.impl.render.hud.components;

import me.lc.vodka.font.Fonts;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventRender2D;
import me.lc.vodka.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;


import java.util.Iterator;

public class Status {
	Runnable r2 = ()-> System.out.println("233333333");

		public Minecraft mc = Minecraft.getMinecraft();

		@EventTarget
		public void o(EventRender2D e) {
			int pY = 0;
			pY = -23;
			ScaledResolution sr = new ScaledResolution(mc);
			Iterator localIterator = this.mc.getMinecraft().thePlayer.getActivePotionEffects().iterator();
			while (localIterator.hasNext()) {
				PotionEffect effect = (PotionEffect) localIterator.next();
				Potion potion = Potion.potionTypes[effect.getPotionID()];
				String PType = I18n.format(potion.getName(), new Object[0]);
				if (effect.getAmplifier() == 1) {
					PType = PType + "  II";
				} else if (effect.getAmplifier() == 2) {
					PType = PType + "  III";
				} else if (effect.getAmplifier() == 3) {
					PType = PType + "  IV";
				} else if (effect.getAmplifier() == 4) {
					PType = PType + "  V";
				} else if (effect.getAmplifier() == 5) {
					PType = PType + "  VI";
				} else if (effect.getAmplifier() == 6) {
					PType = PType + "  VII";
				} else if (effect.getAmplifier() == 7) {
					PType = PType + "  VIII";
				} else if (effect.getAmplifier() == 8) {
					PType = PType + "  IX";
				} else if (effect.getAmplifier() == 9) {
					PType = PType + "  X";
				} else if (effect.getAmplifier() >= 10) {
					PType = PType + "  X+";
				} else {
					PType = PType + "  I";
				}
				if ((effect.getDuration() < 600) && (effect.getDuration() > 300)) {
					PType = PType + " " + Potion.getDurationString(effect);
				} else if (effect.getDuration() < 300) {
					PType = PType + " " + Potion.getDurationString(effect);
				} else if (effect.getDuration() > 600) {
					PType = PType + " " + Potion.getDurationString(effect);
				}
				Fonts.clickgui.drawString(PType,
						sr.getScaledWidth() - Fonts.clickgui.getStringWidth(PType),
						sr.getScaledHeight() - Fonts.clickgui.FONT_HEIGHT+ pY, potion.getLiquidColor());
				pY -= 9;
			}
		}

	}

