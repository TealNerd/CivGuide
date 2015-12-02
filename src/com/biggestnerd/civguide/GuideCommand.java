package com.biggestnerd.civguide;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.biggestnerd.civguide.database.DAOManager;

import net.md_5.bungee.api.ChatColor;

public class GuideCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("dismiss")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Sorry, only players can dismiss events");
				return true;
			}
			Player player = (Player) sender;
			if(args.length == 0) {
				return true;
			}
			if(DAOManager.getInstance().getDismissedPlayersForEvent(args[0]).contains(player.getUniqueId())) {
				player.sendMessage(ChatColor.RED + "You have already dismissed the " + args[0]);
				return true;
			}
			DAOManager.getInstance().addDismissal(args[0], player.getUniqueId().toString());
			player.sendMessage(ChatColor.GREEN + "You have dismissed the " + args[0] + " you will no longer see messages about this event");
			return true;
		} else if(command.getName().equalsIgnoreCase("guide")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Sorry, only players can get guide books");
				return true;
			}
			Player player = (Player) sender;
			if(args.length == 0) {
				player.sendMessage(ChatColor.RED + command.getUsage());
				return true;
			}
			String bookName = args[0];
			if(GuideBook.hasBook(bookName, player)) {
				player.sendMessage(ChatColor.RED + "You already have that book!");
				return true;
			}
			GuideBook.giveBook(bookName, player);
			return true;
		}
		return false;
	}
}
