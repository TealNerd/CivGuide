package com.biggestnerd.civguide.responses;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.biggestnerd.civguide.database.DAOManager;

public class EventResponse {

	private final String eventTag;
	
	private final List<String> triggerEvents;
	
	private final String response;
	
	private final TriggerItem triggerItem;
	
	private List<UUID> dismissed;

	public EventResponse(String pluginName, String eventTag, List<String> triggerEvents, String response, TriggerItem triggerItem) {
		if((dismissed = DAOManager.getInstance().getDismissedPlayersForEvent(pluginName + "." + eventTag)) == null) {
			dismissed = new ArrayList<UUID>();
		}
		this.eventTag = eventTag;
		this.triggerEvents = triggerEvents;
		this.response = response;
		this.triggerItem = triggerItem;
	}

	public String getEventTag() {
		return eventTag;
	}

	public List<String> getTriggerEvents() {
		return triggerEvents;
	}

	public String getResponse() {
		return response;
	}

	public TriggerItem getTriggerItem() {
		return triggerItem;
	}
	
	public boolean isDismissed(UUID player) {
		return dismissed.contains(player);
	}
	
	public void dismiss(UUID player) {
		dismissed.add(player);
	}
}
