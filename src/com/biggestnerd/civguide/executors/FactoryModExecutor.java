package com.biggestnerd.civguide.executors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.github.igotyou.FactoryMod.FactoryModPlugin;

public class FactoryModExecutor extends GuideExecutor {

	@Override
	public String getPluginName() {
		return "FactoryMod";
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.LEFT_CLICK_BLOCK && event.getPlayer().getItemInHand() != null
				&& event.getPlayer().getItemInHand().getType() == FactoryModPlugin.FACTORY_INTERACTION_MATERIAL) {
			Material mat = event.getClickedBlock().getType();
			if(mat == FactoryModPlugin.CENTRAL_BLOCK_MATERIAL) {
				sendEventMessage(event.getEventName(), event.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		sendEventMessage(event.getEventName(), event.getItem().getItemStack(), event.getPlayer());
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		sendEventMessage(event.getEventName(), event.getCurrentItem(), Bukkit.getPlayer(event.getWhoClicked().getUniqueId()));
	}
}
