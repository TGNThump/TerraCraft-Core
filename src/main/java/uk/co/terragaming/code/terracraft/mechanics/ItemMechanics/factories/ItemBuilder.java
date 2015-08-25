package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories;

import org.bukkit.Material;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.Container;


public class ItemBuilder {
	private Material icon;
	private Container container;
	private String name = "";
	private String itemType = "";
	private String itemClass = "";
	
	public ItemBuilder setIcon(Material icon) {
		this.icon = icon;
		return this;
	}
	
	public ItemBuilder setContainer(Container container) {
		this.container = container;
		return this;
	}
	
	public ItemBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public ItemBuilder setType(String itemType) {
		this.itemType = itemType;
		return this;
	}
	
	public ItemBuilder setClass(String itemClass) {
		this.itemClass = itemClass;
		return this;
	}
	
	public Item build(){
		Item i = ItemFactory.create(container, icon, name, itemType, itemClass);
		return i;
	}
}
