package me.lc.vodka.module.impl.movement;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventRender3D;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.utils.Colors;
import me.lc.vodka.utils.util.PlayerUtil;
import me.lc.vodka.utils.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class Fly extends Module {

    Runnable r2 = ()-> System.out.println("233333333");

        public Setting Mod;
        public Setting Vanilla;
        public static ArrayList<String> options = new ArrayList<>();
        public Setting flyHackSpeed;
        private int counter;
        private int delay = 3;
        public Fly() {
            super("Fly",Keyboard.KEY_F,Category.MOVEMENT);
            options.add(0, "Cubecraft");
            options.add(1,"Vanilla");
            options.add(2,"Hypixel");
            options.add(3,"AACv1");
            options.add(4,"AACv3");
            Vodka.INSTANCE.SETTING_MANAGER.addSetting(Mod = (new Setting(this,"MOD","Vanilla",options)));
            Vodka.INSTANCE.SETTING_MANAGER.addSetting( flyHackSpeed = (new Setting( this, "Vanillaspeed",0.1D,0.1D,1.0D,false )));
        }

    @EventTarget
    public void onUpdate(EventUpdate e) {

        switch(Mod.getCurrentOptionIndex())
        {
            case 0://Cubecraft
                mc.thePlayer.capabilities.isFlying = true;
                setTimerSpeed(0.29F);
                break;

            case 1://Vanilla
                this.mc.thePlayer.capabilities.isFlying = true;

                if (this.mc.gameSettings.keyBindJump.isPressed()) {
                      this.mc.thePlayer.motionY += 0.2D;
                     }
                if (this.mc.gameSettings.keyBindSneak.isPressed()) {
                      this.mc.thePlayer.motionY -= 0.2D;
                      }
                if (this.mc.gameSettings.keyBindForward.isPressed()) {
                      this.mc.thePlayer.capabilities.setFlySpeed((float) flyHackSpeed.getCurrentValue());
                      }
                break;

            case 2://Hypixel
                this.mc.thePlayer.capabilities.isFlying = false;
                net.minecraft.util.Timer var10000 = this.mc.timer;
                net.minecraft.util.Timer.timerSpeed = 1.1F;
                this.mc.thePlayer.motionY = 0.0D;
                if (this.mc.thePlayer.isMoving()) {
                    if (new Random().nextBoolean()) {
                        this.mc.thePlayer.speedInAir = 0.0215F;
                    } else {
                        this.mc.thePlayer.speedInAir = 0.0195F;
                    }
                }
                this.counter += 1;
                for (int i = 0; i < 2; i++) {
                    if (this.counter == 1)
                    {
                        this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 8.0E-6D, this.mc.thePlayer.posZ);
                    }
                    else if (this.counter == 2)
                    {
                        this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 8.0E-6D, this.mc.thePlayer.posZ);
                        this.counter = 0;
                    }
                }
                if (this.timer.hasTimeElapsed(500L))
                {
                    this.timer.reset();
                    if (this.mc.thePlayer.movementInput.jump) {
                        this.mc.thePlayer.motionY += 0.5D;
                    }
                    if (this.mc.thePlayer.movementInput.sneak) {
                        this.mc.thePlayer.motionY -= 0.5D;
                    }
                }
                 break;

            case 3://AACv1
                if (this.mc.thePlayer.fallDistance > 3.5D)
                {
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                    this.mc.thePlayer.motionY += 1.59D;
                    this.mc.thePlayer.fallDistance = 0.0F;
                    if (PlayerUtil.MovementInput()) {
                        PlayerUtil.toFwd(0.07D);
                    }
                }
                break;
            case 4://AACv3
                if (this.delay == 2) {
                    this.mc.thePlayer.motionY = 0.075D;
                } else if (this.delay > 2) {
                    this.delay = 0;
                }
                this.delay += 1;
                break;
        }
    }


    @EventTarget
    public void a(EventRender3D e) {
        double var10000 = this.mc.thePlayer.posX;
        Minecraft.getMinecraft().getRenderManager();
        double x = var10000 - RenderManager.renderPosX;
        var10000 = this.mc.thePlayer.posY - 0.5D;
        Minecraft.getMinecraft().getRenderManager();
        double y = var10000 - RenderManager.renderPosY;
        var10000 = this.mc.thePlayer.posZ;
        Minecraft.getMinecraft().getRenderManager();
        double z = var10000 - RenderManager.renderPosZ;
            if(Mod.getCurrentOptionIndex() == 2) {
                Color color = new Color(Colors.BLUE.c);
                x -= 0.5;
                z -= 0.5;
                final double mid = 0.5;
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glTranslated(x + mid, y + mid, z + mid);
                GL11.glTranslated(-(x + mid), -(y + mid), -(z + mid));
                GL11.glDisable(3553);
                GL11.glEnable(2848);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
                GL11.glLineWidth(2.0f);
                RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 0.05, z + 1.0));
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.5f);
                RenderUtil.drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 0.05, z + 1.0));
                GL11.glDisable(2848);
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
    }
    public void onDisable() {
        super.onDisable();
            this.mc.thePlayer.capabilities.setFlySpeed(0.1F);
            mc.thePlayer.capabilities.isFlying = false;
            setTimerSpeed(1.0F);
        this.delay = 1;
        super.onDisable();
        if (PlayerUtil.MovementInput()) {
            PlayerUtil.setSpeed(0.0);
        }
        this.mc.thePlayer.capabilities.isFlying = false;
        this.mc.timer.timerSpeed = 1.0f;
    }
    public static void setTimerSpeed(float timerSpeed) {
        try {
            Class<?> mcClass = Minecraft.class;
            Field timerField = mcClass.getDeclaredField("timer"); // mc.timer
            timerField.setAccessible(true);
            try {
                Object timer = timerField.get(Minecraft.getMinecraft());
                Class<?> timerClass = timer.getClass();
                Field timerSpeedField = timerClass.getDeclaredField("timerSpeed"); // mc.timer.timerSpeed
                timerSpeedField.setAccessible(true);
                timerSpeedField.setFloat(timer, timerSpeed); // mc.timer.timerSpeed = timerSpeed
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


}
