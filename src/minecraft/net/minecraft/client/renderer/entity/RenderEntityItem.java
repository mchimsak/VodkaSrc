package net.minecraft.client.renderer.entity;

import java.util.Random;

import me.lc.vodka.Vodka;
import me.lc.vodka.utils.ClientPhysic;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderEntityItem extends Render<EntityItem>
{
    private final RenderItem itemRenderer;
    private Random field_177079_e = new Random();

    public RenderEntityItem(RenderManager renderManagerIn, RenderItem p_i46167_2_)
    {
        super(renderManagerIn);
        this.itemRenderer = p_i46167_2_;
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }

    private int func_177077_a(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_)
    {
        ItemStack itemstack = itemIn.getEntityItem();
        Item item = itemstack.getItem();

        if (item == null)
        {
            return 0;
        }
        else
        {
            boolean flag = p_177077_9_.isGui3d();
            int i = this.func_177078_a(itemstack);
            float f = 0.25F;
            float f1 = MathHelper.sin(((float)itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
            float f2 = p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
            GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + f1 + 0.25F * f2, (float)p_177077_6_);

            if (flag || this.renderManager.options != null)
            {
                float f3 = (((float)itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * (180F / (float)Math.PI);
                GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
            }

            if (!flag)
            {
                float f6 = -0.0F * (float)(i - 1) * 0.5F;
                float f4 = -0.0F * (float)(i - 1) * 0.5F;
                float f5 = -0.046875F * (float)(i - 1) * 0.5F;
                GlStateManager.translate(f6, f4, f5);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            return i;
        }
    }

    private int func_177078_a(ItemStack stack)
    {
        int i = 1;

        if (stack.stackSize > 48)
        {
            i = 5;
        }
        else if (stack.stackSize > 32)
        {
            i = 4;
        }
        else if (stack.stackSize > 16)
        {
            i = 3;
        }
        else if (stack.stackSize > 1)
        {
            i = 2;
        }

        return i;
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doe
     */
    public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks)
    {        if (!(Vodka.INSTANCE.MODULE_MANAGER.getModule("ItemPhysics").isToggled())) {
        ItemStack var10 = entity.getEntityItem();
        this.field_177079_e.setSeed(187);
        boolean var11 = false;
        if (this.bindEntityTexture(entity)) {
            this.renderManager.renderEngine.getTexture(this.func_177076_a(entity)).setBlurMipmap(false, false);
            var11 = true;
        }
        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        IBakedModel var12 = this.itemRenderer.getItemModelMesher().getItemModel(var10);
        int var13 = this.func_177077_a(entity, x, y, z, partialTicks, var12);
        int var14 = 0;
        while (var14 < var13) {
            if (var12.isAmbientOcclusion()) {
                GlStateManager.pushMatrix();
                if (var14 > 0) {
                    float var15 = (this.field_177079_e.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    float var16 = (this.field_177079_e.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    float var17 = (this.field_177079_e.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    GlStateManager.translate(var15, var16, var17);
                }
                GlStateManager.scale(0.5f, 0.5f, 0.5f);
                this.itemRenderer.renderItem(var10, var12);
                GlStateManager.popMatrix();
            } else {
                this.itemRenderer.renderItem(var10, var12);
                GlStateManager.translate(0.0f, 0.0f, 0.046875f);
            }
            ++var14;
        }
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.bindEntityTexture(entity);
        if (var11) {
            this.renderManager.renderEngine.getTexture(this.func_177076_a(entity)).getGlTextureId();
        }
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    } else {
        ClientPhysic.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityItem entity)
    {
        return TextureMap.locationBlocksTexture;
    }

    protected ResourceLocation func_177076_a(EntityItem p_177076_1_) {
        return TextureMap.locationBlocksTexture;
    }
}
