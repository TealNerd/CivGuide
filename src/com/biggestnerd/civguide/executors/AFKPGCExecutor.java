package com.biggestnerd.civguide.executors;

import com.biggestnerd.civguide.CivGuide;

public class AFKPGCExecutor extends GuideExecutor {

	public AFKPGCExecutor(CivGuide plugin) {
		super(plugin);
	}
	
	@Override
	public String getPluginName() {
		return "AFKPGC";
	}
	
	//TODO: Need some kind of custom event in AFKPGC
}
