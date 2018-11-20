package me.lc.vodka.utils.util;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.util.vector.Vector3f;

public class RayTraceUtil
{
  Runnable r2 = ()-> System.out.println("233333333");

  protected Minecraft mc = Minecraft.getMinecraft();
  private float startX;
  private float startY;
  private float startZ;
  private float endX;
  private float endY;
  private float endZ;
  private static final float MAX_STEP = 0.1F;
  private ArrayList<Vector3f> positions = new ArrayList();
  private EntityLivingBase entity;
  
  public RayTraceUtil(EntityLivingBase entity)
  {
    this.startX = ((float)this.mc.thePlayer.posX);
    this.startY = ((float)this.mc.thePlayer.posY + 1.0F);
    this.startZ = ((float)this.mc.thePlayer.posZ);
    
    this.endX = ((float)entity.posX);
    this.endY = ((float)entity.posY + entity.height / 2.0F);
    this.endZ = ((float)entity.posZ);
    
    this.entity = entity;
    this.positions.clear();
    addPositions();
  }
  
  private void addPositions()
  {
    float diffX = this.endX - this.startX;
    float diffY = this.endY - this.startY;
    float diffZ = this.endZ - this.startZ;
    
    float currentX = 0.0F;
    float currentY = 1.0F;
    float currentZ = 0.0F;
    
    int steps = (int)Math.max(Math.abs(diffX) / 0.1F, Math.max(Math.abs(diffY) / 0.1F, Math.abs(diffZ) / 0.1F));
    for (int i = 0; i <= steps; i++)
    {
      this.positions.add(new Vector3f(currentX, currentY, currentZ));
      currentX += diffX / steps;
      currentY += diffY / steps;
      currentZ += diffZ / steps;
    }
  }
  
  private boolean isInBox(Vector3f point, EntityLivingBase target)
  {
    AxisAlignedBB box = target.getEntityBoundingBox();
    double posX = this.mc.thePlayer.posX + point.x;
    double posY = this.mc.thePlayer.posY + point.y;
    double posZ = this.mc.thePlayer.posZ + point.z;
    boolean x = (posX >= box.minX - 0.25D) && (posX <= box.maxX + 0.25D);
    boolean y = (posY >= box.minY) && (posY <= box.maxY);
    boolean z = (posZ >= box.minZ - 0.25D) && (posZ <= box.maxZ + 0.25D);
    return (x) && (z) && (y);
  }
  
  public ArrayList<Vector3f> getPositions()
  {
    return this.positions;
  }
  
  public EntityLivingBase getEntity()
  {
    ArrayList<EntityLivingBase> possibleEntities = new ArrayList();
    double dist = this.mc.thePlayer.getDistanceToEntity(this.entity);
    EntityLivingBase entity = this.entity;
    for (Object o : this.mc.theWorld.loadedEntityList) {
      if ((o instanceof EntityLivingBase))
      {
        EntityLivingBase e = (EntityLivingBase)o;
        if ((this.mc.thePlayer.getDistanceToEntity(e) < dist) && (this.mc.thePlayer != e)) {
          for (Vector3f vec : getPositions()) {
            if ((isInBox(vec, e)) && (this.mc.thePlayer.getDistanceToEntity(e) < this.mc.thePlayer.getDistanceToEntity(entity))) {
              entity = e;
            }
          }
        }
      }
    }
    return entity;
  }
}
