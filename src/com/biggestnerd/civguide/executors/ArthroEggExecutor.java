package com.biggestnerd.civguide.executors;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;

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
	public void onPlayerItemHeld(PlayerItemHeldEvent event) {
		if(event.getPlayer().getItemInHand() != null && event.getPlayer().getItemInHand().containsEnchantment(Enchantment.DAMAGE_ARTHROPODS)) {
			sendEventMessage(event.getEventName(), event.getPlayer());
		}
	}
}
