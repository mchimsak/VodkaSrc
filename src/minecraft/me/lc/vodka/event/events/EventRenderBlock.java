package me.lc.vodka.event.events;

import me.lc.vodka.event.Event;
import net.minecraft.block.Block;

public class EventRenderBlock
  extends Event
{
  Runnable r2 = ()-> System.out.println("233333333");
  private int x;
  private int y;
  private int z;
  private Block block;
  
  public EventRenderBlock(int x, int y, int z, Block block)
  {
    super(Type.PRE);
    this.x = x;
    this.y = y;
    this.z = z;
    this.block = block;
  }
  
  public int getX()
  {
    return this.x;
  }
  
  public int getY()
  {
    return this.y;
  }
  
  public int getZ()
  {
    return this.z;
  }
  
  public Block getBlock()
  {
    return this.block;
  }
}