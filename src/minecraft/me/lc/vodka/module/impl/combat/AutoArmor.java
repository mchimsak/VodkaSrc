/*     */ package me.lc.vodka.module.impl.combat;
/*     */ import me.lc.vodka.event.EventTarget;
            import me.lc.vodka.event.events.EventUpdate;
            import me.lc.vodka.module.Category;
            import me.lc.vodka.helper.TimeHelper;
/*     */ import me.lc.vodka.module.Module;
        import net.minecraft.item.ItemStack;
/*     */ public class AutoArmor extends Module
/*     */ {
/*     */   public AutoArmor()
/*     */   {
/*  13 */     super("AutoArmor", 0, Category.COMBAT);
/*     */   }
/*     */   Runnable r2 = ()-> System.out.println("233333333");
/*  16 */   private TimeHelper time = new TimeHelper();
/*  17 */   private final int[] boots = { 313, 309, 317, 305, 301 };
/*  18 */   private final int[] chestplate = { 311, 307, 315, 303, 299 };
/*  19 */   private final int[] helmet = { 310, 306, 314, 302, 298 };
/*  20 */   private final int[] leggings = { 312, 308, 316, 304, 300 };
private int finalInvIndex;
private int armour;
@EventTarget
public void onCombat(EventUpdate e) {
/*  23 */     if ((isToggled()) && 
/*  24 */       (this.mc.currentScreen != null) && (!(this.mc.currentScreen instanceof net.minecraft.client.gui.GuiChat))) {
/*  25 */       if (!this.time.isDelayComplete(65L)) {
/*  26 */         return;
/*     */       }
/*  28 */       if ((this.mc.thePlayer.openContainer != null) && 
/*  29 */         (this.mc.thePlayer.openContainer.windowId != 0)) {
/*  30 */         return;
/*     */       }
/*     */       
/*  33 */       int item = -1;
/*  34 */       int[] arrayOfInt; int j; int i; if (this.mc.thePlayer.inventory.armorInventory[0] == null) {
/*  35 */         j = (arrayOfInt = this.boots).length; for (i = 0; i < j; i++) { int id = arrayOfInt[i];
/*  36 */           if (findItem(id) != -1) {
/*  37 */             item = findItem(id);
/*  38 */             break;
/*     */           }
/*     */         }
/*     */       }
/*  42 */       if (armourIsBetter(0, this.boots)) {
/*  43 */         item = 8;
/*     */       }
/*  45 */       if (this.mc.thePlayer.inventory.armorInventory[3] == null) {
/*  46 */         j = (arrayOfInt = this.helmet).length; for (i = 0; i < j; i++) { int id = arrayOfInt[i];
/*  47 */           if (findItem(id) != -1) {
/*  48 */             item = findItem(id);
/*  49 */             break;
/*     */           }
/*     */         }
/*     */       }
/*  53 */       if (armourIsBetter(3, this.helmet)) {
/*  54 */         item = 8;
/*     */       }
/*  56 */       if (this.mc.thePlayer.inventory.armorInventory[1] == null) {
/*  57 */         j = (arrayOfInt = this.leggings).length; for (i = 0; i < j; i++) { int id = arrayOfInt[i];
/*  58 */           if (findItem(id) != -1) {
/*  59 */             item = findItem(id);
/*  60 */             break;
/*     */           }
/*     */         }
/*     */       }
/*  64 */       if (armourIsBetter(1, this.leggings)) {
/*  65 */         item = 7;
/*     */       }
/*  67 */       if (this.mc.thePlayer.inventory.armorInventory[2] == null) {
/*  68 */         j = (arrayOfInt = this.chestplate).length; for (i = 0; i < j; i++) { int id = arrayOfInt[i];
/*  69 */           if (findItem(id) != -1) {
/*  70 */             item = findItem(id);
/*  71 */             break;
/*     */           }
/*     */         }
/*     */       }
/*  75 */       if (armourIsBetter(2, this.chestplate)) {
/*  76 */         item = 8;
/*     */       }
/*  78 */       if (item != -1) {
/*  79 */         this.mc.playerController.windowClick(0, item, 0, 1, this.mc.thePlayer);
/*  80 */         this.time.setLastMS();
/*  81 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean armourIsBetter(int slot, int[] armourtype) {
/*  87 */     if (this.mc.thePlayer.inventory.armorInventory[slot] != null) {
/*  88 */       int currentIndex = 9;
/*  89 */       int finalCurrentIndex = -1;
/*  90 */       int invIndex = 0;
/*  91 */       finalInvIndex = -1;
/*  92 */       int[] arrayOfInt2; int j = (arrayOfInt2 = armourtype).length; for (int i = 0; i < j; i++) { armour = arrayOfInt2[i];
/*  93 */         if (net.minecraft.item.Item.getIdFromItem(this.mc.thePlayer.inventory.armorInventory[slot].getItem()) == armour) {
/*  94 */           finalCurrentIndex = currentIndex;
/*  95 */           break;
/*     */         }
/*     */       }
/*  98 */       currentIndex++;
/*     */     }
/* 100 */     int invIndex = 0;
/* 101 */     int finalInvIndex = -1;
/* 102 */     int[] arrayOfInt1; int armour = (arrayOfInt1 = armourtype).length; for (int finalInvIndex1 = 0; finalInvIndex1 < armour; finalInvIndex1++) { int armour1 = arrayOfInt1[finalInvIndex1];
/* 103 */       if (findItem(armour1) != -1) {
/* 104 */         finalInvIndex1 = invIndex;
/* 105 */         break;
/*     */       }
/* 107 */       invIndex++;
/*     */     }
/* 109 */     if (finalInvIndex > -1) {
/* 110 */       return finalInvIndex < invIndex;
/*     */     }
/*     */     
/* 113 */     return false;
/*     */   }
/*     */   
/* 116 */   private int findItem(int id) { for (int index = 9; index < 45; index++) {
/* 117 */       ItemStack item = this.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
/* 118 */       if ((item != null) && (net.minecraft.item.Item.getIdFromItem(item.getItem()) == id)) {
/* 119 */         return index;
/*     */       }
/*     */     }
/* 122 */     return -1;
/*     */   }
/*     */ }