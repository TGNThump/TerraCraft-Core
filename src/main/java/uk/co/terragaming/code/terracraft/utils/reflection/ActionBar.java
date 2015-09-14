package uk.co.terragaming.code.terracraft.utils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.utils.text.Txt;

// Based on github.com/ConnorLinfoot/ActionBarAPI

public class ActionBar {
	
	public static boolean enabled = true;
	
	public static void sendMessage(Player player, String message){
		sendRawMessage(player, Txt.parse(message));
	}
	
	public static void sendMessage(Player player, String message, Object... args){
		sendRawMessage(player, Txt.parse(message, args));
	}
	
	public static void sendRawMessage(Player player, String message){
		if (player == null) return;
		if (message == null) message = "";
		if (!enabled){
			player.sendMessage(message);
			return;
		}
		try{
			// Get the player as a craftPlayer...
			Class<?> cCraftPlayer = Reflection.getClass("org.bukkit.craftbukkit.%s.entity.CraftPlayer");
			Object craftPlayer = cCraftPlayer.cast(player);
			
			
			Object packet = null;
			Class<?> cPacketPlayOutChat = Reflection.getClass("net.minecraft.server.%s.PacketPlayOutChat");
			Class<?> cPacket = Reflection.getClass("net.minecraft.server.%s.Packet");
			Class<?> cIChatBaseComponent = Reflection.getClass("net.minecraft.server.%s.IChatBaseComponent");

			if (Reflection.getVersionInt() > 181){
				Class<?> cChatSerializer = Reflection.getClass("net.minecraft.server.%s.IChatBaseComponent.ChatSerializer");
				Method aMethod = cChatSerializer.getDeclaredMethod("a", new Class<?>[] {String.class});
				Object chatBaseComponent = cIChatBaseComponent.cast(aMethod.invoke(cChatSerializer, "{\"text\": \"" + message + "\"}"));
				packet = cPacketPlayOutChat.getConstructor(new Class<?>[] {cIChatBaseComponent, byte.class}).newInstance(new Object[] {chatBaseComponent, (byte) 2});
			} else {
				Class<?> cChatComponentText = Reflection.getClass("net.minecraft.server.%s.ChatComponentText");
				Object chatComponentText = cChatComponentText.getConstructor(new Class<?> [] {String.class}).newInstance(new Object[] {message});
				packet = cPacketPlayOutChat.getConstructor(new Class<?>[] {cIChatBaseComponent, byte.class}).newInstance(new Object[] {chatComponentText, (byte) 2});
			}
			
			Method mGetHandle = cCraftPlayer.getDeclaredMethod("getHandle", new Class<?>[]{});
			Object handle = mGetHandle.invoke(craftPlayer);
			Field f1 = handle.getClass().getDeclaredField("playerConnection");
			Object playerConnection = f1.get(handle);
			Method sendPacket = playerConnection.getClass().getDeclaredMethod("sendPacket", new Class<?>[] {cPacket});
			sendPacket.invoke(playerConnection, packet);
			
		} catch (Exception e){
			enabled = false;
			e.printStackTrace();
		}
	}
	
}
