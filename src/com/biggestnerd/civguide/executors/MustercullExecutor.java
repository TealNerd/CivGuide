package com.biggestnerd.civguide.executors;

import org.bukkit.entity.Animals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.biggestnerd.civguide.CivGuide;

public class MustercullExecutor extends GuideExecutor {

	public MustercullExecutor(CivGuide plugin) {
		super(plugin);
	}
	
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
