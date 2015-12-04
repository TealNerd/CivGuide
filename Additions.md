#So you want to add a plugin to CivGuide? Well you're in luck, it's easy!

In this example, we'll be adding an executor for ArthropodEgg

1) First you need to add the plugin to the CivGuide plugin.yml

2) Then you need to add to the CivGuide config.yml in the following format:
```yml
#The first thing in the section is the plugin name
ArthropodEgg:
  #Then you can make events, of which there are two types
  #Custom events can have multiple bukkit events as well as item triggers
  ReceiveBaneSword:
    events:
      - PlayerPickupItemEvent
      - InventoryClickEvent
    trigger:
      #the material name can be matched with regex. this will match any sword
      material: '*_SWORD'
      #a list of enchants. the trigger item must have all of these, but can also have more
      enchants:
        - DAMAGE_ARTHROPODS
      #you can also have lore which must match exactly
      #lore:
      #  - lore line one
      #  - lore line two
      #you can also specify multiple valid durabilities (damage values for items like stone)
      #duras:
      #  - 1
      #  - 2
    text: Text to display to the player
  #Simple events consist only of a bukkit event name and text
  #BukkitEventName:
    #text: Text to display to the player
```

3) Create an executor for the plugin. It has to extend GuideExecutor. Override getPluginName() to return the name of the plugin. Event handling methods should call the method sendEventMessage() with either two arguments or three. The prior is sendEventMessage(String eventName, Player player) and the second is sendEventMessage(String eventName, ItemStack trigger, Player player). The latter should be used for custom events that have triggers. Without a trigger, you should check to make sure the event should go through, for instance make sure the InventoryClickEvent isn't an instance of InventoryCreativeEvent. Passing sendEventMEssage an item will check if it's a valid trigger for that event. Here's an example class for ArthropodEgg:

```java
package com.biggestnerd.civguide.executors;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.biggestnerd.civguide.CivGuide;

public class ArthroEggExecutor extends GuideExecutor {

	@Override
	public String getPluginName() {
		return "ArthropodEgg";
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event instanceof InventoryCreativeEvent) {
			return;
		}
		sendEventMessage(event.getEventName(), event.getCurrentItem(), Bukkit.getPlayer(event.getWhoClicked().getUniqueId()));
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		sendEventMessage(event.getEventName(), event.getItem().getItemStack(), event.getPlayer());
	}
}
```

4) Lastly register the executor by instantiating it in the main plugin class
