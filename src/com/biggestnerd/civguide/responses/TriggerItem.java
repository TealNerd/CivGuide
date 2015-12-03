package com.biggestnerd.civguide.responses;

import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class TriggerItem {

	private String materialName;
	private List<String> enchants;
	private List<String> lore;
	private List<Short> duras;
	
	public TriggerItem(String materialName, List<String> enchants, List<String> lore, List<Short> duras) {
		this.materialName = materialName;
		this.enchants = enchants;
		this.lore = lore;
		this.duras = duras;
	}
	
	public boolean equals(ItemStack item) {
		boolean otherChecks = true;
		if(enchants.size() > 0 && !enchants.isEmpty() && enchants != null && item.getItemMeta().hasEnchants()) {
			for(String enchant : enchants) {
				if(!item.getItemMeta().hasEnchant(Enchantment.getByName(enchant))) {
					otherChecks = false;
				}
			}
		}
		if(lore != null && lore.size() > 0 && !lore.isEmpty()) {
			otherChecks = lore.equals(item.getItemMeta().getLore());
		}
		if(duras != null && duras.size() > 0 && !duras.isEmpty()) {
			otherChecks = duras.contains(item.getDurability());
		}
		return item.getType().toString().matches(materialName) && otherChecks;
	}
}
