package me.lc.vodka.module.impl.movement;

import me.lc.vodka.event.EventTarget;
import me.lc.vodka.event.events.EventUpdate;
import me.lc.vodka.module.Category;
import me.lc.vodka.module.Module;
import me.lc.vodka.helper.TimeHelper;
import me.lc.vodka.vui.Line3D;
import me.lc.vodka.vui.Location3D;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.BlockStairs;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class Step extends Module {
        Runnable r2 = ()-> System.out.println("233333333");

        public Step() {
                super("Step",0,Category.MOVEMENT);
        }

        TimeHelper time1 = new TimeHelper();
        double groundy = 0.0D;
        @EventTarget
        public void onUpdate(EventUpdate e) {
                if(this.mc.gameSettings.keyBindForward.isKeyDown()) {
                        if(!this.mc.thePlayer.isInWater() && !this.mc.thePlayer.isInLava()) {
                                Line3D line = new Line3D(new Location3D(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ), (double)(-this.mc.thePlayer.rotationYaw), 0.0D, 1.0D);
                                BlockPos willBeTop = new BlockPos(line.getEnd().getX(), line.getEnd().getY() + 1.0D, line.getEnd().getZ());
                                if(this.mc.theWorld.getBlockState(willBeTop).getBlock().getBlockBoundsMaxY() != this.mc.theWorld.getBlockState(willBeTop).getBlock().getBlockBoundsMinY() + 1.0D || this.mc.theWorld.getBlockState(willBeTop).getBlock().isTranslucent() || this.mc.theWorld.getBlockState(willBeTop).getBlock() == Blocks.water || this.mc.theWorld.getBlockState(willBeTop).getBlock() instanceof BlockSlab) {
                                        if(this.mc.thePlayer.isCollidedHorizontally && Math.abs(this.mc.thePlayer.motionX) + Math.abs(this.mc.thePlayer.motionZ) < 0.1D && this.mc.thePlayer.motionY <= 0.0D) {
                                                this.mc.thePlayer.jump();
                                        } else {
                                                BlockPos willBe = new BlockPos(line.getEnd().getX(), line.getEnd().getY(), line.getEnd().getZ());
                                                if(this.mc.theWorld.getBlockState(willBe).getBlock().getBlockBoundsMaxY() == this.mc.theWorld.getBlockState(willBe).getBlock().getBlockBoundsMinY() + 1.0D && !this.mc.theWorld.getBlockState(willBe).getBlock().isTranslucent() && this.mc.theWorld.getBlockState(willBe).getBlock() != Blocks.water && !(this.mc.theWorld.getBlockState(willBe).getBlock() instanceof BlockSlab) && !(this.mc.theWorld.getBlockState(willBe).getBlock() instanceof BlockStairs) && !(this.mc.theWorld.getBlockState(willBe).getBlock() instanceof BlockFence) && this.mc.thePlayer.posY < (double)((int)this.mc.thePlayer.posY) + 0.5D && !(this.mc.theWorld.getBlockState(willBe).getBlock() instanceof BlockSign) && !(this.mc.theWorld.getBlockState(willBe).getBlock() instanceof BlockFence) && !(this.mc.theWorld.getBlockState(willBe).getBlock() instanceof BlockStainedGlassPane)) {
                                                        if(this.mc.thePlayer.motionY <= 0.0D) {
                                                                this.groundy = this.mc.thePlayer.posY;
                                                                this.mc.thePlayer.motionY = 0.41999998688697815D;
                                                        }

                                                        this.move(this.mc.thePlayer.rotationYaw, 0.25F);
                                                }

                                                if(this.mc.thePlayer.posY > this.groundy + 0.8D) {
                                                        this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, (double)((int)this.groundy + 1), this.mc.thePlayer.posZ);
                                                        this.mc.thePlayer.motionY = -1.0D;
                                                        this.move(this.mc.thePlayer.rotationYaw, 0.2F);
                                                }

                                        }
                                }
                        }
                }
        }

        public void move(float yaw, float multiplyer) {
                double moveX = -Math.sin(Math.toRadians((double)yaw)) * (double)multiplyer;
                double moveZ = Math.cos(Math.toRadians((double)yaw)) * (double)multiplyer;
                this.mc.thePlayer.motionX = moveX;
                this.mc.thePlayer.motionZ = moveZ;
        }
}
