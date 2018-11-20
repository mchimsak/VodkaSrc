package me.lc.vodka.module.impl.render;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventRender3D;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.module.impl.combat.KillAura;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.utils.Colors;
import me.lc.vodka.utils.util.FlatColors;
import me.lc.vodka.utils.util.RenderUtil;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ESP extends Module {
        Runnable r2 = ()-> System.out.println("233333333");
        public static Setting value;
        public ESP() {
                super("ESP",0,Category.RENDER);
        }
}
