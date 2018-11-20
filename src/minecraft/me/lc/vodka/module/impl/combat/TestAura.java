package me.lc.vodka.module.impl.combat;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventMotion;
import me.lc.vodka.helper.TimeHelper;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.utils.Utils;
import me.lc.vodka.utils.util.RayCastUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;

public class TestAura extends Module {

    private EntityLivingBase target;


    private Setting rangeValue;
    private Setting minCPSValue;
    private Setting maxCPSValue;
    private Setting targetChangeDelay;
    private Setting switchValue;
    private Setting raycast;
    private Setting debug;

    private TimeHelper cpsTimer = new TimeHelper();
    private TimeHelper targetChangeTimer = new TimeHelper();
    private float[] facing;

    public TestAura () {
        super("TestAura",0,Category.COMBAT);
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(rangeValue = (new Setting(this,"Reach",3.8D,0.1D,7.0D,true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(minCPSValue = (new Setting(this,"MinCPS",7, 1, 20,true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(maxCPSValue = (new Setting(this,"MaxCPS",12,1, 20,true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(targetChangeDelay = (new Setting(this,"TargetChangeDelay", 100, 0, 1000,true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(switchValue = (new Setting(this,"Switch",false)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(raycast = (new Setting(this,"RayCast",true)));
        Vodka.INSTANCE.SETTING_MANAGER.addSetting(debug = (new Setting(this,"Debug",false)));
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        if(!isToggled())
            return;

        int currentCPS = Utils.random((int)minCPSValue.getCurrentValue(), (int)maxCPSValue.getCurrentValue());

        switch(event.getType()) {

            case PRE:

                Object[] objects = mc.theWorld.loadedEntityList.stream().filter(this::isValid).sorted(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer))).toArray();

                if (!isValid(target))
                    target = null;

                if(objects.length > 0 && target == null) {
                    target = (EntityLivingBase) objects[0];
                    targetChangeTimer.setLastMS();
                }

                if(target == null)
                    return;

                facing = Utils.getNeededRotations(Utils.getRandomCenter(target.getEntityBoundingBox()));
                event.setYaw(facing[0]);
                event.setPitch(facing[1]);
                mc.thePlayer.rotationYawHead = facing[0];

                break;
            case POST:
                if(target == null)
                    return;

                if (!cpsTimer.isDelayComplete(1000 / currentCPS))
                    return;

                if (!targetChangeTimer.isDelayComplete((long)targetChangeDelay.getCurrentValue()))
                    return;

                Entity rayCastEntity = raycast.getBooleanValue() ? RayCastUtil.rayCast(rangeValue.getCurrentValue() + 1.0f, facing[0], facing[1]) : null;

                if (rayCastEntity != null && debug.getBooleanValue()) {
                }

                mc.thePlayer.swingItem();
                mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(rayCastEntity == null ? target : rayCastEntity, C02PacketUseEntity.Action.ATTACK));

                cpsTimer.setLastMS();

                if (switchValue.getBooleanValue())
                    target = null;

                break;
        }
    }

    private boolean isValid(Entity entity) {
        return entity instanceof EntityLivingBase && entity != mc.thePlayer && ((EntityLivingBase) entity).getHealth() > 0F && entity.getDistanceToEntity(mc.thePlayer) <= rangeValue.getCurrentValue();
    }
}
