package com.biggestnerd.civguide;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class HoverTextMessage {
	
	private final static int CHAT_SIZE = 52;
	private TextComponent message;

	public HoverTextMessage(String event, net.md_5.bungee.api.ChatColor color, String text, String hover) {
		message = new TextComponent(text);
		if(color != null) {
			message.setColor(color);
		}
		hover = splitHoverText(hover);
		message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()));
		message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dismiss " + event));
	}
	
	public void sendToPlayer(Player player) {
		player.spigot().sendMessage(message);
	}
	
	public String splitHoverText(String hoverText) {
		String[] words = hoverText.split(" ");
		StringBuilder lineBuilder = new StringBuilder();
		StringBuilder currentLine = new StringBuilder();
		for(int i = 0; i < words.length; i++) {
			lineBuilder.append(words[i] + " ");
			currentLine.append(words[i] + " ");
			if(currentLine.toString().length() >= CHAT_SIZE) {
				lineBuilder.append('\n');
				currentLine = new StringBuilder();
			}
		}
		return lineBuilder.toString();
	}
}
