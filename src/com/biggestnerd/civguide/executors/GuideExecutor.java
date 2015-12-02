package com.biggestnerd.civguide.executors;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.biggestnerd.civguide.CivGuide;
import com.biggestnerd.civguide.GuidedResponse;
/**
 * A GuideExecutor listens for events and sends GuidedResponses to players
 * Do not override sendEventMessage unless you know what you're doing
 */
public abstract class GuideExecutor implements Listener {
	
	protected CivGuide plugin;
	
	public GuideExecutor(CivGuide plugin) {
		this.plugin = plugin;
		if(plugin.getServer().getPluginManager().isPluginEnabled(getPluginName())) {
			FileConfiguration config = plugin.getConfig();
			if(config.getConfigurationSection(getPluginName()) != null) {
				plugin.getLogger().info(getPluginName() + " is installed, registering it's CivGuide executor!");
				plugin.getServer().getPluginManager().registerEvents(this, plugin);
				loadConfigValues(config);
			} else {
				plugin.getLogger().severe(getPluginName() + " is not in the config, cannot register it's CivGuide executor!");
			}
		} else {
			plugin.getLogger().severe(getPluginName() + " is not installed, cannot register it's CivGuide executor!");
		}
	}

	public abstract String getPluginName();
	
	private void loadConfigValues(FileConfiguration config) {
		ConfigurationSection pluginSection = config.getConfigurationSection(getPluginName());
		for(String event : pluginSection.getKeys(false)) {
			new GuidedResponse(getPluginName() + "." + event, pluginSection.getString(event + ".text"), pluginSection.getString(event + ".hover"));
		}
	}
	
	/**
	 * Sends a HoverTextMessage to a player for an event
	 * 
	 * @param eventName the name of the event
	 * @param player the player you want to send a message to
	 */
	protected void sendEventMessage(String eventName, Player player) {
		GuidedResponse response;
		if((response = GuidedResponse.getResponse(eventName)) != null) {
			if(!response.dismissed(player)) {
				response.generateMessage().sendToPlayer(player);
			}
		} else {
			plugin.getLogger().severe(eventName + " was not configured, cannot send " + player.getName() + " a message");
		}
	}
}
