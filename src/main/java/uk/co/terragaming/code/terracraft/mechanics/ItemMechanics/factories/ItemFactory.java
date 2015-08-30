package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import uk.co.terragaming.code.terracraft.events.item.ItemCreateEvent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemMechanics;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemSystem;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.Container;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;


public class ItemFactory {
	
	private static ItemSystem ItemSystem = ItemMechanics.getInstance().getItemSystem();
	
	public static Item create(Container container, Material icon, String name, String typeName, String className){
		Item item = new Item();
		
		item.setName(name);
		item.setIcon(icon);
		item.setItemType(typeName);
		item.setItemClass(className);
		
		if (!container.add(item)){
			TerraLogger.error(Txt.parse("Container '%s' of type '%s' is full...", container.getContainerId(), container.getDao().getType()));
			item = null;
			return null;
		}
		
		try {
			Item.dao.create(item);
			item.refresh();
			item.componentData.refreshCollection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		ItemSystem.put(item);
		
		ItemCreateEvent event = new ItemCreateEvent(item);
		Bukkit.getPluginManager().callEvent(event);
		
		return item;
	}
	
	public static Item clone(Container container, Item toClone){
		Item clone = create(container, toClone.getIcon(), toClone.getName(), toClone.getItemType(), toClone.getItemClass());
		if (clone == null) return null;
		for (ItemComponent c : toClone){
			clone.add(c);
		}
		return clone;
	}
	
}
