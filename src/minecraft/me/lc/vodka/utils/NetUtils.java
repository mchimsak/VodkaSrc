// 
// Decompiled by Procyon v0.5.30
// 

package me.lc.vodka.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class NetUtils
{
    public static void sendPacket(final Packet packet) {
        Minecraft.getMinecraft().getNetHandler().getNetworkManager().dispatchPacket(packet, null);
    }
}
