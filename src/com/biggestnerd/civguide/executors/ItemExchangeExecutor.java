package com.biggestnerd.civguide.executors;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import com.untamedears.ItemExchange.ItemExchangePlugin;
import com.untamedears.ItemExchange.utility.ItemExchange;

public class ItemExchangeExecutor extends GuideExecutor {

	@Override
	public String getPluginName() {
		return "ItemExchange";
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.LEFT_CLICK_BLOCK && ItemExchangePlugin.ACCEPTABLE_BLOCKS.contains(event.getClickedBlock().getType())) {
			Inventory inv = ((InventoryHolder) event.getClickedBlock().getState()).getInventory();
			ItemExchange ex = ItemExchange.getItemExchange(inv);
			if(ex.isValid()) {
				sendEventMessage(event.getEventName(), event.getPlayer());
			}
		}
	}
}
