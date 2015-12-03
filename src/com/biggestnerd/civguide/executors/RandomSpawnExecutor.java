package com.biggestnerd.civguide.executors;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.biggestnerd.civguide.CivGuide;

public class RandomSpawnExecutor extends GuideExecutor {

	public RandomSpawnExecutor(CivGuide plugin) {
		super(plugin);
	}
	
	@Override
	public String getPluginName() {
		return "Random-Spawn";
	}
	
	@EventHandler
	public void onPlayerSpawn(PlayerRespawnEvent event) {
		sendEventMessage(event.getEventName(), event.getPlayer());
	}
}
