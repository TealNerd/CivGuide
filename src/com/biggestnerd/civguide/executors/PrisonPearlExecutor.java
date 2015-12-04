package com.biggestnerd.civguide.executors;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.untamedears.PrisonPearl.events.PrisonPearlEvent;

public class PrisonPearlExecutor extends GuideExecutor {

	@Override
	public String getPluginName() {
		return "PrisonPearl";
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		sendEventMessage(event.getEventName(), event.getItem().getItemStack(), event.getPlayer());
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event instanceof InventoryCreativeEvent) {
			return;
		}
		sendEventMessage(event.getEventName(), event.getCurrentItem(), Bukkit.getPlayer(event.getWhoClicked().getUniqueId()));
	}

	@EventHandler
	public void onPrisonPearl(PrisonPearlEvent event) {
		sendEventMessage(event.getEventName(), event.getPrisonPearl().getImprisonedPlayer());
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if(event.getEntityType() == EntityType.PLAYER && event.getEntity().getKiller() != null) {
			sendEventMessage(event.getEventName(), event.getEntity().getKiller());
		}
	}
}
