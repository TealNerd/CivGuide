package com.biggestnerd.civguide.responses;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.biggestnerd.civguide.CivGuide;
import com.biggestnerd.civguide.GuideBook;
import com.biggestnerd.civguide.database.DAOManager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import vg.civcraft.mc.civmenu.Menu;

public class ResponseManager {

	private HashMap<String, EventResponse> responses;
	private String pluginName;
	private String documentationUrl = null;
	
	public ResponseManager(String pluginName) {
		this.pluginName = pluginName;
		responses = new HashMap<String, EventResponse>();
		ConfigurationSection config = CivGuide.getInstance().getConfig().getConfigurationSection(pluginName);
		if(config.contains("url")) {
			documentationUrl = config.getString("url");
		}
	}
	
	public void registerResponse(String eventTag, List<String> triggerEvents, String response, TriggerItem triggerItem) {
		responses.put(eventTag, new EventResponse(pluginName, eventTag, triggerEvents, response, triggerItem));
	}
	
	public EventResponse getEventResponse(String eventName) {
		EventResponse out = null;
		if((out = responses.get(eventName)) == null) {
			for(EventResponse response : responses.values()) {
				if(response.getTriggerEvents().contains(eventName)) {
					return response;
				}
			}
		}
		return out;
	}
	
	public EventResponse getEventResponse(String eventName, ItemStack trigger) {
		for(EventResponse response : responses.values()) {
			if(response.getTriggerEvents().contains(eventName)) {
				System.out.println(response.getEventTag() + " is triggered by " + eventName);
			}
			if(response.getTriggerEvents().contains(eventName) && response.getTriggerItem().equals(trigger)) {
				return response;
			}
		}
		return null;
	}
	
	public void sendMessage(EventResponse response, Player player) {
		Menu menu = new Menu();
		
		TextComponent title = new TextComponent(pluginName + " " + response.getEventTag());
		title.setColor(ChatColor.YELLOW);
		menu.setTitle(title);
		
		TextComponent message = new TextComponent(response.getResponse());
		message.setColor(ChatColor.AQUA);
		menu.setSubTitle(message);
		
		TextComponent link = new TextComponent("Click to dismiss forever");
		link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/dismiss " + pluginName + "." + response.getEventTag()).create()));
		link.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dismiss " + pluginName + "." + response.getEventTag()));
		link.setItalic(true);
		menu.addPart(link);
		
		if(GuideBook.getBook(pluginName) != null) {
			TextComponent book = new TextComponent("Click for plugin guide");
			book.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/guide " + pluginName).create()));
			book.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guide " + pluginName));
			book.setItalic(true);
			menu.addPart(book);
		}
		
		if(documentationUrl != null) {
			TextComponent docu = new TextComponent("Click to view documentation");
			docu.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(documentationUrl).create()));
			docu.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, documentationUrl));
			docu.setItalic(true);
			menu.addPart(docu);
		}
		
		menu.sendPlayer(player);
	}
	
	public void dismissEvent(EventResponse response, UUID player) {
		DAOManager.getInstance().addDismissal(pluginName + "." + response.getEventTag(), player);
	}
}
