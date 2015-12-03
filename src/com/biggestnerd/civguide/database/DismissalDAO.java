package com.biggestnerd.civguide.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.biggestnerd.civguide.CivGuide;

import vg.civcraft.mc.namelayer.NameAPI;

public class DismissalDAO implements IDismissalDAO {
	
	private Database db;
	private Logger logger;
	
	public DismissalDAO() {
		CivGuide plugin = CivGuide.getInstance();
		logger = plugin.getLogger();
		FileConfiguration config = plugin.getConfig();
		ConfigurationSection sql = config.getConfigurationSection("sql");
		String dbName = sql.getString("dbname");
		String host = sql.getString("host");
		String pass = sql.getString("pass");
		int port = sql.getInt("port");
		String user = sql.getString("user");
		db = new Database(host, port, dbName, user, pass, plugin.getLogger());
		initializeDatabase();
		prepareTable();
	}
	
	public void initializeDatabase() {
		db.connect();
	}
	
	public void prepareTable() {
		String createTable = "CREATE TABLE IF NOT EXISTS dismissed (event VARCHAR(40) NOT NULL, uuid VARCHAR(40) NOT NULL)";
		db.execute(createTable);
	}

	@Override
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
			logger.log(Level.SEVERE, "Could not load dismissals for " + event, ex);
		}
		return players;
	}

	@Override
	public ArrayList<String> getDismissedEventsForPlayer(String player) {
		ArrayList<String> events = new ArrayList<String>();
		try {
			PreparedStatement request = db.prepareStatement("SELECT * FROM dismissed WHERE name = ?");
			request.setString(1, player);
			ResultSet set = request.executeQuery();
			while(set.next()) {
				events.add(set.getString("event"));
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Could not load dissmissed events for " + NameAPI.getCurrentName(UUID.fromString(player)), ex);
		}
		return events;
	}

	@Override
	public void addDismissal(String event, UUID player) {
		if(getDismissedPlayersForEvent(event).contains(player)) {
			return;
		}
		try {
			PreparedStatement playerDismiss = db.prepareStatement("INSERT INTO dismissed (event, uuid) VALUES (?,?)");
			playerDismiss.setString(1, event);
			playerDismiss.setString(2, player.toString());
			playerDismiss.execute();
			//GuidedResponse.dismiss(event.split("\\.")[1], player);
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Error dismissing event " + event + " for " + player, ex);
		}
	}

	public boolean valid() {
		return db.isConnected();
	}
}
