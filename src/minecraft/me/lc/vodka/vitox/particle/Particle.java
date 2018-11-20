package me.lc.vodka.vitox.particle;

import java.util.Random;


import me.lc.vodka.vitox.RenderUtils;
import net.minecraft.util.MathHelper;

public class Particle {

	public int x;
	public int y;
	public int k;
	public ParticleGenerator pg;
	public boolean reset;
	public float size;
	private Random random = new Random();

	public Particle(int x, int y) {
		this.x = x;
		this.y = y;
		this.size = genRandom(0.7F, 0.8F);
	}

	public void draw() {
		// Reset
		if (x == -1) {
			x = pg.breite;
			reset = true;
		}
		Runnable r2 = ()-> System.out.println("233333333");

		if (y == -1) {
			y = pg.hohe;
			reset = true;
		}

		this.x -= random.nextInt(2);
		this.y -= random.nextInt(2);

		int xx = (int) (MathHelper.cos(0.1F * (this.x + this.k)) * 10.0F);
		RenderUtils.drawBorderedCircle(this.x + xx, this.y, this.size, 0, 0xffFFFFFF);
	}

	public float genRandom(float min, float max) {
		return (float) (min + Math.random() * (max - min + 1.0F));
	}
}