package com.biggestnerd.civguide.executors;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import vg.civcraft.mc.citadel.events.ReinforcementDamageEvent;

public class CitadelExecutor extends GuideExecutor {

	@Override
	public String getPluginName() {
		return "Citadel";
	}

	@EventHandler
	public void onReinforcementDamage(ReinforcementDamageEvent event) {
		sendEventMessage(event.getEventName(), event.getPlayer());
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		sendEventMessage(event.getEventName(), event.getPlayer());
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event instanceof InventoryCreativeEvent) {
			return;
		}
		sendEventMessage(event.getEventName(), event.getCurrentItem(), Bukkit.getPlayer(event.getWhoClicked().getUniqueId()));
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		sendEventMessage(event.getEventName(), event.getItem().getItemStack(), event.getPlayer());
	}
}
