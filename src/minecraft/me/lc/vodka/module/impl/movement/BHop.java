package me.lc.vodka.module.impl.movement;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import net.minecraft.block.BlockSlime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;

public class BHop extends Module {

        public BHop() {
                super("BHop",0,Category.MOVEMENT);
        }
    Runnable r2 = ()-> System.out.println("233333333");

        @EventTarget
        public  void onMovement(EventUpdate e) {
            if (mc.thePlayer.isSneaking() || mc.thePlayer.isInWater() || game.keyBindJump.pressed || game.keyBindBack.pressed)
            {
                return;
            }

            if (game.keyBindForward.pressed || game.keyBindLeft.pressed || game.keyBindRight.pressed)
            {
                if (mc.thePlayer.onGround)
                {
                    mc.thePlayer.jump();
                    mc.thePlayer.setSprinting(true);
                }

            }

        }

    private void getStrafe(double speed)
    {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        double yaw = player.rotationYaw;
        boolean isMoving = player.moveForward != 0 || player.moveStrafing != 0;
        boolean isMovingForward = player.moveForward > 0;
        boolean isMovingBackward = player.moveForward < 0;
        boolean isMovingRight = player.moveStrafing > 0;
        boolean isMovingLeft = player.moveStrafing < 0;
        boolean isMovingSideways = isMovingLeft || isMovingRight;
        boolean isMovingStraight = isMovingForward || isMovingBackward;

        if (isMoving)
        {
            if (isMovingForward && !isMovingSideways)
            {
                yaw += 0;
            }
            else if (isMovingBackward && !isMovingSideways)
            {
                yaw += 180;
            }
            else if (isMovingForward && isMovingLeft)
            {
                yaw += 45;
            }
            else if (isMovingForward)
            {
                yaw -= 45;
            }
            else if (!isMovingStraight && isMovingLeft)
            {
                yaw += 90;
            }
            else if (!isMovingStraight && isMovingRight)
            {
                yaw -= 90;
            }
            else if (isMovingBackward && isMovingLeft)
            {
                yaw += 135;
            }
            else if (isMovingBackward)
            {
                yaw -= 135;
            }

            yaw = Math.toRadians(yaw);
            player.motionX = -Math.sin(yaw) * speed;
            player.motionZ = Math.cos(yaw) * speed;
        }
    }

}
