package com.biggestnerd.civguide;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.biggestnerd.civguide.executors.CitadelExecutor;
import com.biggestnerd.civguide.executors.PPExecutor;

public class CivGuide extends JavaPlugin implements Listener {
	
	private Database db;
	private static CivGuide instance;

	@Override
	public void onEnable() {
		instance = this;
		getLogger().info("CivGuide Enabled");
		saveDefaultConfig();
		reloadConfig();
		loadGuides();
		initializeDatabase();
		getCommand("guide").setExecutor(new GuideCommand(this));
		getServer().getPluginManager().registerEvents(this, this);
		new CitadelExecutor(this);
		new PPExecutor(this);
	}
	
	@Override
	public void onDisable() {
		GuideBook.clearBooks();
		GuidedResponse.clearResponses();
		getLogger().info("CivGuide Disabled");
	}
	
	/**
	 * initializes the database and creates the table if it doesn't exist
	 */
	public void initializeDatabase() {
		FileConfiguration config = getConfig();
		ConfigurationSection sql = config.getConfigurationSection("sql");
		String dbName = sql.getString("dbname");
		String host = sql.getString("host");
		String pass = sql.getString("pass");
		int port = sql.getInt("port");
		String user = sql.getString("user");
		db = new Database(host, port, dbName, user, pass, getLogger());
		db.connect();
		String createTable = "CREATE TABLE IF NOT EXISTS dismissed (event VARCHAR(40) NOT NULL, uuid VARCHAR(40) NOT NULL)";
		db.execute(createTable);
	}
	
	/**
	 * @return A list of players who have dismissed the event
	 * @param event the event you want to get dismissals for
	 */
	public ArrayList<UUID> getDismissedPlayersForEvent(String event) {
		ArrayList<UUID> players = new ArrayList<UUID>();
		try {
			PreparedStatement request = db.prepareStatement("SELECT * FROM dismissed WHERE event = ?");
			request.setString(1, event);
			ResultSet set = request.executeQuery();
			while(set.next()) {
				players.add(UUID.fromString(set.getString("uuid")));
			}
		} catch (SQLException ex) {
			getLogger().log(Level.SEVERE, "Could not load dismissals for " + event, ex);
		}
		return players;
	}
	
	/**
	 * Dismisses the event for a player
	 * 
	 * @param event the event you want to dismiss
	 * @param player the uuid as a string of the player dismissing the event
	 */
	public void addDismissal(String event, String player) {
		if(getDismissedPlayersForEvent(event).contains(UUID.fromString(player))) {
			return;
		}
		try {
			PreparedStatement playerDismiss = db.prepareStatement("INSERT INTO dismissed (event, uuid) VALUES (?,?)");
			playerDismiss.setString(1, event);
			playerDismiss.setString(2, player);
			playerDismiss.execute();
			GuidedResponse.dismiss(event.split("\\.")[1], player);
		} catch (SQLException ex) {
			getLogger().log(Level.SEVERE, "Error dismissing event " + event + " for " + player, ex);
		}
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
