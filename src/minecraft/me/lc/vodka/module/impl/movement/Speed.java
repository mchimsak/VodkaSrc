package me.lc.vodka.module.impl.movement;

import me.lc.vodka.Vodka;
import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.setting.Setting;
import me.lc.vodka.vui.Line3D;
import me.lc.vodka.vui.Location3D;
import me.lc.vodka.utils.util.PlayerUtil;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.Iterator;

public class Speed extends Module {

        int delay;
        double startY;
        boolean timerWasSettedByMe = false;
        public static ArrayList<String> options = new ArrayList<>();
        public Setting Mod;
        public Setting TimerSlider;
        public Setting hopYMotionM;
        public Setting airUpSpeedM;
        public Setting groundSpeedM;
        public Setting hopSpeedM;
        public Setting airDownSpeedM;
        public Setting multiply;
    private boolean legitHop = false;
    Runnable r2 = ()-> System.out.println("233333333");

    public Speed() {
                super("Speed",0,Category.MOVEMENT);
            Vodka.INSTANCE.SETTING_MANAGER.addSetting(Mod = (new Setting(this,"MOD","Custom",options)));
            Vodka.INSTANCE.SETTING_MANAGER.addSetting(TimerSlider = (new Setting(this,"Timer", 2.0D, 0.5D, 1.0D, false)));
            Vodka.INSTANCE.SETTING_MANAGER.addSetting(hopYMotionM = (new Setting(this,"hopYMotionM", 0.42D, 0.0D, 1.0D, false)));
            Vodka.INSTANCE.SETTING_MANAGER.addSetting(airUpSpeedM = (new Setting(this,"airUpSpeedM", 2.0D, 0.0D, 1.0D, false)));
            Vodka.INSTANCE.SETTING_MANAGER.addSetting(groundSpeedM = (new Setting(this,"groundSpeedM", 2.0D, 0.0D, 1.0D, false)));
            Vodka.INSTANCE.SETTING_MANAGER.addSetting(hopSpeedM = (new Setting(this,"hopSpeedM", 3.0D, 0.0D, 1.0D, false)));
            Vodka.INSTANCE.SETTING_MANAGER.addSetting(airDownSpeedM = (new Setting(this,"airDownSpeedM", 2.0D, 0.0D, 1.0D, false)));
            Vodka.INSTANCE.SETTING_MANAGER.addSetting(multiply = (new Setting(this,"Multiply",false)));

        options.add(0,"Custom");
        options.add(1,"Teleport");
        options.add(2,"Normal");
        options.add(3,"Fast");
        options.add(4,"Bac");
        options.add(5,"AACLowhop");
        options.add(6,"AACFast");
        options.add(7,"NCPBhop");
        options.add( 8,"AAC1");

    }


        @EventTarget
    public void onUpdate(EventUpdate e) {
            if(this.mc.thePlayer.moveForward != 0.0F || this.mc.thePlayer.moveStrafing != 0.0F) {
                    switch (Mod.getCurrentOptionIndex()) {
                        case 0://Custom
                            if(this.TimerSlider.getCurrentValue() != 1.0D) {
                                this.timerWasSettedByMe = true;
                                this.mc.timer.timerSpeed = (float)this.TimerSlider.getCurrentValue();
                            }

                            this.costum();
                            break;

                        case 1://Teleport
                                this.onTeleport();
                            break;

                        case 2://Normal
                            this.mc.thePlayer.capabilities.setPlayerWalkSpeed(0.08F);
                            this.mc.thePlayer.motionX *= 1.0299999713897705D;
                            this.mc.thePlayer.motionZ *= 1.0299999713897705D;
                            if(this.mc.thePlayer.onGround) {
                                this.mc.thePlayer.motionX *= 1.0399999618530273D;
                                this.mc.thePlayer.motionZ *= 1.0399999618530273D;
                            }
                            break;
                        case 3://Fast
                            if(this.mc.thePlayer.motionY > 0.0D) {
                                this.mc.thePlayer.motionX *= 1.2000000476837158D;
                                this.mc.thePlayer.motionZ *= 1.2000000476837158D;
                            }

                            if(this.mc.thePlayer.onGround) {
                                this.mc.thePlayer.jump();
                                this.mc.thePlayer.motionY = 0.0D;
                                this.portMove(this.mc.thePlayer.rotationYaw, 0.0F, 0.42F);
                            }
                            break;

                        case 4://Bac
                            BlockPos pos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.1D, this.mc.thePlayer.posZ);
                            if(this.mc.theWorld.getBlockState(pos).getBlock() == Blocks.air) {
                                this.mc.timer.timerSpeed = 1.0F;
                                return;
                            }

                            if(this.mc.thePlayer.onGround) {
                                this.mc.timer.timerSpeed = 1.05F;
                                this.mc.thePlayer.motionY = 0.09000000357627869D;
                                this.move(this.mc.thePlayer.rotationYaw, 0.432F);
                            } else if(this.mc.thePlayer.motionY < -0.07D) {
                                this.move(this.mc.thePlayer.rotationYaw, 0.432F);
                            } else {
                                this.move(this.mc.thePlayer.rotationYaw, 0.333F);
                            }
                            break;

                        case 5://AACLowhop
                            this.onAAC317();
                            break;
                        case 6://AACFast
                            if(this.delay == 1) {
                                this.delay = 0;
                                this.move(this.mc.thePlayer.rotationYaw, -0.2F);
                            }

                            if(this.mc.thePlayer.motionY > 0.3D) {
                                this.portMove(this.mc.thePlayer.rotationYaw, 0.5F, 0.0F);
                                this.delay = 1;
                            }
                            break;
                        case 7://NCP
                            this.doNCP();
                            break;
                        case 8://AAC1
                            if ((!this.mc.thePlayer.isBlocking()))
                            {
                                if (this.mc.thePlayer.hurtTime == 0)
                                {
                                    if (PlayerUtil.MovementInput())
                                    {
                                        if (this.legitHop)
                                        {
                                            if (this.mc.thePlayer.onGround)
                                            {
                                                this.mc.thePlayer.jump();
                                                this.mc.thePlayer.onGround = false;
                                                this.legitHop = false;
                                            }
                                            return;
                                        }
                                        if (this.mc.thePlayer.onGround)
                                        {
                                            this.mc.thePlayer.onGround = false;
                                            strafe(0.375F);
                                            this.mc.thePlayer.jump();
                                            this.mc.thePlayer.motionY = 0.41D;
                                        }
                                        else
                                        {
                                            this.mc.thePlayer.speedInAir = 0.0211F;
                                        }
                                    }
                                    else
                                    {
                                        this.mc.thePlayer.motionX = (this.mc.thePlayer.motionZ = 0.0D);
                                        this.legitHop = true;
                                    }
                                    if (this.mc.thePlayer.isAirBorne) {
                                        PlayerUtil.setSpeed(PlayerUtil.getSpeed());
                                    }
                                }
                            }
                            break;
                    }

            }

        }

    public void onDisable() {
        this.mc.thePlayer.capabilities.setPlayerWalkSpeed(0.1F);
        this.mc.timer.timerSpeed = 1.0F;
        this.mc.thePlayer.speedInAir = 0.02F;
        super.onDisable();
    }

    public void costum() {
        if(this.multiply.getBooleanValue()) {
            if(!this.mc.gameSettings.keyBindForward.isKeyDown()) {
                return;
            }

            if(this.mc.thePlayer.onGround) {
                if(this.hopYMotionM.getCurrentValue() <= 0.0D && !this.mc.gameSettings.keyBindJump.isPressed()) {
                    this.mc.thePlayer.motionX *= this.groundSpeedM.getCurrentValue();
                    this.mc.thePlayer.motionZ *= this.groundSpeedM.getCurrentValue();
                } else {
                    if(this.hopYMotionM.getCurrentValue() > 0.0D) {
                        this.mc.thePlayer.motionY = this.hopYMotionM.getCurrentValue();
                    }

                    this.mc.thePlayer.motionX *= this.hopSpeedM.getCurrentValue();
                    this.mc.thePlayer.motionZ *= this.hopSpeedM.getCurrentValue();
                }
            } else if(this.mc.thePlayer.motionY > 0.0D) {
                this.mc.thePlayer.motionX *= this.airUpSpeedM.getCurrentValue();
                this.mc.thePlayer.motionZ *= this.airUpSpeedM.getCurrentValue();
            } else {
                this.mc.thePlayer.motionX *= this.airDownSpeedM.getCurrentValue();
                this.mc.thePlayer.motionZ *= this.airDownSpeedM.getCurrentValue();
            }
        } else {
            float direction = this.mc.thePlayer.rotationYaw;
            boolean move = false;
            boolean invert = false;
            if(this.mc.gameSettings.keyBindBack.isKeyDown()) {
                move = true;
                invert = true;
                direction += 180.0F;
            }

            if(this.mc.gameSettings.keyBindLeft.isKeyDown()) {
                move = true;
                if(invert) {
                    direction += 90.0F;
                } else {
                    direction -= 90.0F;
                }
            }

            if(this.mc.gameSettings.keyBindRight.isKeyDown()) {
                move = true;
                if(invert) {
                    direction -= 90.0F;
                } else {
                    direction += 90.0F;
                }
            }

            if(this.mc.gameSettings.keyBindForward.isKeyDown()) {
                move = true;
                direction = direction / 2.0F + this.mc.thePlayer.rotationYaw / 2.0F;
            } else if(this.mc.gameSettings.keyBindBack.isKeyDown()) {
                move = true;
                direction = direction / 2.0F + (this.mc.thePlayer.rotationYaw + 180.0F) / 2.0F;
            }

            if(move) {
                if(this.mc.thePlayer.onGround) {
                    if(this.hopYMotionM.getCurrentValue() <= 0.0D && !this.mc.gameSettings.keyBindJump.isPressed()) {
                        this.move(direction, (float)this.groundSpeedM.getCurrentValue());
                    } else {
                        if(this.hopYMotionM.getCurrentValue() > 0.0D) {
                            this.mc.thePlayer.motionY = this.hopYMotionM.getCurrentValue();
                        }

                        this.move(direction, (float)this.hopSpeedM.getCurrentValue());
                    }
                } else if(this.mc.thePlayer.motionY > 0.0D) {
                    this.move(direction, (float)this.airUpSpeedM.getCurrentValue());
                } else {
                    this.move(direction, (float)this.airDownSpeedM.getCurrentValue());
                }
            }
        }

    }

    private void onTeleport() {
        ++this.delay;
        if(this.delay >= 1) {
            this.delay = 0;
            Location3D start = new Location3D(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
            Line3D line = new Line3D(start, (double)(-this.mc.thePlayer.rotationYaw), 0.0D, 8.0D);
            Iterator var4 = line.getPointsOn(2.0D).iterator();

            while(var4.hasNext()) {
                Location3D point = (Location3D)var4.next();
                this.mc.thePlayer.setPosition(point.getX(), point.getY(), point.getZ());
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(point.getX(), point.getY(), point.getZ(), false));
            }

            this.mc.thePlayer.setPosition(line.getEnd().getX(), line.getEnd().getY(), line.getEnd().getZ());
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(line.getEnd().getX(), line.getEnd().getY() - 1.0D, line.getEnd().getZ(), false));
        }
    }

    private void onAAC317() {
        if(!this.mc.thePlayer.isCollidedHorizontally && !this.mc.gameSettings.keyBindJump.isKeyDown() && this.mc.gameSettings.keyBindForward.isKeyDown()) {
            if(this.mc.thePlayer.hurtTime <= 0) {
                if(this.mc.thePlayer.onGround) {
                    this.mc.thePlayer.jump();
                    this.mc.thePlayer.motionX *= 1.0099999904632568D;
                    this.mc.thePlayer.motionZ *= 1.0099999904632568D;
                    this.mc.thePlayer.motionY = 0.38510000705718994D;
                } else if(this.mc.thePlayer.motionY > 0.0D) {
                    this.mc.thePlayer.motionY -= 0.014999999664723873D;
                } else {
                    this.mc.thePlayer.motionY -= 0.01489999983459711D;
                    BlockPos willBe = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.6D, this.mc.thePlayer.posZ);
                    if(this.mc.theWorld.getBlockState(willBe).getBlock().getBlockBoundsMaxY() == this.mc.theWorld.getBlockState(willBe).getBlock().getBlockBoundsMinY() + 1.0D && !this.mc.theWorld.getBlockState(willBe).getBlock().isTranslucent() && this.mc.theWorld.getBlockState(willBe).getBlock() != Blocks.water && !(this.mc.theWorld.getBlockState(willBe).getBlock() instanceof BlockSlab)) {
                        this.mc.thePlayer.motionY = -1.0D;
                    }
                }

            }
        }
    }

    public void doNCP() {
        if(this.mc.gameSettings.keyBindForward.isKeyDown()) {
            this.mc.gameSettings.keyBindJump.pressed = false;
            if(this.mc.thePlayer.onGround) {
                this.mc.thePlayer.jump();
                this.mc.thePlayer.motionY = 0.39989998936653137D;
                this.mc.thePlayer.motionX *= 1.0020000267028809D;
                this.mc.thePlayer.motionZ *= 1.020000267028809D;
            } else if(this.mc.thePlayer.motionY < 0.0D && this.mc.thePlayer.motionY > -0.6D) {
                this.mc.thePlayer.motionY -= 0.019999999552965164D;
                this.mc.thePlayer.jumpMovementFactor = 0.021F;
            }

            this.mc.timer.timerSpeed = 1.08F;
            this.timerWasSettedByMe = true;
        }
    }

    public void portMove(float yaw, float multiplyer, float up) {
        double moveX = -Math.sin(Math.toRadians((double)yaw)) * (double)multiplyer;
        double moveZ = Math.cos(Math.toRadians((double)yaw)) * (double)multiplyer;
        double moveY = (double)up;
        this.mc.thePlayer.setPosition(moveX + this.mc.thePlayer.posX, moveY + this.mc.thePlayer.posY, moveZ + this.mc.thePlayer.posZ);
    }

    public void move(float yaw, float multiplyer, float up) {
        double moveX = -Math.sin(Math.toRadians((double)yaw)) * (double)multiplyer;
        double moveZ = Math.cos(Math.toRadians((double)yaw)) * (double)multiplyer;
        this.mc.thePlayer.motionX = moveX;
        this.mc.thePlayer.motionY = (double)up;
        this.mc.thePlayer.motionZ = moveZ;
    }

    public void move(float yaw, float multiplyer) {
        double moveX = -Math.sin(Math.toRadians((double)yaw)) * (double)multiplyer;
        double moveZ = Math.cos(Math.toRadians((double)yaw)) * (double)multiplyer;
        this.mc.thePlayer.motionX = moveX;
        this.mc.thePlayer.motionZ = moveZ;
    }

    private void strafe(float speed)
    {
        boolean isMoving = (this.mc.thePlayer.moveForward != 0.0F) || (this.mc.thePlayer.moveStrafing != 0.0F);
        boolean isMovingForward = this.mc.thePlayer.moveForward > 0.0F;
        boolean isMovingBackward = this.mc.thePlayer.moveForward < 0.0F;
        boolean isMovingRight = this.mc.thePlayer.moveStrafing > 0.0F;
        boolean isMovingLeft = this.mc.thePlayer.moveStrafing < 0.0F;
        boolean isMovingSideways = (isMovingLeft) || (isMovingRight);
        boolean isMovingStraight = (isMovingForward) || (isMovingBackward);
        if (!isMoving) {
            return;
        }
        double yaw = this.mc.thePlayer.rotationYaw;
        if ((isMovingForward) && (!isMovingSideways)) {
            yaw += 0.0D;
        } else if ((isMovingBackward) && (!isMovingSideways)) {
            yaw += 180.0D;
        } else if ((isMovingForward) && (isMovingLeft)) {
            yaw += 45.0D;
        } else if (isMovingForward) {
            yaw -= 45.0D;
        } else if ((!isMovingStraight) && (isMovingLeft)) {
            yaw += 90.0D;
        } else if ((!isMovingStraight) && (isMovingRight)) {
            yaw -= 90.0D;
        } else if ((isMovingBackward) && (isMovingLeft)) {
            yaw += 135.0D;
        } else if (isMovingBackward) {
            yaw -= 135.0D;
        }
        yaw = Math.toRadians(yaw);
        this.mc.thePlayer.motionX = (-Math.sin(yaw) * speed);
        this.mc.thePlayer.motionZ = (Math.cos(yaw) * speed);
    }
}
