package com.biggestnerd.civguide;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.google.gson.Gson;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class HoverTextMessage {
	
	private String text = "";
	private ChatEvent hoverEvent;
	private ChatEvent clickEvent;

	public HoverTextMessage(String event, ChatColor color, String text, String hover) {
		if(color != null) {
			this.text = color.toString();
		}
		this.text += text;
		String hoverLines = splitHoverText(hover);
		hoverEvent = new ChatEvent("show_text", hoverLines);
		clickEvent = new ChatEvent("run_command", "/dismiss " + event);
	}
	
	public void sendToPlayer(Player player) {
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(this);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a(jsonMessage)));
	}
	
	private class ChatEvent {
		private String action;
		private String value;
		
		public ChatEvent(String action, String value) {
			this.action = action;
			this.value = value;
		}
	}
	
	public String splitHoverText(String hoverText) {
		String[] words = hoverText.split(" ");
		StringBuilder lineBuilder = new StringBuilder();
		StringBuilder currentLine = new StringBuilder();
		for(int i = 0; i < words.length; i++) {
			lineBuilder.append(words[i] + " ");
			currentLine.append(words[i] + " ");
			if(currentLine.toString().length() >= 42) {
				lineBuilder.append('\n');
				currentLine = new StringBuilder();
			}
		}
		return lineBuilder.toString();
	}
}
