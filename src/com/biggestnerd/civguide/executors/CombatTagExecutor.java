package com.biggestnerd.civguide.executors;

import org.bukkit.event.EventHandler;

import com.biggestnerd.civguide.CivGuide;

import net.minelink.ctplus.event.PlayerCombatTagEvent;

public class CombatTagExecutor extends GuideExecutor {

	public CombatTagExecutor(CivGuide plugin) {
		super(plugin);
	}
	
	@Override
	public String getPluginName() {
		return "CombatTagPlus";
	}
	
	@EventHandler
	public void onCombatTag(PlayerCombatTagEvent event) {
		sendEventMessage(event.getEventName(), event.getVictim());
	}
}
