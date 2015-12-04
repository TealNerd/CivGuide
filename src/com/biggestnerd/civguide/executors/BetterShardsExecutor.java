package com.biggestnerd.civguide.executors;

import org.bukkit.event.EventHandler;

import vg.civcraft.mc.bettershards.events.PlayerArrivedChangeServerEvent;

public class BetterShardsExecutor extends GuideExecutor {
	
	@Override
	public String getPluginName() {
		return "BetterShards";
	}
	
	@EventHandler
	public void onPlayerArrivedChangeServer(PlayerArrivedChangeServerEvent event) {
		sendEventMessage(event.getEventName(), event.getPlayer());
	}
}
