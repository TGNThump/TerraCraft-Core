package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.enums.ItemRarity;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemMechanics;
import uk.co.terragaming.code.terracraft.utils.item.CustomItem;

import com.comphenix.attribute.AttributeStorage;
import com.google.common.collect.Lists;


public class RenderComponent extends ItemComponent{
	
	public ItemStack render(){

		CustomItem item = new CustomItem(getItem().getIcon());
		item.setName(getColouredName());
		item.setStackSize(getStackSize());
		item.addLore("<silver>" + getClassName() + "                              <gold>" + getTypeName());
		item.addLore(renderComponents());
		item.setDurability(getDurability());
		
		AttributeStorage storage = AttributeStorage.newTarget(item.getItemStack(), ItemMechanics.getInstance().getAttributeUUID());
		storage.setData("TCID: " + getItem().getId());
		
		return storage.getTarget();
		
	}

	private List<String> renderComponents(){
		List<String> ret = Lists.newArrayList();
		
		for (int i = 0; i < 5; i++){
			for (ItemComponent c : getItem()){
				String value = c.render(i);
				if (value == null) continue;
				if (value == "") continue;
				ret.add(value);
			}
		}
		
		return ret;
	}
	
	public String getColouredName(){
		String colour = "<silver>";
		String weight = "<bold>";
		
		if (getItem().has(RarityComponent.class)){
			RarityComponent c = getItem().get(RarityComponent.class);
			colour = "" + ItemRarity.getChatColor(c.getRarity());
		}
		
		if (getItem().has(DurabilityComponent.class)){
			DurabilityComponent c = getItem().get(DurabilityComponent.class);
			if (c.isBroken()) weight = "<italic>";
		}
		
		return colour + weight + getItem().getName() + "<r>";
	}
	
	private Integer getStackSize() {
		if (!getItem().has(StackableComponent.class)) return 1;
		StackableComponent c = getItem().get(StackableComponent.class);
		return c.getCurStackSize();
	}
	
	private String getTypeName() {
		return getItem().getItemType();
	}

	private String getClassName() {
		return getItem().getItemClass();
	}
	
	private Short getDurability() {
		if (!getItem().has(DurabilityComponent.class)) return 0;
		DurabilityComponent c = getItem().get(DurabilityComponent.class);
		return c.getItemDurability();
	}
	
}
