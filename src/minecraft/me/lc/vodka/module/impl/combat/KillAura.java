package me.lc.vodka.module.impl.combat;

import java.awt.*;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventMotion;
import me.lc.vodka.event.events.EventRender;
import me.lc.vodka.helper.TimeHelper;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.module.impl.other.AntiBots;
import me.lc.vodka.module.impl.other.Teams;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.ui.notifications.Notification;
import me.lc.vodka.ui.notifications.NotificationManager;
import me.lc.vodka.ui.notifications.NotificationType;
import me.lc.vodka.utils.Colors;
import me.lc.vodka.utils.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;

public class KillAura extends Module
{
    TimeHelper kms;
    public ArrayList<EntityLivingBase> targets;
    public ArrayList<EntityLivingBase> attackedTargets;
    public static EntityLivingBase curTarget;
    public static EntityLivingBase curBot;
    public static EntityLivingBase lastTarget;
    public static Setting autoBlock;
    public static Setting reach;
    public Setting attackDelay;
    public Setting cracksize;
    public static Setting gomme;
    public static Setting slow;
    public Setting attackRandomDelay;
    public Setting attackPlayers;
    public Setting attackAnimals;
    public Setting attackMobs;
    public Setting aRayTrace;
    public Setting blockRayTrace;
    public Setting rotations;
    public Setting switchDelay;
    public Setting maxTargets;
    public Setting noswing;
    public Setting startDelay;
    public Setting openInv;
    public Setting invisible;
    public Setting espMode;
    public static ArrayList<String> options = new ArrayList<>();
    private TimeHelper test;
    private boolean doBlock;
    private boolean unBlock;
    private Random random;
    private long lastMs;
    private int delay;
    private float curYaw;
    private float curPitch;
    private int tick;
    private int a = 0;
    
    public KillAura() {
        super("KillAura", 0,Category.COMBAT);
        this.kms = new TimeHelper();
        this.targets = new ArrayList<EntityLivingBase>();
        this.attackedTargets = new ArrayList<EntityLivingBase>();
        KillAura.curTarget = null;
        KillAura.curBot = null;
        KillAura.lastTarget = null;
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(autoBlock = (new Setting(this,"AutoBlock",true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(reach = (new Setting(this,"Reach",3.8D,0.1D,7.0D,false)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(gomme = (new Setting(this,"Gomme",true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(slow = (new Setting(this,"SlowDown",false)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(attackDelay = (new Setting(this,"AttackDelay",120.0D,0.0D,1000.0D,true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(cracksize = (new Setting(this,"Delay",1.0D,1.0D,10.0D,true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(attackRandomDelay = (new Setting(this,"Random",true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(attackPlayers = (new Setting(this,"AttackPlayers",true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(attackAnimals = (new Setting(this,"AttackAnimals",true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(attackMobs = (new Setting(this,"AttackMobs",true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(aRayTrace = (new Setting(this,"RayTrace",true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(blockRayTrace = (new Setting(this,"BlockRayTrace",true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(rotations = (new Setting(this,"Rotations",true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(switchDelay = (new Setting(this,"switchDelay",2.0D,0.0D,10.0D,true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(maxTargets = (new Setting(this,"Targets",2.0D,1.0D,20.0D,true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(noswing = (new Setting(this,"NoSwing",false)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(startDelay = (new Setting(this,"StartDelay",true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(openInv = (new Setting(this,"AttackInInventory",true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(invisible = (new Setting(this,"AttackInvisibles",false)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(espMode = new Setting(this,"ESP","Box",options));
        this.test = new TimeHelper();
        this.doBlock = false;
        this.unBlock = false;
        this.random = new Random();
        this.delay = 0;
        this.curYaw = 0.0f;
        this.curPitch = 0.0f;
        this.tick = 0;
        options.add(0,"None");
        options.add(1,"Box");
        options.add(2,"Flat Box");
    }
    
    @EventTarget
    public void onRender(EventRender render) {
        if (KillAura.curTarget == null || this.espMode.getCurrentOptionIndex() ==0) {
            return;
        }
        Color color = new Color(Colors.BLUE.c);
        if (KillAura.curTarget.hurtTime > 0) {
            color = new Color(FlatColors.RED.c);
        }
        if (this.espMode.getCurrentOptionIndex() == 1) {
            this.mc.getRenderManager();
            double x = KillAura.curTarget.lastTickPosX + (KillAura.curTarget.posX - KillAura.curTarget.lastTickPosX) * this.mc.timer.renderPartialTicks - RenderManager.renderPosX;
            this.mc.getRenderManager();
            double y = KillAura.curTarget.lastTickPosY + (KillAura.curTarget.posY - KillAura.curTarget.lastTickPosY) * this.mc.timer.renderPartialTicks - RenderManager.renderPosY;
            this.mc.getRenderManager();
            double z = KillAura.curTarget.lastTickPosZ + (KillAura.curTarget.posZ - KillAura.curTarget.lastTickPosZ) * this.mc.timer.renderPartialTicks - RenderManager.renderPosZ;
            if (KillAura.curTarget instanceof EntityPlayer) {
                x -= 0.275;
                z -= 0.275;
                y += KillAura.curTarget.getEyeHeight() - 0.225 - (KillAura.curTarget.isSneaking() ? 0.25 : 0.0);
                final double mid = 0.275;
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                final double rotAdd = -0.25 * (Math.abs(KillAura.curTarget.rotationPitch) / 90.0f);
                GL11.glTranslated(0.0, rotAdd, 0.0);
                GL11.glTranslated(x + mid, y + mid, z + mid);
                GL11.glRotated((double)(-KillAura.curTarget.rotationYaw % 360.0f), 0.0, 1.0, 0.0);
                GL11.glTranslated(-(x + mid), -(y + mid), -(z + mid));
                GL11.glTranslated(x + mid, y + mid, z + mid);
                GL11.glRotated((double)KillAura.curTarget.rotationPitch, 1.0, 0.0, 0.0);
                GL11.glTranslated(-(x + mid), -(y + mid), -(z + mid));
                GL11.glDisable(3553);
                GL11.glEnable(2848);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
                GL11.glLineWidth(1.0f);
                RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x - 0.0025, y - 0.0025, z - 0.0025, x + 0.55 + 0.0025, y + 0.55 + 0.0025, z + 0.55 + 0.0025));
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.5f);
                RenderUtil.drawBoundingBox(new AxisAlignedBB(x - 0.0025, y - 0.0025, z - 0.0025, x + 0.55 + 0.0025, y + 0.55 + 0.0025, z + 0.55 + 0.0025));
                GL11.glDisable(2848);
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
            else {
                final double width = KillAura.curTarget.getEntityBoundingBox().maxX - KillAura.curTarget.getEntityBoundingBox().minX;
                final double height = KillAura.curTarget.getEntityBoundingBox().maxY - KillAura.curTarget.getEntityBoundingBox().minY + 0.25;
                final float red = 0.0f;
                final float green = 0.5f;
                final float blue = 1.0f;
                final float alpha = 0.5f;
                final float lineRed = 0.0f;
                final float lineGreen = 0.5f;
                final float lineBlue = 1.0f;
                final float lineAlpha = 1.0f;
                final float lineWdith = 2.0f;
                RenderUtil.drawEntityESP(x, y, z, width, height, red, green, blue, alpha, lineRed, lineGreen, lineBlue, lineAlpha, lineWdith);
            }
        }
        else {
            this.mc.getRenderManager();
            double x = KillAura.curTarget.lastTickPosX + (KillAura.curTarget.posX - KillAura.curTarget.lastTickPosX) * this.mc.timer.renderPartialTicks - RenderManager.renderPosX;
            this.mc.getRenderManager();
            double y = KillAura.curTarget.lastTickPosY + (KillAura.curTarget.posY - KillAura.curTarget.lastTickPosY) * this.mc.timer.renderPartialTicks - RenderManager.renderPosY;
            this.mc.getRenderManager();
            double z = KillAura.curTarget.lastTickPosZ + (KillAura.curTarget.posZ - KillAura.curTarget.lastTickPosZ) * this.mc.timer.renderPartialTicks - RenderManager.renderPosZ;
            if (KillAura.curTarget instanceof EntityPlayer) {
                x -= 0.5;
                z -= 0.5;
                y += KillAura.curTarget.getEyeHeight() + 0.35 - (KillAura.curTarget.isSneaking() ? 0.25 : 0.0);
                final double mid = 0.5;
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                final double rotAdd = -0.25 * (Math.abs(KillAura.curTarget.rotationPitch) / 90.0f);
                GL11.glTranslated(x + mid, y + mid, z + mid);
                GL11.glRotated((double)(-KillAura.curTarget.rotationYaw % 360.0f), 0.0, 1.0, 0.0);
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
            else {
                final double width = KillAura.curTarget.getEntityBoundingBox().maxZ - KillAura.curTarget.getEntityBoundingBox().minZ;
                final double height = 0.1;
                final float red = 0.0f;
                final float green = 0.5f;
                final float blue = 1.0f;
                final float alpha = 0.5f;
                final float lineRed = 0.0f;
                final float lineGreen = 0.5f;
                final float lineBlue = 1.0f;
                final float lineAlpha = 1.0f;
                final float lineWdith = 2.0f;
                RenderUtil.drawEntityESP(x, y + KillAura.curTarget.getEyeHeight() + 0.25, z, width, height, red, green, blue, alpha, lineRed, lineGreen, lineBlue, lineAlpha, lineWdith);
            }
        }
    }

    @EventTarget
    public void onPre(EventMotion event) {
        if(a == 0) {
        this.curYaw = this.mc.thePlayer.rotationYaw;
        this.curPitch = this.mc.thePlayer.rotationPitch;
        a = a+1;
        }
        if (!this.openInv.getBooleanValue() && this.mc.currentScreen != null) {
            this.lastMs = System.currentTimeMillis() + 1000L;
            this.test.setLastMs(1000);
            return;
        }
        this.doBlock = false;
        this.clear();
        this.findTargets(event);
        this.setCurTarget();
        if (this.aRayTrace.getBooleanValue() && KillAura.curTarget != null) {
            final RayTraceUtil rayCastUtil = new RayTraceUtil(KillAura.curTarget);
            if (rayCastUtil.getEntity() != KillAura.curTarget) {
                KillAura.curBot = rayCastUtil.getEntity();
            }
        }
        if (KillAura.curTarget != null) {
            this.switchDelay();
            final Random rand = new Random();
            if (this.rotations.getBooleanValue()) {
                if (this.tick == 0) {
                    this.doAttack();
                    KillAura.lastTarget = KillAura.curTarget;
                    event.pitch = this.curPitch;
                    event.yaw = this.curYaw + rand.nextInt(12) - 5.0f;
                    if (this.mc.thePlayer.getDistanceToEntity((Entity)KillAura.curTarget) < (double)KillAura.reach.getCurrentValue()) {
                        for (int i = 0; i < this.cracksize.getCurrentValue(); ++i) {
                            this.mc.effectRenderer.emitParticleAtEntity((Entity)KillAura.curTarget, EnumParticleTypes.CRIT);
                        }
                    }
                    this.mc.thePlayer.rotationYawHead = this.curYaw;
                }
                else {
                    event.yaw = this.mc.thePlayer.rotationYaw + (this.curYaw + rand.nextInt(9) - 5.0f - this.mc.thePlayer.rotationYaw) / 2.0f;
                }
            }
            else if (this.tick == 0) {
                this.doAttack();
                KillAura.lastTarget = KillAura.curTarget;
                if (this.mc.thePlayer.getDistanceToEntity((Entity)KillAura.curTarget) < KillAura.reach.getCurrentValue()) {
                    for (int i = 0; i < this.cracksize.getCurrentValue(); ++i) {
                        this.mc.effectRenderer.emitParticleAtEntity((Entity)KillAura.curTarget, EnumParticleTypes.CRIT);
                    }
                }
            }
            if ((boolean)KillAura.gomme.getBooleanValue() && this.mc.thePlayer.getDistanceSqToEntity((Entity)KillAura.curTarget) > (double)KillAura.reach.getCurrentValue() + 1.5) {
                this.mc.gameSettings.keyBindSprint.pressed = false;
                this.mc.thePlayer.setSprinting(false);
            }
        }
        else {
            this.targets.clear();
            this.attackedTargets.clear();
            this.lastMs = System.currentTimeMillis();
            if (this.unBlock) {
                this.mc.getNetHandler().addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                this.mc.thePlayer.itemInUseCount = 0;
                this.unBlock = false;
            }
        }
    }
    
    private void switchDelay() {
        if (KillAura.lastTarget != null && KillAura.lastTarget != KillAura.curTarget) {
            ++this.tick;
        }
        else {
            this.tick = 0;
        }
        if (this.tick > (double)this.switchDelay.getCurrentValue() + (this.attackRandomDelay.getBooleanValue() ? this.random.nextInt(3) : 0)) {
            this.tick = 0;
        }
    }
    
    private void setRotation() {
        final float[] rotations = CombatUtil.getRotations((Entity)KillAura.curTarget);
        this.curYaw = rotations[0];
        this.curPitch = rotations[1] + this.random.nextInt(12) - 5.0f;
        if (this.curPitch > 90.0f) {
            this.curPitch = 90.0f;
        }
        else if (this.curPitch < -90.0f) {
            this.curPitch = -90.0f;
        }
    }
    
    private void doAttack() {
        this.setRotation();
        final int ticks = 1;
        final int MAX_TICK = 100;
        if (this.mc.thePlayer.getDistanceToEntity((Entity)KillAura.curTarget) <= (double)KillAura.reach.getCurrentValue() && this.tick == 0 && this.test.isDelayComplete((long)(((Double)this.attackDelay.getCurrentValue()).intValue() - 20 + (this.attackRandomDelay.getBooleanValue() ? this.random.nextInt(50) : 0)))) {
            this.test.reset();
            final boolean miss = this.random.nextInt(50) + 1 == 38;
            if (this.mc.thePlayer.isBlocking() || (this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && (boolean)KillAura.autoBlock.getBooleanValue())) {
                this.mc.getNetHandler().addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                this.unBlock = false;
            }
            if (!this.mc.thePlayer.isBlocking() && !(boolean)KillAura.autoBlock.getBooleanValue() && this.mc.thePlayer.itemInUseCount > 0) {
                this.mc.thePlayer.itemInUseCount = 0;
            }
            this.attack(miss);
            this.doBlock = true;
            if (!miss) {
                this.attackedTargets.add(KillAura.curTarget);
            }
        }
        if (System.currentTimeMillis() - this.lastMs > this.delay + ticks * MAX_TICK) {
            this.lastMs = System.currentTimeMillis();
            this.delay = (int)(((Double)this.attackDelay.getCurrentValue()).intValue() + (this.attackRandomDelay.getBooleanValue() ? this.random.nextInt(100) : 0.0)) - ticks * MAX_TICK;
            if (this.delay < 0) {
                this.delay = 0;
            }
        }
    }
    
    @EventTarget
    public void onPost(EventMotion event) {
        if (KillAura.curTarget != null && ((this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && (boolean)KillAura.autoBlock.getBooleanValue()) || this.mc.thePlayer.isBlocking()) && this.doBlock) {
            this.mc.thePlayer.itemInUseCount = this.mc.thePlayer.getHeldItem().getMaxItemUseDuration();
            this.mc.getNetHandler().addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, this.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
            this.unBlock = true;
        }
    }
    
    private void attack(final boolean fake) {
        this.mc.thePlayer.onCriticalHit((Entity)KillAura.curTarget);
        if (!(boolean)this.noswing.getBooleanValue()) {
            this.mc.thePlayer.swingItem();
        }
        if (!fake) {
            this.doBlock = true;
            double eX = KillAura.curTarget.posX + (KillAura.curTarget.posX - KillAura.curTarget.lastTickPosX);
            eX += ((eX > KillAura.curTarget.posX) ? 0.5 : ((eX == KillAura.curTarget.posX) ? 0.0 : -0.5));
            double eZ = KillAura.curTarget.posZ + (KillAura.curTarget.posZ - KillAura.curTarget.lastTickPosZ);
            eZ += ((eZ > KillAura.curTarget.posZ) ? 0.5 : ((eZ == KillAura.curTarget.posZ) ? 0.0 : -0.5));
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity((Entity)((KillAura.curBot != null) ? KillAura.curBot : KillAura.curTarget), C02PacketUseEntity.Action.ATTACK));
            System.out.println((KillAura.curBot != null) ? KillAura.curBot : KillAura.curTarget);
            KillAura.curBot = null;
        }
    }
    
    private void setCurTarget() {
        for (final EntityLivingBase ent : this.targets) {
            if (!this.attackedTargets.contains(ent)) {
                KillAura.curTarget = ent;
                break;
            }
            if (this.attackedTargets.size() != this.targets.size()) {
                continue;
            }
            if (this.attackedTargets.size() > 0) {
                this.attackedTargets.clear();
            }
            this.setCurTarget();
        }
    }
    
    private void autoblock() {
    }
    
    private void clear() {
        KillAura.curTarget = null;
        KillAura.curBot = null;
        for (final EntityLivingBase ent : this.targets) {
            if (!this.isValidEntity(ent)) {
                this.targets.remove(ent);
                if (!this.attackedTargets.contains(ent)) {
                    continue;
                }
                this.attackedTargets.remove(ent);
            }
        }
    }
    @EventTarget
    private void findTargets(EventMotion event) {
        final int maxSize = (int)(this.maxTargets.getBooleanValue() ? 1.0 : this.maxTargets.getCurrentValue());        for (final Object o : this.mc.theWorld.loadedEntityList) {
            if (o instanceof EntityLivingBase) {
                final EntityLivingBase curEnt = (EntityLivingBase)o;
                if (this.isValidEntity(curEnt) && !this.targets.contains(curEnt)) {
                    this.targets.add(curEnt);
                }
            }
            if (this.targets.size() >= maxSize) {
                break;
            }
        }
        final float[] e1 = new float[1];
        final float[] e2 = new float[1];
        this.targets.sort((ent1, ent2) -> {
            e1[0] = CombatUtil.getRotations(ent1)[0];
            e2[0] = CombatUtil.getRotations(ent2)[0];
            return (e1[0] < e2[0]) ? 1 : ((e1[0] == e2[0]) ? 0 : -1);
        });
    }
    
    private boolean isValidEntity(final EntityLivingBase ent) {
        return ent != null && ent != this.mc.thePlayer && !ent.getName().equalsIgnoreCase("?6Dealer") && (!(ent instanceof EntityPlayer) || (boolean)this.attackPlayers.getBooleanValue()) && ((!(ent instanceof EntityAnimal) && !(ent instanceof EntitySquid) && !(ent instanceof EntityArmorStand)) || (boolean)this.attackAnimals.getBooleanValue()) && ((!(ent instanceof EntityMob) && !(ent instanceof EntityVillager) && !(ent instanceof EntityBat)) || (boolean)this.attackMobs.getBooleanValue()) && this.mc.thePlayer.getDistanceToEntity((Entity)ent) <= (double)KillAura.reach.getCurrentValue() + 1.0 && !ent.isDead && ent.getHealth() > 0.0f && (!ent.isInvisible() || (boolean)this.invisible.getBooleanValue()) && (!(ent instanceof EntityPlayer) || !AntiBots.isBot((EntityPlayer)ent)) && !this.mc.thePlayer.isDead && (!(ent instanceof EntityPlayer) || !Teams.isEnemy((EntityPlayer)ent)) && (!(boolean)this.blockRayTrace.getBooleanValue() || !ClientUtil.isBlockBetween(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight(), this.mc.thePlayer.posZ), new BlockPos(ent.posX, ent.posY + ent.getEyeHeight(), ent.posZ)));
    }


    public void onEnable() {
            if (this.startDelay.getBooleanValue()) {
                this.test.setLastMs( 100 );
            }
        NotificationManager.show(new Notification(NotificationType.INFO,"INFO","Killaura onEnable ",1));
        super.onEnable();
    }
    
    public void onDisable() {
        a = 0;
        this.targets.clear();
        this.attackedTargets.clear();
        KillAura.curTarget = null;
        this.mc.thePlayer.itemInUseCount = 0;
        this.mc.getNetHandler().addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        NotificationManager.show(new Notification(NotificationType.INFO,"INFO","Killaura onDisable ",1));
        super.onDisable();
    }
    
    private float getYawDifference(final float yaw, final EntityLivingBase target) {
        return CombatUtil.getYawDifference(yaw, CombatUtil.getRotations((Entity)target)[0]);
    }
}
