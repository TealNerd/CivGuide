package com.biggestnerd.civguide.executors;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.biggestnerd.civguide.CivGuide;
import com.biggestnerd.civguide.responses.EventResponse;
import com.biggestnerd.civguide.responses.ResponseManager;
import com.biggestnerd.civguide.responses.TriggerItem;
/**
 * A GuideExecutor listens for events and sends GuidedResponses to players
 * Do not override sendEventMessage unless you know what you're doing
 */
public abstract class GuideExecutor implements Listener {
	
	protected CivGuide plugin;
	protected ResponseManager responseMan;
	
	public GuideExecutor(CivGuide plugin) {
		this.plugin = plugin;
		if(plugin.getServer().getPluginManager().isPluginEnabled(getPluginName())) {
			FileConfiguration config = plugin.getConfig();
			if(config.getConfigurationSection(getPluginName()) != null) {
				responseMan = new ResponseManager(getPluginName());
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

	/**
	 * @return the name of the plugin this executor corresponds to
	 */
	public abstract String getPluginName();
	
	private void loadConfigValues(FileConfiguration config) {
		ConfigurationSection pluginSection = config.getConfigurationSection(getPluginName());
		for(String event : pluginSection.getKeys(false)) {
			if(event.equals("url")) {
				continue;
			}
			ConfigurationSection eventSection = pluginSection.getConfigurationSection(event);
			ArrayList<String> triggers = new ArrayList<String>();
			String response = eventSection.getString("text");
			if(eventSection.getKeys(false).contains("events")) {
				triggers.addAll(eventSection.getStringList("events"));
			} else {
				triggers.add(event);
			}
			if(eventSection.getKeys(false).contains("trigger")) {
				String materialName = eventSection.getString("trigger.material");
				List<String> enchants = eventSection.getStringList("triggers.enchants");
				List<String> lore = eventSection.getStringList("triggers.lore");
				List<Short> duras = eventSection.getShortList("triggers.duras");
				TriggerItem triggerItem = new TriggerItem(materialName, enchants, lore, duras);
				responseMan.registerResponse(event, triggers, response, triggerItem);
			} else {
				responseMan.registerResponse(event, triggers, response, null);
			}
		}
	}
	
	/**
	 * Sends a HoverTextMessage to a player for an event
	 * 
	 * @param eventName the name of the event
	 * @param player the player you want to send a message to
	 */
	protected void sendEventMessage(String eventName, Player player) {
		EventResponse response;
		if((response = responseMan.getEventResponse(eventName)) != null) {
			if(!response.isDismissed(player.getUniqueId())) {
				responseMan.sendMessage(response, player);
			}
		} else {
			plugin.getLogger().severe(getPluginName() + " " + eventName + " was not configured, cannot send " + player.getName() + " a message");
		}
	}
	
	protected void sendEventMessage(String eventName, ItemStack trigger, Player player) {
		EventResponse response;
		if((response = responseMan.getEventResponse(eventName, trigger)) != null) {
			if(!response.isDismissed(player.getUniqueId())) {
				responseMan.sendMessage(response, player);
			}
		} else {
			plugin.getLogger().severe(getPluginName() + " " + eventName + " was not configured, cannot send " + player.getName() + " a message");
		}
	}
}
