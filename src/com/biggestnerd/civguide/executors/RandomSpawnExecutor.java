package com.biggestnerd.civguide.executors;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RandomSpawnExecutor extends GuideExecutor {

	@Override
	public String getPluginName() {
		return "Random-Spawn";
	}
	
	@EventHandler
	public void onPlayerSpawn(PlayerRespawnEvent event) {
		sendEventMessage(event.getEventName(), event.getPlayer());
	}
}
