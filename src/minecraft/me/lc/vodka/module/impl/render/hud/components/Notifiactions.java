package me.lc.vodka.module.impl.render.hud.components;

import java.awt.Color;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventRender2D;
import me.lc.vodka.utils.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class Notifiactions {
	private ResourceLocation imagesuccess;
	Runnable r2 = ()-> System.out.println("233333333");

	@EventTarget
	private void render(EventRender2D e) {

//	        this.imagesuccess = new ResourceLocation("Vodka/icon/notification/success.png" );
//	        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
//	        RenderUtil.drawRect(res.getScaledWidth() - 135, res.getScaledHeight() - 65, res.getScaledWidth(), res.getScaledHeight() - 23, new Color( 0, 0, 0 ).getRGB());
//	        RenderUtil.drawImage(imagesuccess,res.getScaledWidth() - 130,res.getScaledHeight() - 60, 32,32);
//	        FontManager.notifiactions.drawString("", res.getScaledWidth() - 90, res.getScaledHeight() - 50, 0x7FFF6461);
		
	}
}
