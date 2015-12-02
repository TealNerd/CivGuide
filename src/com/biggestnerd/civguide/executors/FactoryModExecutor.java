package com.biggestnerd.civguide.executors;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.biggestnerd.civguide.CivGuide;
import com.github.igotyou.FactoryMod.FactoryModPlugin;

public class FactoryModExecutor extends GuideExecutor {

	public FactoryModExecutor(CivGuide plugin) {
		super(plugin);
	}
	
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
}
