package com.biggestnerd.civguide.executors;

import org.bukkit.event.EventHandler;

import vg.civcraft.mc.citadel.events.ReinforcementDamageEvent;

public class CitadelExecutor extends GuideExecutor {

	@EventHandler
	public void onReinforcementDamage(ReinforcementDamageEvent event) {
		sendEventMessage(event.getEventName(), event.getPlayer());
	}

	@Override
	public String getPluginName() {
		return "Citadel";
	}
}
