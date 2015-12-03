package com.biggestnerd.civguide.executors;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.biggestnerd.civguide.CivGuide;

public class ArthroEggExecutor extends GuideExecutor {

	public ArthroEggExecutor(CivGuide plugin) {
		super(plugin);
	}
	
	@Override
	public String getPluginName() {
		return "ArthropodEgg";
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
