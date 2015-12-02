package com.biggestnerd.civguide.executors;

import org.bukkit.event.EventHandler;

import com.biggestnerd.civguide.CivGuide;

import vg.civcraft.mc.citadel.events.ReinforcementDamageEvent;

public class CitadelExecutor extends GuideExecutor {
	
	public CitadelExecutor(CivGuide plugin) {
		super(plugin);
	}

	@EventHandler
	public void onReinforcementDamage(ReinforcementDamageEvent event) {
		sendEventMessage(event.getEventName(), event.getPlayer());
	}

	@Override
	public String getPluginName() {
		return "Citadel";
	}
}
