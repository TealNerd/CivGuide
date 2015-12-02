package com.biggestnerd.civguide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.biggestnerd.civguide.database.DAOManager;

import net.md_5.bungee.api.ChatColor;

public class GuidedResponse {

	/**
	 * the main text to display for this response
	 */
	private final String text;
	/**
	 * the hover text to display for this response
	 */
	private final String hoverText;
	/**
	 * the event this response responds to. formatted as PluginName.EventName
	 */
	private final String event;
	
	/**
	 * List of dismissed players for the event response
	 */
	private ArrayList<UUID> dismissed;
	
	/**
	 * static list of all responses, the key is the true bukkit event name
	 */
	private static HashMap<String, GuidedResponse> responses = new HashMap<String, GuidedResponse>();
	
	public GuidedResponse(String event, String text, String hoverText) {
		if((dismissed = DAOManager.getInstance().getDismissedPlayersForEvent(event)) == null) {
			dismissed = new ArrayList<UUID>();
		}
		this.event = event;
		this.text = text + " Hover for more or click to dismiss";
		this.hoverText = hoverText;
		String[] eventParts = event.split("\\.");
		//if a guide exists for the plugin it'll let the player know they can get more info
		if(GuideBook.getBook(eventParts[0]) != null) {
			hoverText += " Do /guide " + eventParts[0] + " for more information about the " + eventParts[0] + " plugin";
		}
		responses.put(eventParts[1], this);
	}
	
	/**
	 * 
	 * @return a hoverable text message that can be sent to the player
	 */
	public HoverTextMessage generateMessage() {
		return new HoverTextMessage(event, ChatColor.YELLOW, text, hoverText);
	}
	
	/**
	 * 
	 * @param player the player to check
	 * @return if this player has dismissed this response
	 */
	public boolean dismissed(Player player) {
		return dismissed.contains(player.getUniqueId());
	}
	
	/**
	 * dismisses the given event for the given player
	 * 
	 * @param event the event to dismiss
	 * @param player the player to dismiss the event
	 */
	public static void dismiss(String event, String player) {
		GuidedResponse response;
		if((response = responses.get(event)) != null) {
			response.addDismissal(UUID.fromString(player));
		}
	}
	
	/**
	 * 
	 * @param event the event you want the response for
	 * @return a response for the given event, null if there is no response
	 */
	public static GuidedResponse getResponse(String event) {
		return responses.get(event);
	}
	
	private void addDismissal(UUID player) {
		dismissed.add(player);
	}

	public static void clearResponses() {
		responses.clear();
	}
}
