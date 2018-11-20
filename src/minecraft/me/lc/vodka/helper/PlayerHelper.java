package me.lc.vodka.helper;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import me.lc.vodka.utils.ClickCounter;
import org.lwjgl.input.Mouse;
import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class PlayerHelper
{
    Runnable r2 = ()-> System.out.println("233333333");

    public static int bestWeapon(Entity target)
    {
        Minecraft mc = Minecraft.getMinecraft();
        int firstSlot = mc.thePlayer.inventory.currentItem = 0;
        int bestWeapon = -1;
        int j = 1;

        for (byte i = 0; i < 9; i++)
        {
            mc.thePlayer.inventory.currentItem = i;
            ItemStack itemStack = mc.thePlayer.getHeldItem();

            if (itemStack != null)
            {
                int itemAtkDamage =  (int) getItemAtkDamage(itemStack);
                itemAtkDamage += EnchantmentHelper.func_152377_a(itemStack, EnumCreatureAttribute.UNDEFINED);

                if (itemAtkDamage > j)
                {
                    j = itemAtkDamage;
                    bestWeapon = i;
                }
            }
        }

        if (bestWeapon != -1)
        {
            return bestWeapon;
        }
        else
        {
            return firstSlot;
        }
    }

    public static float getItemAtkDamage(ItemStack itemStack)
	{
		final Multimap multimap = itemStack.getAttributeModifiers();
		if (!multimap.isEmpty())
		{
			final Iterator iterator = multimap.entries().iterator();
			if (iterator.hasNext()) 
			{
				final Entry entry = (Entry) iterator.next();
				final AttributeModifier attributeModifier = (AttributeModifier) entry.getValue();
				double damage = attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2 ? attributeModifier.getAmount() :  attributeModifier.getAmount() * 100.0;
								
				if (attributeModifier.getAmount() > 1.0) 
				{
					return 1.0f + (float) damage;
				}
				return 1.0f;
			}
		}
		return 1.0f;
	}

    public static void refill(Item item)
    {
        Minecraft mc = Minecraft.getMinecraft();

        for (int i = 9; i <= 35; i++)
        {
            ItemStack itemstack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (itemstack != null && itemstack.getItem() == item)
            {
                mc.playerController.windowClick(0, i, 0, 1, mc.thePlayer);
                break;
            }
        }
    }

    public static void fakeClicks(boolean value, float minClick, float maxClick)
    {
        if (value)
        {
            Random r = new Random();
            float minDelay = (1000 / minClick);
            float maxDelay = (1000 / maxClick);
            int clicks = (int)(maxDelay + r.nextFloat() * (minDelay - maxDelay));
            ClickCounter.clicks = (1000 / clicks);
        }
    }

    public static void blockHit(Entity en, boolean value)
    {
        Minecraft mc = Minecraft.getMinecraft();
        ItemStack stack = mc.thePlayer.getCurrentEquippedItem();

        if (mc.thePlayer.getCurrentEquippedItem() != null && en != null && value)
        {
            if (stack.getItem() instanceof ItemSword && mc.thePlayer.swingProgress > 0.2)
            {
                mc.thePlayer.getCurrentEquippedItem().useItemRightClick(mc.theWorld, mc.thePlayer);
            }
        }
    }

    public static boolean hotbarIsFull()
    {
        for (int i = 0; i <= 8; i++)
        {
            ItemStack itemstack = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);

            if (itemstack == null)
            {
                return false;
            }
        }

        return true;
    }
    
    public static boolean chance(float value)
    {
        return Math.random() * 100 < value;
    }
    
    public static boolean hitThroughBlock(boolean value) 
    {
		if (!value && Minecraft.getMinecraft().objectMouseOver != null && Minecraft.getMinecraft().objectMouseOver.typeOfHit == MovingObjectType.BLOCK) 
		{
			return false;
		}
	
		return true;
    }
}
