package com.biggestnerd.civguide.executors;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.PlayerInventory;

import com.biggestnerd.civguide.CivGuide;
import com.untamedears.PrisonPearl.events.PrisonPearlEvent;

public class PPExecutor extends GuideExecutor {

	public PPExecutor(CivGuide plugin) {
		super(plugin);
	}
	
	@Override
	public String getPluginName() {
		return "PrisonPearl";
	}
	
	@EventHandler
	public void onItemPickup(PlayerItemHeldEvent event) {
		PlayerInventory inv = event.getPlayer().getInventory();
		if(inv.getItem(event.getNewSlot()) != null && inv.getItem(event.getNewSlot()).getType() == Material.ENDER_PEARL) {
			sendEventMessage(event.getEventName(), event.getPlayer());
		}
	}

	@EventHandler
	public void onPrisonPearl(PrisonPearlEvent event) {
		sendEventMessage(event.getEventName(), event.getPrisonPearl().getImprisonedPlayer());
	}
}
