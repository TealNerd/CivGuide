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
		if(hasLore() != item.getItemMeta().hasLore() || ((hasLore() && item.getItemMeta().hasLore()) && !lore.equals(item.getItemMeta().getLore()))) {
			otherChecks = false;
		}
		if(duras != null && duras.size() > 0 && !duras.isEmpty()) {
			otherChecks = otherChecks && duras.contains(item.getDurability());
		}
		return item.getType().toString().matches(materialName) && otherChecks;
	}
	
	private boolean hasLore() {
		return lore != null && !lore.isEmpty() && lore.size() > 0;
	}
}
