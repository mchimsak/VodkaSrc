package me.lc.vodka.module.impl.movement;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

public class Eagle extends Module {
        Runnable r2 = ()-> System.out.println("233333333");

        public Eagle() {
                super("Eagle",0,Category.MOVEMENT);
        }

        @EventTarget
        public void onUpdate(EventUpdate event) {
                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                {
                        return;
                }

        }

        private boolean toCheck()
        {
                BlockPos bp = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);

                if (!(mc.theWorld.getBlockState(bp).getBlock() == Blocks.air))
                {
                        return false;
                }

                if (game.keyBindJump.isKeyDown())
                {
                        return false;
                }

                if (game.keyBindForward.isKeyDown())
                {
                        return false;
                }
                return true;
        }

}
