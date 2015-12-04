package com.biggestnerd.civguide.executors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTameEvent;

public class CivPetExecutor extends GuideExecutor {

	@Override
	public String getPluginName() {
		return "CivPets";
	}
	
	@EventHandler
	public void onTame(EntityTameEvent event) {
		Player player = Bukkit.getServer().getPlayer(event.getOwner().getUniqueId());
		sendEventMessage(event.getEventName(), player);
	}
}
