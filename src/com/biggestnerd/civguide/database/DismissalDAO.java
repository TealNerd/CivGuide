package com.biggestnerd.civguide.database;

import java.util.ArrayList;
import java.util.UUID;

public interface DismissalDAO {
	/**
	 * @return A list of players who have dismissed the event
	 * @param event the event you want to get dismissals for
	 */
	public ArrayList<UUID> getDismissedPlayersForEvent(String event);
	/**
	 * @return A list of events a player has dismissed
	 * @param player the player you want to get dismissed events for
	 */
	public ArrayList<String> getDismissedEventsForPlayer(String player);
	/**
	 * Dismisses an event for a given player
	 *  
	 * @param event the event to be dismissed
	 * @param player the player dismissing the event
	 */
	public void addDismissal(String event, String player);
}
