package com.biggestnerd.civguide;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.biggestnerd.civguide.database.DAOManager;
import com.biggestnerd.civguide.executors.ArthroEggExecutor;
import com.biggestnerd.civguide.executors.CitadelExecutor;
import com.biggestnerd.civguide.executors.CombatTagExecutor;
import com.biggestnerd.civguide.executors.FactoryModExecutor;
import com.biggestnerd.civguide.executors.ItemExchangeExecutor;
import com.biggestnerd.civguide.executors.PrisonPearlExecutor;
import com.biggestnerd.civguide.executors.RealisticBiomesExecutor;

public class CivGuide extends JavaPlugin implements Listener {
	
	private static CivGuide instance;

	@Override
	public void onEnable() {
		instance = this;
		getLogger().info("CivGuide Enabled");
		saveDefaultConfig();
		reloadConfig();
		loadGuides();
		DAOManager.getInstance();
		GuideCommand gc = new GuideCommand();
		getCommand("guide").setExecutor(gc);
		getCommand("dismiss").setExecutor(gc);
		getServer().getPluginManager().registerEvents(this, this);
		new CitadelExecutor(this);
		new PrisonPearlExecutor(this);
		new ArthroEggExecutor(this);
		new CombatTagExecutor(this);
		new FactoryModExecutor(this);
		new ItemExchangeExecutor(this);
		new RealisticBiomesExecutor(this);
	}
	
	@Override
	public void onDisable() {
		GuideBook.clearBooks();
		GuidedResponse.clearResponses();
		getLogger().info("CivGuide Disabled");
	}
	
	/**
	 * 
	 * @return the instance of the plugin
	 */
	public static CivGuide getInstance() {
		return instance;
	}
	
	/**
	 * Loads guide books from the config
	 */
	public void loadGuides() {
		FileConfiguration config = getConfig();
		if(!config.contains("booklist")) {
			getLogger().info("No book list found in the config, not loading guides");
			return;
		}
		
		List<String> bookList = config.getStringList("booklist");
		
		String guideName;
		List<String> pages;
		GuideBook book;
		
		for(String bookName : bookList) {
			if(!config.contains("books." + bookName) || bookName.isEmpty()) {
				getLogger().info(bookName + " not found but was in the book list for some reason, weird");
				continue;
			}
			ConfigurationSection bookSection = config.getConfigurationSection("books." + bookName);
			guideName = bookSection.getString("guidename", bookName);
			pages = bookSection.getStringList("pages");
			book = new GuideBook(guideName);
			book.addPages(pages);
			GuideBook.addBook(book);
		}
	}
}
