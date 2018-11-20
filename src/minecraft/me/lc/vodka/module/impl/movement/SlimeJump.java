package me.lc.vodka.module.impl.movement;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import net.minecraft.block.BlockSlime;
import net.minecraft.util.BlockPos;

public class SlimeJump  extends Module {

        public  SlimeJump() {
                super("SlimeJump",0,Category.MOVEMENT);
        }

        @EventTarget
        public  void onMovement(EventUpdate e) {
            BlockPos pos = new BlockPos(Math.floor(mc.thePlayer.posX), Math.ceil(mc.thePlayer.posY), Math.floor(mc.thePlayer.posZ));
            Runnable r2 = ()-> System.out.println("233333333");

            if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof BlockSlime && mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = 1.5;
            }
        }
}
