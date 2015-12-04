package com.biggestnerd.civguide.executors;

import org.bukkit.entity.Animals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class MustercullExecutor extends GuideExecutor {
	
	@Override
	public String getPluginName() {
		return "MusterCull";
	}
	
	@EventHandler
	public void onBreedAnimal(PlayerInteractEntityEvent event) {
		if(event.getRightClicked() instanceof Animals) {
			sendEventMessage(event.getEventName(), event.getPlayer());
		}
	}
}
