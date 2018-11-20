package net.minecraft.client.gui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Gui
{
    public static final ResourceLocation optionsBackground = new ResourceLocation("Vodka/icon/options_background.png");
    public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
    public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
    public float zLevel;

    public void drawHorizontalLine(int startX, int endX, int y, int color)
    {
        if (endX < startX)
        {
            int i = startX;
            startX = endX;
            endX = i;
        }
        drawRect(startX, y, endX + 1, y + 1, color);
    }

    public static void drawVerticalLine(double xPos, double yPos, double xSize, double thickness, Color color)
    {
        Tessellator tesselator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tesselator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(xPos - xSize, yPos - thickness / 2.0D, 0.0D)
                .color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F)
                .endVertex();
        worldRenderer.pos(xPos - xSize, yPos + thickness / 2.0D, 0.0D)
                .color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F)
                .endVertex();
        worldRenderer.pos(xPos + xSize, yPos + thickness / 2.0D, 0.0D)
                .color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F)
                .endVertex();
        worldRenderer.pos(xPos + xSize, yPos - thickness / 2.0D, 0.0D)
                .color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F)
                .endVertex();
        tesselator.draw();
    }

    public static void drawBorderedRect(int x, int y, int x1, int y1, int size, Color borderC, Color insideC)
    {
        drawRect(x + size, y + size, x1 - size, y1 - size, insideC);
        drawRect(x + size, y + size, x1, y, borderC);
        drawRect(x, y, x + size, y1, borderC);
        drawRect(x1, y1, x1 - size, y + size, borderC);
        drawRect(x, y1 - size, x1, y1, borderC);
    }

    public static void drawBorderedRect(int x, int y, int x1, int y1, int size, int borderC, int insideC)
    {
        drawRect(x + size, y + size, x1 - size, y1 - size, insideC);
        drawRect(x + size, y + size, x1, y, borderC);
        drawRect(x, y, x + size, y1, borderC);
        drawRect(x1, y1, x1 - size, y + size, borderC);
        drawRect(x, y1 - size, x1, y1, borderC);
    }

    public static void drawBorderedRect(float x, float y, float x1, float y1, float size, int borderC, int insideC)
    {
        drawRect(x + size, y + size, x1 - size, y1 - size, insideC);
        drawRect(x + size, y + size, x1, y, borderC);
        drawRect(x, y, x + size, y1, borderC);
        drawRect(x1, y1, x1 - size, y + size, borderC);
        drawRect(x, y1 - size, x1, y1, borderC);
    }

    public static void drawRainbowRectHorizontal(int x, int y, int x1, int y1, int segments, float alpha)
    {
        if (segments < 1) {
            segments = 1;
        }
        if (segments > x1 - x) {
            segments = x1 - x;
        }
        int segmentLength = (x1 - x) / segments;
        long time = System.nanoTime();
        for (int i = 0; i < segments; i++) {
            drawRect(x + segmentLength * i, y, x + (segmentLength + 1) * i, y1, rainbow(time, i, alpha));
        }
    }

    public static void drawRainbowRectVertical(int x, int y, int x1, int y1, int segments, float alpha)
    {
        if (segments < 1) {
            segments = 1;
        }
        if (segments > y1 - y) {
            segments = y1 - y;
        }
        int segmentLength = (y1 - y) / segments;
        long time = System.nanoTime();
        for (int i = 0; i < segments; i++) {
            Minecraft.getMinecraft().ingameGUI.drawGradientRect(x, y + segmentLength * i - 1, x1, y + (segmentLength + 1) * i, rainbow(time, i * 0.1F, alpha).getRGB(), rainbow(time, (i + 0.1F) * 0.1F, alpha).getRGB());
        }
    }

    public static Color rainbow(long time, float count, float fade)
    {
        float hue = ((float)time + (1.0F + count) * 2.0E8F) / 1.0E10F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
        Color c = new Color((int)color);
        return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
    }

    public static void drawVerticalLine(int x, int startY, int endY, int color)
    {
        if (endY < startY)
        {
            int i = startY;
            startY = endY;
            endY = i;
        }
        drawRect(x, startY + 1, x + 1, endY, color);
    }

    public static void drawLine(float x, float y, float x2, float y2, Color color)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0F);
        GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
        worldrenderer.begin(1, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x, y, 0.0D).endVertex();
        worldrenderer.pos(x2, y2, 0.0D).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect(int left, int top, int right, int bottom, int color)
    {
        if (left < right)
        {
            int i = left;
            left = right;
            right = i;
        }
        if (top < bottom)
        {
            int j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (color >> 24 & 0xFF) / 255.0F;
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect(float left, float top, float right, float bottom, Color color)
    {
        if (left < right)
        {
            float i = left;
            left = right;
            right = i;
        }
        if (top < bottom)
        {
            float j = top;
            top = bottom;
            bottom = j;
        }
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect(float left, float top, float right, float bottom, int color)
    {
        if (left < right)
        {
            float i = left;
            left = right;
            right = i;
        }
        if (top < bottom)
        {
            float j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (color >> 24 & 0xFF) / 255.0F;
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect(int left, int top, int right, int bottom, Color color)
    {
        if (left < right)
        {
            int i = left;
            left = right;
            right = i;
        }
        if (top < bottom)
        {
            int j = top;
            top = bottom;
            bottom = j;
        }
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
    {
        float f = (startColor >> 24 & 0xFF) / 255.0F;
        float f1 = (startColor >> 16 & 0xFF) / 255.0F;
        float f2 = (startColor >> 8 & 0xFF) / 255.0F;
        float f3 = (startColor & 0xFF) / 255.0F;
        float f4 = (endColor >> 24 & 0xFF) / 255.0F;
        float f5 = (endColor >> 16 & 0xFF) / 255.0F;
        float f6 = (endColor >> 8 & 0xFF) / 255.0F;
        float f7 = (endColor & 0xFF) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(right, top, this.zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, top, this.zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
        worldrenderer.pos(right, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public void drawGradientBorderedRect(float left, float top, float right, float bottom, int startColor, int endColor, float borderSize, int borderColor)
    {
        drawGradientRect(left, top, right, bottom, startColor, endColor);

        drawRect(left, top + borderSize, left + borderSize, bottom - borderSize, borderColor);
        drawRect(right, top + borderSize, right - borderSize, bottom - borderSize, borderColor);

        drawRect(left, top, right, top + borderSize, borderColor);
        drawRect(left, bottom, right, bottom - borderSize, borderColor);
    }

    public void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor)
    {
        float f = (startColor >> 24 & 0xFF) / 255.0F;
        float f1 = (startColor >> 16 & 0xFF) / 255.0F;
        float f2 = (startColor >> 8 & 0xFF) / 255.0F;
        float f3 = (startColor & 0xFF) / 255.0F;
        float f4 = (endColor >> 24 & 0xFF) / 255.0F;
        float f5 = (endColor >> 16 & 0xFF) / 255.0F;
        float f6 = (endColor >> 8 & 0xFF) / 255.0F;
        float f7 = (endColor & 0xFF) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(right, top, this.zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, top, this.zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
        worldrenderer.pos(right, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawStringWithShadow(text, x - fontRendererIn.getStringWidth(text) / 2, y, color);
    }

    public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawStringWithShadow(text, x, y, color);
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x + 0, y + height, this.zLevel).tex((textureX + 0) * f, (textureY + height) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, this.zLevel).tex((textureX + width) * f, (textureY + height) * f1).endVertex();
        worldrenderer.pos(x + width, y + 0, this.zLevel).tex((textureX + width) * f, (textureY + 0) * f1).endVertex();
        worldrenderer.pos(x + 0, y + 0, this.zLevel).tex((textureX + 0) * f, (textureY + 0) * f1).endVertex();
        tessellator.draw();
    }

    public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(xCoord + 0.0F, yCoord + maxV, this.zLevel).tex((minU + 0) * f, (minV + maxV) * f1).endVertex();
        worldrenderer.pos(xCoord + maxU, yCoord + maxV, this.zLevel).tex((minU + maxU) * f, (minV + maxV) * f1).endVertex();
        worldrenderer.pos(xCoord + maxU, yCoord + 0.0F, this.zLevel).tex((minU + maxU) * f, (minV + 0) * f1).endVertex();
        worldrenderer.pos(xCoord + 0.0F, yCoord + 0.0F, this.zLevel).tex((minU + 0) * f, (minV + 0) * f1).endVertex();
        tessellator.draw();
    }

    public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(xCoord + 0, yCoord + heightIn, this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMaxV()).endVertex();
        worldrenderer.pos(xCoord + widthIn, yCoord + heightIn, this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMaxV()).endVertex();
        worldrenderer.pos(xCoord + widthIn, yCoord + 0, this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMinV()).endVertex();
        worldrenderer.pos(xCoord + 0, yCoord + 0, this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }

    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight)
    {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0D).tex(u * f, (v + height) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0D).tex((u + width) * f, (v + height) * f1).endVertex();
        worldrenderer.pos(x + width, y, 0.0D).tex((u + width) * f, v * f1).endVertex();
        worldrenderer.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight)
    {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0D).tex(u * f, (v + vHeight) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0D).tex((u + uWidth) * f, (v + vHeight) * f1).endVertex();
        worldrenderer.pos(x + width, y, 0.0D).tex((u + uWidth) * f, v * f1).endVertex();
        worldrenderer.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static void drawCircle(int xx, int yy, int radius, Color col)
    {
        int sections = 70;
        double dAngle = 6.283185307179586D / sections;

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glBegin(2);
        for (int i = 0; i < sections; i++)
        {
            float x = (float)(radius * Math.cos(i * dAngle));
            float y = (float)(radius * Math.sin(i * dAngle));

            GL11.glColor4f(col.getRed() / 255.0F, col.getGreen() / 255.0F, col.getBlue() / 255.0F, col.getAlpha() / 255.0F);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawCircle(float xx, float yy, float radius, Color col)
    {
        int sections = 70;
        double dAngle = 6.283185307179586D / sections;

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0F);
        GL11.glShadeModel(7425);
        GL11.glBegin(2);
        for (int i = 0; i < sections; i++)
        {
            float x = (float)(radius * Math.cos(i * dAngle));
            float y = (float)(radius * Math.sin(i * dAngle));

            GL11.glColor4f(col.getRed() / 255.0F, col.getGreen() / 255.0F, col.getBlue() / 255.0F, col.getAlpha() / 255.0F);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawCircle(float xx, float yy, float radius, Color col, float width, float position, float round)
    {
        int sections = 100;
        double dAngle = round * 2.0F * 3.141592653589793D / sections;
        float x = 0.0F;float y = 0.0F;

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(width);
        GL11.glShadeModel(7425);
        GL11.glBegin(2);
        for (int i = (int)position; i < position + sections; i++)
        {
            x = (float)(radius * Math.cos(i * dAngle));
            y = (float)(radius * Math.sin(i * dAngle));

            GL11.glColor4f(col.getRed() / 255.0F, col.getGreen() / 255.0F, col.getBlue() / 255.0F, col.getAlpha() / 255.0F);
            GL11.glVertex2f(xx + x, yy + y);
        }
        for (int i = (int)(position + sections); i > (int)position; i--)
        {
            x = (float)(radius * Math.cos(i * dAngle));
            y = (float)(radius * Math.sin(i * dAngle));

            GL11.glColor4f(col.getRed() / 255.0F, col.getGreen() / 255.0F, col.getBlue() / 255.0F, col.getAlpha() / 255.0F);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawFilledCircle(int xx, int yy, float radius, Color col)
    {
        int sections = 50;
        double dAngle = 6.283185307179586D / sections;

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < sections; i++)
        {
            float x = (float)(radius * Math.sin(i * dAngle));
            float y = (float)(radius * Math.cos(i * dAngle));

            GL11.glColor4f(col.getRed() / 255.0F, col.getGreen() / 255.0F, col.getBlue() / 255.0F, col.getAlpha() / 255.0F);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void circle(float x, float y, float radius, int fill)
    {
        arc(x, y, 0.0F, 360.0F, radius, fill);
    }

    public static void circle(float x, float y, float radius, Color fill)
    {
        arc(x, y, 0.0F, 360.0F, radius, fill);
    }

    public static void arc(float x, float y, float start, float end, float radius, int color)
    {
        arcEllipse(x, y, start, end, radius, radius, color);
    }

    public static void arc(float x, float y, float start, float end, float radius, Color color)
    {
        arcEllipse(x, y, start, end, radius, radius, color);
    }

    public static void arcEllipse(float x, float y, float start, float end, float w, float h, int color)
    {
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);

        float temp = 0.0F;
        if (start > end)
        {
            temp = end;
            end = start;
            start = temp;
        }
        float var11 = (color >> 24 & 0xFF) / 255.0F;
        float var6 = (color >> 16 & 0xFF) / 255.0F;
        float var7 = (color >> 8 & 0xFF) / 255.0F;
        float var8 = (color & 0xFF) / 255.0F;

        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var6, var7, var8, var11);
        if (var11 > 0.5F)
        {
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0F);
            GL11.glBegin(3);
            for (float i = end; i >= start; i -= 4.0F)
            {
                float ldx = (float)Math.cos(i * 3.141592653589793D / 180.0D) * (w * 1.001F);
                float ldy = (float)Math.sin(i * 3.141592653589793D / 180.0D) * (h * 1.001F);
                GL11.glVertex2f(x + ldx, y + ldy);
            }
            GL11.glEnd();
            GL11.glDisable(2848);
        }
        GL11.glBegin(6);
        for (float i = end; i >= start; i -= 4.0F)
        {
            float ldx = (float)Math.cos(i * 3.141592653589793D / 180.0D) * w;
            float ldy = (float)Math.sin(i * 3.141592653589793D / 180.0D) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }
        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void arcEllipse(float x, float y, float start, float end, float w, float h, Color color)
    {
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);

        float temp = 0.0F;
        if (start > end)
        {
            temp = end;
            end = start;
            start = temp;
        }
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
        if (color.getAlpha() > 0.5F)
        {
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0F);
            GL11.glBegin(3);
            for (float i = end; i >= start; i -= 4.0F)
            {
                float ldx = (float)Math.cos(i * 3.141592653589793D / 180.0D) * (w * 1.001F);
                float ldy = (float)Math.sin(i * 3.141592653589793D / 180.0D) * (h * 1.001F);
                GL11.glVertex2f(x + ldx, y + ldy);
            }
            GL11.glEnd();
            GL11.glDisable(2848);
        }
        GL11.glBegin(6);
        for (float i = end; i >= start; i -= 4.0F)
        {
            float ldx = (float)Math.cos(i * 3.141592653589793D / 180.0D) * w;
            float ldy = (float)Math.sin(i * 3.141592653589793D / 180.0D) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }
        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawFilledCircle(float xx, float yy, float radius, Color col)
    {
        int sections = 50;
        double dAngle = 6.283185307179586D / sections;

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < sections; i++)
        {
            float x = (float)(radius * Math.sin(i * dAngle));
            float y = (float)(radius * Math.cos(i * dAngle));

            GL11.glColor4f(col.getRed() / 255.0F, col.getGreen() / 255.0F, col.getBlue() / 255.0F, col.getAlpha() / 255.0F);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawFilledCircle(int xx, int yy, float radius, int col)
    {
        float f = (col >> 24 & 0xFF) / 255.0F;
        float f1 = (col >> 16 & 0xFF) / 255.0F;
        float f2 = (col >> 8 & 0xFF) / 255.0F;
        float f3 = (col & 0xFF) / 255.0F;

        int sections = 50;
        double dAngle = 6.283185307179586D / sections;

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(6);
        for (int i = 0; i < sections; i++)
        {
            float x = (float)(radius * Math.sin(i * dAngle));
            float y = (float)(radius * Math.cos(i * dAngle));

            GL11.glColor4f(f1, f2, f3, f);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawFilledCircle(float xx, float yy, float radius, int col)
    {
        float f = (col >> 24 & 0xFF) / 255.0F;
        float f1 = (col >> 16 & 0xFF) / 255.0F;
        float f2 = (col >> 8 & 0xFF) / 255.0F;
        float f3 = (col & 0xFF) / 255.0F;

        int sections = 50;
        double dAngle = 6.283185307179586D / sections;

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < sections; i++)
        {
            float x = (float)(radius * Math.sin(i * dAngle));
            float y = (float)(radius * Math.cos(i * dAngle));

            GL11.glColor4f(f1, f2, f3, f);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawFilledCircle(int xx, int yy, float radius, int col, int xLeft, int yAbove, int xRight, int yUnder)
    {
        float f = (col >> 24 & 0xFF) / 255.0F;
        float f1 = (col >> 16 & 0xFF) / 255.0F;
        float f2 = (col >> 8 & 0xFF) / 255.0F;
        float f3 = (col & 0xFF) / 255.0F;

        int sections = 50;
        double dAngle = 6.283185307179586D / sections;

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < sections; i++)
        {
            float x = (float)(radius * Math.sin(i * dAngle));
            float y = (float)(radius * Math.cos(i * dAngle));

            float xEnd = xx + x;
            float yEnd = yy + y;
            if (xEnd < xLeft) {
                xEnd = xLeft;
            }
            if (xEnd > xRight) {
                xEnd = xRight;
            }
            if (yEnd < yAbove) {
                yEnd = yAbove;
            }
            if (yEnd > yUnder) {
                yEnd = yUnder;
            }
            GL11.glColor4f(f1, f2, f3, f);
            GL11.glVertex2f(xEnd, yEnd);
        }
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
}
