package com.biggestnerd.civguide.executors;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.untamedears.realisticbiomes.RealisticBiomes;
import com.untamedears.realisticbiomes.utils.MaterialAliases;

public class RealisticBiomesExecutor extends GuideExecutor {

	@Override
	public String getPluginName() {
		return "RealisticBiomes";
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (event.getItem() == null) {
			return;
		}
		
		RealisticBiomes rbPlugin = (RealisticBiomes)plugin.getServer().getPluginManager().getPlugin("RealisticBiomes");
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem().getType() == Material.STICK) {
			if(MaterialAliases.getConfig(rbPlugin.materialGrowth, event.getClickedBlock()) != null) {
				sendEventMessage(event.getEventName(), event.getPlayer());
			}
		} else {
			if(MaterialAliases.getConfig(rbPlugin.materialGrowth, event.getItem()) != null) {
				sendEventMessage(event.getEventName(), event.getPlayer());
			}
		}
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getClickedBlock().getType() == Material.SOIL && MaterialAliases.getConfig(rbPlugin.materialGrowth, event.getClickedBlock()) != null) {
				sendEventMessage(event.getEventName(), event.getPlayer());
			}
		}
	}
}
