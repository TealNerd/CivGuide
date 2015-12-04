package com.biggestnerd.civguide.executors;

import org.bukkit.event.EventHandler;

import net.minelink.ctplus.event.PlayerCombatTagEvent;

public class CombatTagExecutor extends GuideExecutor {
	
	@Override
	public String getPluginName() {
		return "CombatTagPlus";
	}
	
	@EventHandler
	public void onCombatTag(PlayerCombatTagEvent event) {
		sendEventMessage(event.getEventName(), event.getVictim());
	}
}
